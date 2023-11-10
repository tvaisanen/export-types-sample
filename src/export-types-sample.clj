(ns export-types-sample
  "Simple example on how to create and export type clj-kondo
  type definitions that can be installed in a similar way
  that @types/lib-name is used in Typescript."
  (:require [clojure.java.io :as io]
            [malli.dev :as dev]))

(def Point [:map
            [:x :int]
            [:y :int]])

(defn add-points
  {:malli/schema [:=> [:cat Point Point] Point]}
  [p1 p2]
  (merge-with + p1 p2))

(defn render-point
  {:malli/schema [:=> [:cat Point] :string]}
  [p]
  (str p))

(defn export-types []
  ;; collect and start instrumentation
  (dev/start!)

  ;; create export file
  (def export-file
    (io/file "resources/clj-kondo/clj-kondo.exports/tvaisanen/export-types-sample/config.edn"))

  ;; make parents if not exist
  (io/make-parents export-file)

  ;; copy the configs
  (io/copy
   (io/file ".clj-kondo/metosin/malli-types-clj/config.edn")
   export-file)

  ;; clear the cache and stop instrumentation
  (dev/stop!))

(export-types)

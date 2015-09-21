define(
    "frontend-js-metal-web@1.0.0/metal/src/array/array",
    ['exports', 'module'],
    function (exports, module) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        var array = (function () {
            function array() {
                _classCallCheck(this, array);
            }

            _createClass(array, null, [{
                key: 'equal',

                /**
        * Checks if the given arrays have the same content.
        * @param {!Array<*>} arr1
        * @param {!Array<*>} arr2
        * @return {boolean}
        */
                value: function equal(arr1, arr2) {
                    for (var i = 0; i < arr1.length; i++) {
                        if (arr1[i] !== arr2[i]) {
                            return false;
                        }
                    }
                    return arr1.length === arr2.length;
                }

                /**
        * Returns the first value in the given array that isn't undefined.
        * @param {!Array} arr
        * @return {*}
        */
            }, {
                key: 'firstDefinedValue',
                value: function firstDefinedValue(arr) {
                    for (var i = 0; i < arr.length; i++) {
                        if (arr[i] !== undefined) {
                            return arr[i];
                        }
                    }
                }

                /**
        * Transforms the input nested array to become flat.
        * @param {Array.<*|Array.<*>>} arr Nested array to flatten.
        * @param {Array.<*>} opt_output Optional output array.
        * @return {Array.<*>} Flat array.
        */
            }, {
                key: 'flatten',
                value: function flatten(arr, opt_output) {
                    var output = opt_output || [];
                    for (var i = 0; i < arr.length; i++) {
                        if (Array.isArray(arr[i])) {
                            array.flatten(arr[i], output);
                        } else {
                            output.push(arr[i]);
                        }
                    }
                    return output;
                }

                /**
        * Removes the first occurrence of a particular value from an array.
        * @param {Array.<T>} arr Array from which to remove value.
        * @param {T} obj Object to remove.
        * @return {boolean} True if an element was removed.
        * @template T
        */
            }, {
                key: 'remove',
                value: function remove(arr, obj) {
                    var i = arr.indexOf(obj);
                    var rv;
                    if (rv = i >= 0) {
                        array.removeAt(arr, i);
                    }
                    return rv;
                }

                /**
        * Removes from an array the element at index i
        * @param {Array} arr Array or array like object from which to remove value.
        * @param {number} i The index to remove.
        * @return {boolean} True if an element was removed.
        */
            }, {
                key: 'removeAt',
                value: function removeAt(arr, i) {
                    return Array.prototype.splice.call(arr, i, 1).length === 1;
                }
            }]);

            return array;
        })();

        module.exports = array;
    }
);
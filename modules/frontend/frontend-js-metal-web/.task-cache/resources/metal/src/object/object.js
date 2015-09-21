define(
    "frontend-js-metal-web@1.0.0/metal/src/object/object",
    ['exports', 'module', 'metal/src/core'],
    function (exports, module, _metalSrcCore) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        var _core = _interopRequireDefault(_metalSrcCore);

        var object = (function () {
            function object() {
                _classCallCheck(this, object);
            }

            _createClass(object, null, [{
                key: 'mixin',

                /**
        * Copies all the members of a source object to a target object.
        * @param {Object} target Target object.
        * @param {...Object} var_args The objects from which values will be copied.
        * @return {Object} Returns the target object reference.
        */
                value: function mixin(target) {
                    var key, source;
                    for (var i = 1; i < arguments.length; i++) {
                        source = arguments[i];
                        for (key in source) {
                            target[key] = source[key];
                        }
                    }
                    return target;
                }

                /**
        * Returns an object based on its fully qualified external name.
        * @param {string} name The fully qualified name.
        * @param {object=} opt_obj The object within which to look; default is
        *     <code>window</code>.
        * @return {?} The value (object or primitive) or, if not found, null.
        */
            }, {
                key: 'getObjectByName',
                value: function getObjectByName(name, opt_obj) {
                    var parts = name.split('.');
                    var cur = opt_obj || window;
                    var part;
                    while (part = parts.shift()) {
                        if (_core['default'].isDefAndNotNull(cur[part])) {
                            cur = cur[part];
                        } else {
                            return null;
                        }
                    }
                    return cur;
                }
            }]);

            return object;
        })();

        module.exports = object;
    }
);
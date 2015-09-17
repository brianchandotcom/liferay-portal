define(
    "frontend-js-metal-web@1.0.0/metal/src/component/SurfaceCollector",
    ['exports', 'module', 'metal/src/object/object', 'metal/src/disposable/Disposable'],
    function (exports, module, _metalSrcObjectObject, _metalSrcDisposableDisposable) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

        var _object = _interopRequireDefault(_metalSrcObjectObject);

        var _Disposable2 = _interopRequireDefault(_metalSrcDisposableDisposable);

        /**
      * Stores surface data to be used later by Components.
      */

        var SurfaceCollector = (function (_Disposable) {
            _inherits(SurfaceCollector, _Disposable);

            function SurfaceCollector() {
                _classCallCheck(this, SurfaceCollector);

                _get(Object.getPrototypeOf(SurfaceCollector.prototype), 'constructor', this).call(this);

                /**
        * Holds all registered surfaces, mapped by their element ids.
        * @type {!Array<!Object>}
        * @protected
        */
                this.surfaces_ = {};
            }

            /**
       * Adds a surface to this collector.
       * @param {string} surfaceElementId
       * @param {Object=} opt_data Surface data to be stored.
       */

            _createClass(SurfaceCollector, [{
                key: 'addSurface',
                value: function addSurface(surfaceElementId, opt_data) {
                    if (this.surfaces_[surfaceElementId]) {
                        this.updateSurface(surfaceElementId, opt_data);
                    } else {
                        this.surfaces_[surfaceElementId] = opt_data;
                    }
                }

                /**
        * @inheritDoc
        */
            }, {
                key: 'disposeInternal',
                value: function disposeInternal() {
                    this.surfaces_ = null;
                }

                /**
        * Gets the data for the given surface id.
        * @param {string} surfaceElementId
        * @return {!Object}
        */
            }, {
                key: 'getSurface',
                value: function getSurface(surfaceElementId) {
                    return this.surfaces_[surfaceElementId] ? this.surfaces_[surfaceElementId] : null;
                }

                /**
        * Removes all surfaces from this collector.
        */
            }, {
                key: 'removeAllSurfaces',
                value: function removeAllSurfaces() {
                    this.surfaces_ = [];
                }

                /**
        * Removes the surface with the given surface id.
        * @param {string} surfaceElementId
        */
            }, {
                key: 'removeSurface',
                value: function removeSurface(surfaceElementId) {
                    this.surfaces_[surfaceElementId] = null;
                }

                /**
        * Updates a surface from this collector.
        * @param {string} surfaceElementId
        * @param {Object=} opt_data Surface data to update the existing data.
        */
            }, {
                key: 'updateSurface',
                value: function updateSurface(surfaceElementId, opt_data) {
                    _object['default'].mixin(this.surfaces_[surfaceElementId], opt_data);
                }
            }]);

            return SurfaceCollector;
        })(_Disposable2['default']);

        module.exports = SurfaceCollector;
    }
);
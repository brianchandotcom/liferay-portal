define(
    "frontend-js-metal-web@1.0.0/metal-drag-drop/src/helpers/DragShim",
    ['exports', 'module', 'metal/src/dom/dom'],
    function (exports, module, _metalSrcDomDom) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        var _dom = _interopRequireDefault(_metalSrcDomDom);

        /**
      * Helper called by the `Drag` instance that creates a shim element
      * for attaching event listeners instead of attaching them to the
      * document. Helpful when dragging over iframes.
      */

        var DragShim = (function () {
            function DragShim() {
                _classCallCheck(this, DragShim);
            }

            /**
       * The shim element. This is only created when necessary.
       * @type {Element}
       * @protected
       * @static
       */

            _createClass(DragShim, null, [{
                key: 'attachDocListeners',

                /**
        * Attaches a listener for the document. If `useShim` is true, a
        * shim element covering the whole document will be created and
        * the listener will be attached to it instead.
        * @param {boolean} useShim
        * @param {!Object<string, !function()>} listeners
        * @return {!Array<!EventHandle>}
        * @static
        */
                value: function attachDocListeners(useShim, listeners) {
                    var element = document;
                    if (useShim) {
                        element = DragShim.getDocShim();
                        element.style.display = 'block';
                    }
                    var eventTypes = Object.keys(listeners);
                    return eventTypes.map(function (type) {
                        var isTouch = type.substr(0, 5) === 'touch';
                        return _dom['default'].on(isTouch ? document : element, type, listeners[type]);
                    });
                }

                /**
        * Gets the document's shim element, creating it when called for the first time.
        * @return {!Element}
        * @static
        */
            }, {
                key: 'getDocShim',
                value: function getDocShim() {
                    if (!DragShim.docShim_) {
                        DragShim.docShim_ = document.createElement('div');
                        DragShim.docShim_.className = 'shim';
                        DragShim.docShim_.style.position = 'fixed';
                        DragShim.docShim_.style.top = 0;
                        DragShim.docShim_.style.left = 0;
                        DragShim.docShim_.style.width = '100%';
                        DragShim.docShim_.style.height = '100%';
                        DragShim.docShim_.style.display = 'none';
                        DragShim.docShim_.style.opacity = 0;
                        DragShim.docShim_.style.zIndex = 9999;
                        _dom['default'].enterDocument(DragShim.docShim_);
                    }
                    return DragShim.docShim_;
                }

                /**
        * Hides the document's shim element.
        * @static
        */
            }, {
                key: 'hideDocShim',
                value: function hideDocShim() {
                    DragShim.getDocShim().style.display = 'none';
                }

                /**
        * Resets `DragShim`, removing the shim element from the document
        * and clearing its variable so it can be created again.
        * @static
        */
            }, {
                key: 'reset',
                value: function reset() {
                    if (DragShim.docShim_) {
                        _dom['default'].exitDocument(DragShim.docShim_);
                        DragShim.docShim_ = null;
                    }
                }
            }]);

            return DragShim;
        })();

        DragShim.docShim_ = null;

        module.exports = DragShim;
    }
);
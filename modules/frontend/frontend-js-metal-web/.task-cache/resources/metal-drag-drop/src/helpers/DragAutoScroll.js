define(
    "frontend-js-metal-web@1.0.0/metal-drag-drop/src/helpers/DragAutoScroll",
    ['exports', 'module', 'metal/src/core', 'metal/src/attribute/Attribute', 'metal-position/src/Position'],
    function (exports, module, _metalSrcCore, _metalSrcAttributeAttribute, _metalPositionSrcPosition) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

        var _core = _interopRequireDefault(_metalSrcCore);

        var _Attribute2 = _interopRequireDefault(_metalSrcAttributeAttribute);

        var _Position = _interopRequireDefault(_metalPositionSrcPosition);

        /**
      * Helper called by the `Drag` instance that scrolls elements when the
      * mouse is near their boundaries.
      */

        var DragAutoScroll = (function (_Attribute) {
            _inherits(DragAutoScroll, _Attribute);

            /**
       * @inheritDoc
       */

            function DragAutoScroll(opt_config) {
                _classCallCheck(this, DragAutoScroll);

                _get(Object.getPrototypeOf(DragAutoScroll.prototype), 'constructor', this).call(this, opt_config);

                /**
        * The handler for the current call to `setTimeout`.
        * @type {?number}
        * @protected
        */
                this.scrollTimeout_ = null;
            }

            /**
       * Attributes definition.
       * @type {!Object}
       * @static
       */

            /**
       * @inheritDoc
       */

            _createClass(DragAutoScroll, [{
                key: 'disposeInternal',
                value: function disposeInternal() {
                    _get(Object.getPrototypeOf(DragAutoScroll.prototype), 'disposeInternal', this).call(this);
                    this.stop();
                }

                /**
        * Gets the region for the given scroll container, without including scroll.
        * @param {!Element} scrollContainer
        * @return {!Object}
        * @protected
        */
            }, {
                key: 'getRegionWithoutScroll_',
                value: function getRegionWithoutScroll_(scrollContainer) {
                    if (_core['default'].isDocument(scrollContainer)) {
                        var height = window.innerHeight;
                        var width = window.innerWidth;
                        return _Position['default'].makeRegion(height, height, 0, width, 0, width);
                    } else {
                        return _Position['default'].getRegion(scrollContainer);
                    }
                }

                /**
        * Schedules a function to scroll the given containers.
        * @param {!Array<!Element>} scrollContainers
        * @param {number} mouseX
        * @param {number} mouseY
        */
            }, {
                key: 'scroll',
                value: function scroll(scrollContainers, mouseX, mouseY) {
                    this.stop();
                    this.scrollTimeout_ = setTimeout(this.scrollInternal_.bind(this, scrollContainers, mouseX, mouseY), this.delay);
                }

                /**
        * Adds the given deltas to the given element's scroll position.
        * @param {!Element} element
        * @param {number} deltaX
        * @param {number} deltaY
        * @protected
        */
            }, {
                key: 'scrollElement_',
                value: function scrollElement_(element, deltaX, deltaY) {
                    if (_core['default'].isDocument(element)) {
                        window.scrollBy(deltaX, deltaY);
                    } else {
                        element.scrollTop += deltaY;
                        element.scrollLeft += deltaX;
                    }
                }

                /**
        * Scrolls the given containers if the mouse is near their boundaries.
        * @param {!Array<!Element>} scrollContainers
        * @param {number} mouseX
        * @param {number} mouseY
        * @protected
        */
            }, {
                key: 'scrollInternal_',
                value: function scrollInternal_(scrollContainers, mouseX, mouseY) {
                    for (var i = 0; i < scrollContainers.length; i++) {
                        var scrollRegion = this.getRegionWithoutScroll_(scrollContainers[i]);
                        if (!_Position['default'].pointInsideRegion(mouseX, mouseY, scrollRegion)) {
                            continue;
                        }

                        var deltaX = 0;
                        var deltaY = 0;
                        var scrollTop = _Position['default'].getScrollTop(scrollContainers[i]);
                        var scrollLeft = _Position['default'].getScrollLeft(scrollContainers[i]);
                        if (scrollLeft > 0 && Math.abs(mouseX - scrollRegion.left) <= this.maxDistance) {
                            deltaX -= this.speed;
                        } else if (Math.abs(mouseX - scrollRegion.right) <= this.maxDistance) {
                            deltaX += this.speed;
                        }
                        if (scrollTop > 0 && Math.abs(mouseY - scrollRegion.top) <= this.maxDistance) {
                            deltaY -= this.speed;
                        } else if (Math.abs(mouseY - scrollRegion.bottom) <= this.maxDistance) {
                            deltaY += this.speed;
                        }

                        if (deltaX || deltaY) {
                            this.scrollElement_(scrollContainers[i], deltaX, deltaY);
                            this.scroll(scrollContainers, mouseX, mouseY);
                            break;
                        }
                    }
                }

                /**
        * Stops any auto scrolling that was scheduled to happen in the future.
        */
            }, {
                key: 'stop',
                value: function stop() {
                    clearTimeout(this.scrollTimeout_);
                }
            }]);

            return DragAutoScroll;
        })(_Attribute2['default']);

        DragAutoScroll.ATTRS = {
            /**
       * The delay in ms before an element is scrolled automatically.
       * @type {number}
       * @default 200
       */
            delay: {
                validator: _core['default'].isNumber,
                value: 50
            },

            /**
       * The maximum distance the mouse needs to be from an element before
       * it will be scrolled automatically.
       * @type {number}
       * @default 10
       */
            maxDistance: {
                validator: _core['default'].isNumber,
                value: 20
            },

            /**
       * The number of pixels that will be scrolled each time.
       * @type {number}
       * @default 10
       */
            speed: {
                validator: _core['default'].isNumber,
                value: 20
            }
        };

        module.exports = DragAutoScroll;
    }
);
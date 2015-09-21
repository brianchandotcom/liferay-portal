define(
    "frontend-js-metal-web@1.0.0/metal-position/src/Position",
    ['exports', 'module', 'metal/src/core', 'metal-position/src/Geometry'],
    function (exports, module, _metalSrcCore, _metalPositionSrcGeometry) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        var _core = _interopRequireDefault(_metalSrcCore);

        var _Geometry = _interopRequireDefault(_metalPositionSrcGeometry);

        /**
      * Class with static methods responsible for doing browser position checks.
      */

        var Position = (function () {
            function Position() {
                _classCallCheck(this, Position);
            }

            _createClass(Position, null, [{
                key: 'getClientHeight',

                /**
        * Gets the client height of the specified node. Scroll height is not
        * included.
        * @param {Element|Document|Window=} node
        * @return {number}
        */
                value: function getClientHeight(node) {
                    return this.getClientSize_(node, 'Height');
                }

                /**
        * Gets the client height or width of the specified node. Scroll height is
        * not included.
        * @param {Element|Document|Window=} node
        * @param {string} `Width` or `Height` property.
        * @return {number}
        * @protected
        */
            }, {
                key: 'getClientSize_',
                value: function getClientSize_(node, prop) {
                    var el = node;
                    if (_core['default'].isWindow(node)) {
                        el = node.document.documentElement;
                    }
                    if (_core['default'].isDocument(node)) {
                        el = node.documentElement;
                    }
                    return el['client' + prop];
                }

                /**
        * Gets the client width of the specified node. Scroll width is not
        * included.
        * @param {Element|Document|Window=} node
        * @return {number}
        */
            }, {
                key: 'getClientWidth',
                value: function getClientWidth(node) {
                    return this.getClientSize_(node, 'Width');
                }

                /**
        * Gets the region of the element, document or window.
        * @param {Element|Document|Window=} opt_element Optional element to test.
        * @return {!DOMRect} The returned value is a simulated DOMRect object which
        *     is the union of the rectangles returned by getClientRects() for the
        *     element, i.e., the CSS border-boxes associated with the element.
        * @protected
        */
            }, {
                key: 'getDocumentRegion_',
                value: function getDocumentRegion_(opt_element) {
                    var height = this.getHeight(opt_element);
                    var width = this.getWidth(opt_element);
                    return this.makeRegion(height, height, 0, width, 0, width);
                }

                /**
        * Gets the height of the specified node. Scroll height is included.
        * @param {Element|Document|Window=} node
        * @return {number}
        */
            }, {
                key: 'getHeight',
                value: function getHeight(node) {
                    return this.getSize_(node, 'Height');
                }

                /**
        * Gets the size of an element and its position relative to the viewport.
        * @param {!Document|Element|Window} node
        * @param {boolean=} opt_includeScroll Flag indicating if the document scroll
        *   position should be considered in the element's region coordinates. Defaults
        *   to false.
        * @return {!DOMRect} The returned value is a DOMRect object which is the
        *     union of the rectangles returned by getClientRects() for the element,
        *     i.e., the CSS border-boxes associated with the element.
        */
            }, {
                key: 'getRegion',
                value: function getRegion(node, opt_includeScroll) {
                    if (_core['default'].isDocument(node) || _core['default'].isWindow(node)) {
                        return this.getDocumentRegion_(node);
                    }
                    return this.makeRegionFromBoundingRect_(node.getBoundingClientRect(), opt_includeScroll);
                }

                /**
        * Gets the scroll left position of the specified node.
        * @param {Element|Document|Window=} node
        * @return {number}
        */
            }, {
                key: 'getScrollLeft',
                value: function getScrollLeft(node) {
                    if (_core['default'].isWindow(node)) {
                        return node.pageXOffset;
                    }
                    if (_core['default'].isDocument(node)) {
                        return node.defaultView.pageXOffset;
                    }
                    return node.scrollLeft;
                }

                /**
        * Gets the scroll top position of the specified node.
        * @param {Element|Document|Window=} node
        * @return {number}
        */
            }, {
                key: 'getScrollTop',
                value: function getScrollTop(node) {
                    if (_core['default'].isWindow(node)) {
                        return node.pageYOffset;
                    }
                    if (_core['default'].isDocument(node)) {
                        return node.defaultView.pageYOffset;
                    }
                    return node.scrollTop;
                }

                /**
        * Gets the height or width of the specified node. Scroll height is
        * included.
        * @param {Element|Document|Window=} node
        * @param {string} `Width` or `Height` property.
        * @return {number}
        * @protected
        */
            }, {
                key: 'getSize_',
                value: function getSize_(node, prop) {
                    if (_core['default'].isWindow(node)) {
                        return this.getClientSize_(node, prop);
                    }
                    if (_core['default'].isDocument(node)) {
                        var docEl = node.documentElement;
                        return Math.max(node.body['scroll' + prop], docEl['scroll' + prop], node.body['offset' + prop], docEl['offset' + prop], docEl['client' + prop]);
                    }
                    return Math.max(node['client' + prop], node['scroll' + prop], node['offset' + prop]);
                }

                /**
        * Gets the width of the specified node. Scroll width is included.
        * @param {Element|Document|Window=} node
        * @return {number}
        */
            }, {
                key: 'getWidth',
                value: function getWidth(node) {
                    return this.getSize_(node, 'Width');
                }

                /**
        * Tests if a region intersects with another.
        * @param {DOMRect} r1
        * @param {DOMRect} r2
        * @return {boolean}
        */
            }, {
                key: 'intersectRegion',
                value: function intersectRegion(r1, r2) {
                    return _Geometry['default'].intersectRect(r1.top, r1.left, r1.bottom, r1.right, r2.top, r2.left, r2.bottom, r2.right);
                }

                /**
        * Tests if a region is inside another.
        * @param {DOMRect} r1
        * @param {DOMRect} r2
        * @return {boolean}
        */
            }, {
                key: 'insideRegion',
                value: function insideRegion(r1, r2) {
                    return r2.top >= r1.top && r2.bottom <= r1.bottom && r2.right <= r1.right && r2.left >= r1.left;
                }

                /**
        * Tests if a region is inside viewport region.
        * @param {DOMRect} region
        * @return {boolean}
        */
            }, {
                key: 'insideViewport',
                value: function insideViewport(region) {
                    return this.insideRegion(this.getRegion(window), region);
                }

                /**
        * Computes the intersection region between two regions.
        * @param {DOMRect} r1
        * @param {DOMRect} r2
        * @return {?DOMRect} Intersection region or null if regions doesn't
        *     intersects.
        */
            }, {
                key: 'intersection',
                value: function intersection(r1, r2) {
                    if (!this.intersectRegion(r1, r2)) {
                        return null;
                    }
                    var bottom = Math.min(r1.bottom, r2.bottom);
                    var right = Math.min(r1.right, r2.right);
                    var left = Math.max(r1.left, r2.left);
                    var top = Math.max(r1.top, r2.top);
                    return this.makeRegion(bottom, bottom - top, left, right, top, right - left);
                }

                /**
        * Makes a region object. It's a writable version of DOMRect.
        * @param {number} bottom
        * @param {number} height
        * @param {number} left
        * @param {number} right
        * @param {number} top
        * @param {number} width
        * @return {!DOMRect} The returned value is a DOMRect object which is the
        *     union of the rectangles returned by getClientRects() for the element,
        *     i.e., the CSS border-boxes associated with the element.
        */
            }, {
                key: 'makeRegion',
                value: function makeRegion(bottom, height, left, right, top, width) {
                    return {
                        bottom: bottom,
                        height: height,
                        left: left,
                        right: right,
                        top: top,
                        width: width
                    };
                }

                /**
        * Makes a region from a DOMRect result from `getBoundingClientRect`.
        * @param  {!DOMRect} The returned value is a DOMRect object which is the
        *     union of the rectangles returned by getClientRects() for the element,
        *     i.e., the CSS border-boxes associated with the element.
        * @param {boolean=} opt_includeScroll Flag indicating if the document scroll
        *   position should be considered in the element's region coordinates. Defaults
        *   to false.
        * @return {DOMRect} Writable version of DOMRect.
        * @protected
        */
            }, {
                key: 'makeRegionFromBoundingRect_',
                value: function makeRegionFromBoundingRect_(rect, opt_includeScroll) {
                    var deltaX = opt_includeScroll ? Position.getScrollLeft(document) : 0;
                    var deltaY = opt_includeScroll ? Position.getScrollTop(document) : 0;
                    return this.makeRegion(rect.bottom + deltaY, rect.height, rect.left + deltaX, rect.right + deltaX, rect.top + deltaY, rect.width);
                }

                /**
        * Checks if the given point coordinates are inside a region.
        * @param {number} x
        * @param {number} y
        * @param {!Object} region
        * @return {boolean}
        */
            }, {
                key: 'pointInsideRegion',
                value: function pointInsideRegion(x, y, region) {
                    return Position.insideRegion(region, Position.makeRegion(y, 0, x, x, y, 0));
                }
            }]);

            return Position;
        })();

        module.exports = Position;
    }
);
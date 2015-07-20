define(['exports', 'module', 'metal/src/core', 'metal-position/src/Geometry'], function (exports, module, _metalSrcCore, _metalPositionSrcGeometry) {
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
       * @return {Number}
       */
      value: function getClientHeight(node) {
        return this.getClientSize_(node, 'Height');
      }
    }, {
      key: 'getClientSize_',

      /**
       * Gets the client height or width of the specified node. Scroll height is
       * not included.
       * @param {Element|Document|Window=} node
       * @param {string} `Width` or `Height` property.
       * @return {Number}
       * @protected
       */
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
    }, {
      key: 'getClientWidth',

      /**
       * Gets the client width of the specified node. Scroll width is not
       * included.
       * @param {Element|Document|Window=} node
       * @return {Number}
       */
      value: function getClientWidth(node) {
        return this.getClientSize_(node, 'Width');
      }
    }, {
      key: 'getDocumentRegion_',

      /**
       * Gets the region of the element, document or window.
       * @param {Element|Document|Window=} opt_element Optional element to test.
       * @return {!DOMRect} The returned value is a simulated DOMRect object which
       *     is the union of the rectangles returned by getClientRects() for the
       *     element, i.e., the CSS border-boxes associated with the element.
       * @protected
       */
      value: function getDocumentRegion_(opt_element) {
        var height = this.getHeight(opt_element);
        var width = this.getWidth(opt_element);
        return this.makeRegion(height, height, 0, width, 0, width);
      }
    }, {
      key: 'getHeight',

      /**
       * Gets the height of the specified node. Scroll height is included.
       * @param {Element|Document|Window=} node
       * @return {Number}
       */
      value: function getHeight(node) {
        return this.getSize_(node, 'Height');
      }
    }, {
      key: 'getRegion',

      /**
       * Gets the size of an element and its position relative to the viewport.
       * @param {!Document|Element|Window} node
       * @return {!DOMRect} The returned value is a DOMRect object which is the
       *     union of the rectangles returned by getClientRects() for the element,
       *     i.e., the CSS border-boxes associated with the element.
       */
      value: function getRegion(node) {
        if (_core['default'].isDocument(node) || _core['default'].isWindow(node)) {
          return this.getDocumentRegion_(node);
        }
        return this.makeRegionFromBoundingRect_(node.getBoundingClientRect());
      }
    }, {
      key: 'getScrollLeft',

      /**
       * Gets the scroll left position of the specified node.
       * @param {Element|Document|Window=} node
       * @return {Number}
       */
      value: function getScrollLeft(node) {
        if (_core['default'].isWindow(node)) {
          return node.pageXOffset;
        }
        if (_core['default'].isDocument(node)) {
          return node.defaultView.pageXOffset;
        }
        return node.scrollLeft;
      }
    }, {
      key: 'getScrollTop',

      /**
       * Gets the scroll top position of the specified node.
       * @param {Element|Document|Window=} node
       * @return {Number}
       */
      value: function getScrollTop(node) {
        if (_core['default'].isWindow(node)) {
          return node.pageYOffset;
        }
        if (_core['default'].isDocument(node)) {
          return node.defaultView.pageYOffset;
        }
        return node.scrollTop;
      }
    }, {
      key: 'getSize_',

      /**
       * Gets the height or width of the specified node. Scroll height is
       * included.
       * @param {Element|Document|Window=} node
       * @param {string} `Width` or `Height` property.
       * @return {Number}
       * @protected
       */
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
    }, {
      key: 'getWidth',

      /**
       * Gets the width of the specified node. Scroll width is included.
       * @param {Element|Document|Window=} node
       * @return {Number}
       */
      value: function getWidth(node) {
        return this.getSize_(node, 'Width');
      }
    }, {
      key: 'intersectRegion',

      /**
       * Tests if a region intersects with another.
       * @param {DOMRect} r1
       * @param {DOMRect} r2
       * @return {boolean}
       */
      value: function intersectRegion(r1, r2) {
        return _Geometry['default'].intersectRect(r1.top, r1.left, r1.bottom, r1.right, r2.top, r2.left, r2.bottom, r2.right);
      }
    }, {
      key: 'insideRegion',

      /**
       * Tests if a region is inside another.
       * @param {DOMRect} r1
       * @param {DOMRect} r2
       * @return {boolean}
       */
      value: function insideRegion(r1, r2) {
        return r2.top >= r1.top && r2.bottom <= r1.bottom && r2.right <= r1.right && r2.left >= r1.left;
      }
    }, {
      key: 'insideViewport',

      /**
       * Tests if a region is inside viewport region.
       * @param {DOMRect} region
       * @return {boolean}
       */
      value: function insideViewport(region) {
        return this.insideRegion(this.getRegion(window), region);
      }
    }, {
      key: 'intersection',

      /**
       * Computes the intersection region between two regions.
       * @param {DOMRect} r1
       * @param {DOMRect} r2
       * @return {?DOMRect} Intersection region or null if regions doesn't
       *     intersects.
       */
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
    }, {
      key: 'makeRegion',

      /**
       * Makes a region object. It's a writable version of DOMRect.
       * @param {Number} bottom
       * @param {Number} height
       * @param {Number} left
       * @param {Number} right
       * @param {Number} top
       * @param {Number} width
       * @return {!DOMRect} The returned value is a DOMRect object which is the
       *     union of the rectangles returned by getClientRects() for the element,
       *     i.e., the CSS border-boxes associated with the element.
       */
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
    }, {
      key: 'makeRegionFromBoundingRect_',

      /**
       * Makes a region from a DOMRect result from `getBoundingClientRect`.
       * @param  {!DOMRect} The returned value is a DOMRect object which is the
       *     union of the rectangles returned by getClientRects() for the element,
       *     i.e., the CSS border-boxes associated with the element.
       * @return {DOMRect} Writable version of DOMRect.
       * @protected
       */
      value: function makeRegionFromBoundingRect_(rect) {
        return this.makeRegion(rect.bottom, rect.height, rect.left, rect.right, rect.top, rect.width);
      }
    }]);

    return Position;
  })();

  module.exports = Position;
});
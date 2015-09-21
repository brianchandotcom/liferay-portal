define(
    "frontend-js-metal-web@1.0.0/metal-position/src/Align",
    ['exports', 'module', 'metal-position/src/Position'],
    function (exports, module, _metalPositionSrcPosition) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        var _Position = _interopRequireDefault(_metalPositionSrcPosition);

        /**
      * Align utility. Computes region or best region to align an element with
      * another. Regions are relative to viewport, make sure to use element with
      * position fixed, or position absolute when the element first positioned
      * parent is the body element.
      */

        var Align = (function () {
            function Align() {
                _classCallCheck(this, Align);
            }

            /**
       * Represents the `Align.Top` constant.
       * @type {number}
       * @default 0
       * @static
       */

            _createClass(Align, null, [{
                key: 'align',

                /**
        * Aligns the element with the best region around alignElement. The best
        * region is defined by clockwise rotation starting from the specified
        * `position`. The element is always aligned in the middle of alignElement
        * axis.
        * @param {!Element} element Element to be aligned.
        * @param {!Element} alignElement Element to align with.
        * @param {Align.Top|Align.Right|Align.Bottom|Align.Left} pos
        *     The initial position to try. Options `Align.Top`, `Align.Right`,
        *     `Align.Bottom`, `Align.Left`.
        * @static
        */
                value: function align(element, alignElement, position) {
                    var bestRegion = this.getAlignBestRegion(element, alignElement, position);

                    var computedStyle = window.getComputedStyle(element, null);
                    if (computedStyle.getPropertyValue('position') !== 'fixed') {
                        bestRegion.top += window.pageYOffset;
                        bestRegion.left += window.pageXOffset;

                        var offsetParent = element;
                        while (offsetParent = offsetParent.offsetParent) {
                            bestRegion.top -= offsetParent.offsetTop;
                            bestRegion.left -= offsetParent.offsetLeft;
                        }
                    }

                    element.style.top = bestRegion.top + 'px';
                    element.style.left = bestRegion.left + 'px';
                }

                /**
        * Returns the best region to align element with alignElement. The best
        * region is defined by clockwise rotation starting from the specified
        * `position`. The element is always aligned in the middle of alignElement
        * axis.
        * @param {!Element} element Element to be aligned.
        * @param {!Element} alignElement Element to align with.
        * @param {Align.Top|Align.Right|Align.Bottom|Align.Left} pos
        *     The initial position to try. Options `Align.Top`, `Align.Right`,
        *     `Align.Bottom`, `Align.Left`.
        * @return {DOMRect} Best region to align element.
        * @static
        */
            }, {
                key: 'getAlignBestRegion',
                value: function getAlignBestRegion(element, alignElement, position) {
                    var bestArea = 0;
                    var bestPosition = position;
                    var bestRegion = this.getAlignRegion(element, alignElement, bestPosition);
                    var tryPosition = bestPosition;
                    var tryRegion = bestRegion;
                    var viewportRegion = _Position['default'].getRegion(window);

                    for (var i = 0; i < 4;) {
                        if (_Position['default'].intersectRegion(viewportRegion, tryRegion)) {
                            var visibleRegion = _Position['default'].intersection(viewportRegion, tryRegion);
                            var area = visibleRegion.width * visibleRegion.height;
                            if (area > bestArea) {
                                bestArea = area;
                                bestRegion = tryRegion;
                                bestPosition = tryPosition;
                            }
                            if (_Position['default'].insideViewport(tryRegion)) {
                                break;
                            }
                        }
                        tryPosition = (position + ++i) % 4;
                        tryRegion = this.getAlignRegion(element, alignElement, tryPosition);
                    }

                    return bestRegion;
                }

                /**
        * Returns the region to align element with alignElement. The element is
        * always aligned in the middle of alignElement axis.
        * @param {!Element} element Element to be aligned.
        * @param {!Element} alignElement Element to align with.
        * @param {Align.Top|Align.Right|Align.Bottom|Align.Left} pos
        *     The position to align. Options `Align.Top`, `Align.Right`,
        *     `Align.Bottom`, `Align.Left`.
        * @return {DOMRect} Region to align element.
        * @static
        */
            }, {
                key: 'getAlignRegion',
                value: function getAlignRegion(element, alignElement, position) {
                    var r1 = _Position['default'].getRegion(alignElement);
                    var r2 = _Position['default'].getRegion(element);
                    var top = 0;
                    var left = 0;

                    switch (position) {
                        case Align.Top:
                            top = r1.top - r2.height;
                            left = r1.left + r1.width / 2 - r2.width / 2;
                            break;
                        case Align.Right:
                            top = r1.top + r1.height / 2 - r2.height / 2;
                            left = r1.left + r1.width;
                            break;
                        case Align.Bottom:
                            top = r1.bottom;
                            left = r1.left + r1.width / 2 - r2.width / 2;
                            break;
                        case Align.Left:
                            top = r1.top + r1.height / 2 - r2.height / 2;
                            left = r1.left - r2.width;
                            break;
                    }

                    return {
                        bottom: top + r2.height,
                        height: r2.height,
                        left: left,
                        right: left + r2.width,
                        top: top,
                        width: r2.width
                    };
                }

                /**
        * Checks if specified value is a valid position. Options `Align.Top`,
        *     `Align.Right`, `Align.Bottom`, `Align.Left`.
        * @param {Align.Top|Align.Right|Align.Bottom|Align.Left} val
        * @return {boolean} Returns true if value is a valid position.
        * @static
        */
            }, {
                key: 'isValidPosition',
                value: function isValidPosition(val) {
                    return 0 <= val && val <= 3;
                }
            }]);

            return Align;
        })();

        Align.Top = 0;

        /**
      * Represents the `Align.Right` constant.
      * @type {number}
      * @default 1
      * @static
      */
        Align.Right = 1;

        /**
      * Represents the `Align.Bottom` constant.
      * @type {number}
      * @default 2
      * @static
      */
        Align.Bottom = 2;

        /**
      * Represents the `Align.Left` constant.
      * @type {number}
      * @default 3
      * @static
      */
        Align.Left = 3;

        module.exports = Align;
    }
);
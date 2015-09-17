define(
    "frontend-js-metal-web@1.0.0/crystal-scrollspy/src/Scrollspy",
    ['exports', 'module', 'metal/src/core', 'metal/src/dom/dom', 'metal/src/attribute/Attribute', 'metal-position/src/Position', 'metal-jquery-adapter/src/JQueryAdapter'],
    function (exports, module, _metalSrcCore, _metalSrcDomDom, _metalSrcAttributeAttribute, _metalPositionSrcPosition, _metalJqueryAdapterSrcJQueryAdapter) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

        var _core = _interopRequireDefault(_metalSrcCore);

        var _dom = _interopRequireDefault(_metalSrcDomDom);

        var _Attribute2 = _interopRequireDefault(_metalSrcAttributeAttribute);

        var _Position = _interopRequireDefault(_metalPositionSrcPosition);

        /**
      * Scrollspy utility.
      */

        var _JQueryAdapter = _interopRequireDefault(_metalJqueryAdapterSrcJQueryAdapter);

        var Scrollspy = (function (_Attribute) {
            _inherits(Scrollspy, _Attribute);

            /**
       * @inheritDoc
       */

            function Scrollspy(opt_config) {
                _classCallCheck(this, Scrollspy);

                _get(Object.getPrototypeOf(Scrollspy.prototype), 'constructor', this).call(this, opt_config);

                /**
        * Holds the active index.
        * @type {Number}
        * @private
        * @default -1
        */
                this.activeIndex = -1;

                /**
        * Holds the regions cache.
        * @type {Object}
        * @private
        * @default []
        */
                this.regions = [];

                /**
        * Holds event handle that listens scroll shared event emitter proxy.
        * @type {EventHandle}
        * @protected
        */
                this.scrollHandle_ = _dom['default'].on(this.scrollElement, 'scroll', this.checkPosition.bind(this));

                this.refresh();
                this.on('elementChanged', this.refresh);
                this.on('offsetChanged', this.checkPosition);
                this.on('scrollElementChanged', this.onScrollElementChanged_);
                this.on('selectorChanged', this.refresh);
            }

            /**
       * @inheritDoc
       */

            _createClass(Scrollspy, [{
                key: 'disposeInternal',
                value: function disposeInternal() {
                    this.deactivateAll();
                    this.scrollHandle_.dispose();
                    _get(Object.getPrototypeOf(Scrollspy.prototype), 'disposeInternal', this).call(this);
                }

                /**
        * Activates index matching element.
        * @param {Number} index
        */
            }, {
                key: 'activate',
                value: function activate(index) {
                    if (this.activeIndex >= 0) {
                        this.deactivate(this.activeIndex);
                    }
                    this.activeIndex = index;
                    _dom['default'].addClasses(this.resolveElement(this.regions[index].link), this.activeClass);
                }

                /**
        * Checks position of elements and activate the one in region.
        */
            }, {
                key: 'checkPosition',
                value: function checkPosition() {
                    var scrollHeight = this.getScrollHeight_();
                    var scrollTop = _Position['default'].getScrollTop(this.scrollElement);

                    if (scrollHeight < scrollTop + this.offset) {
                        this.activate(this.regions.length - 1);
                        return;
                    }

                    var index = this.findBestRegionAt_(scrollTop);
                    if (index !== this.activeIndex) {
                        if (index === -1) {
                            this.deactivateAll();
                        } else {
                            this.activate(index);
                        }
                    }
                }

                /**
        * Deactivates index matching element.
        * @param {Number} index
        */
            }, {
                key: 'deactivate',
                value: function deactivate(index) {
                    _dom['default'].removeClasses(this.resolveElement(this.regions[index].link), this.activeClass);
                }

                /**
        * Deactivates all elements.
        */
            }, {
                key: 'deactivateAll',
                value: function deactivateAll() {
                    for (var i = 0; i < this.regions.length; i++) {
                        this.deactivate(i);
                    }
                    this.activeIndex = -1;
                }

                /**
        * Finds best region to activate.
        * @param {number} scrollTop The scrollTop to use as reference.
        * @return {number} The index of best region found.
        */
            }, {
                key: 'findBestRegionAt_',
                value: function findBestRegionAt_(scrollTop) {
                    var index = -1;
                    var origin = scrollTop + this.offset + this.scrollElementRegion_.top;
                    if (this.regions.length > 0 && origin >= this.regions[0].top) {
                        for (var i = 0; i < this.regions.length; i++) {
                            var region = this.regions[i];
                            var lastRegion = i === this.regions.length - 1;
                            if (origin >= region.top && (lastRegion || origin < this.regions[i + 1].top)) {
                                index = i;
                                break;
                            }
                        }
                    }
                    return index;
                }

                /**
        * Gets the scroll height of `scrollElement`.
        * @return {Number}
        */
            }, {
                key: 'getScrollHeight_',
                value: function getScrollHeight_() {
                    var scrollHeight = _Position['default'].getHeight(this.scrollElement);
                    scrollHeight += this.scrollElementRegion_.top;
                    scrollHeight -= _Position['default'].getClientHeight(this.scrollElement);
                    return scrollHeight;
                }

                /**
        * Fired when the value of the `scrollElement` attribute changes.
        * Refreshes the spy and updates the event handler to listen to the new scroll element.
        * @param {Element} scrollElement
        * @protected
        */
            }, {
                key: 'onScrollElementChanged_',
                value: function onScrollElementChanged_(event) {
                    this.refresh();

                    this.scrollHandle_.dispose();
                    this.scrollHandle_ = _dom['default'].on(event.newVal, 'scroll', this.checkPosition.bind(this));
                }

                /**
        * Refreshes all regions from document. Relevant when spying elements that
        * nodes can be added and removed.
        */
            }, {
                key: 'refresh',
                value: function refresh() {
                    // Removes the "active" class from all current regions.
                    this.deactivateAll();

                    this.scrollElementRegion_ = _Position['default'].getRegion(this.scrollElement);
                    this.scrollHeight_ = this.getScrollHeight_();

                    this.regions = [];
                    var links = this.element.querySelectorAll(this.selector);
                    var scrollTop = _Position['default'].getScrollTop(this.scrollElement);
                    for (var i = 0; i < links.length; ++i) {
                        var link = links[i];
                        if (link.hash && link.hash.length > 1) {
                            var element = document.getElementById(link.hash.substring(1));
                            if (element) {
                                var region = _Position['default'].getRegion(element);
                                this.regions.push({
                                    link: link,
                                    top: region.top + scrollTop,
                                    bottom: region.bottom + scrollTop
                                });
                            }
                        }
                    }
                    this.sortRegions_();

                    // Removes the "active" class from all new regions and then activate the right one for
                    // the current position.
                    this.deactivateAll();
                    this.checkPosition();
                }

                /**
        * Sorts regions from lower to higher on y-axis.
        * @protected
        */
            }, {
                key: 'sortRegions_',
                value: function sortRegions_() {
                    this.regions.sort(function (a, b) {
                        return a.top - b.top;
                    });
                }
            }]);

            return Scrollspy;
        })(_Attribute2['default']);

        Scrollspy.ATTRS = {
            /**
       * Class to be used as active class.
       * @attribute activeClass
       * @type {string}
       */
            activeClass: {
                validator: _core['default'].isString,
                value: 'active'
            },

            /**
       * Function that receives the matching element as argument and return
       * itself. Relevant when the `activeClass` must be applied to a different
       * element, e.g. a parentNode.
       * @type {function}
       * @default core.identityFunction
       */
            resolveElement: {
                validator: _core['default'].isFunction,
                value: _core['default'].identityFunction
            },

            /**
       * The scrollElement element to be used as scrollElement area for affix. The scrollElement is
       * where the scroll event is listened from.
       * @type {Element|Window}
       */
            scrollElement: {
                setter: _dom['default'].toElement,
                value: document
            },

            /**
       * Defines the offset that triggers scrollspy.
       * @type {Number}
       * @default 0
       */
            offset: {
                validator: _core['default'].isNumber,
                value: 0
            },

            /**
       * Element to be used as alignment reference of affix.
       * @type {Element}
       */
            element: {
                setter: _dom['default'].toElement
            },

            /**
       * Selector to query elements inside `element` to be activated.
       * @type {Element}
       * @default 'a'
       */
            selector: {
                validator: _core['default'].isString,
                value: 'a'
            }
        };

        module.exports = Scrollspy;
        _JQueryAdapter['default'].register('scrollspy', Scrollspy);
    }
);
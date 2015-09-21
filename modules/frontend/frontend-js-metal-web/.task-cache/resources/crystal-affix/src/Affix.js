define(
    "frontend-js-metal-web@1.0.0/crystal-affix/src/Affix",
    ['exports', 'module', 'metal/src/core', 'metal/src/dom/dom', 'metal/src/attribute/Attribute', 'metal/src/events/EventEmitter', 'metal/src/events/EventEmitterProxy', 'metal-position/src/Position', 'metal-jquery-adapter/src/JQueryAdapter'],
    function (exports, module, _metalSrcCore, _metalSrcDomDom, _metalSrcAttributeAttribute, _metalSrcEventsEventEmitter, _metalSrcEventsEventEmitterProxy, _metalPositionSrcPosition, _metalJqueryAdapterSrcJQueryAdapter) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

        var _core = _interopRequireDefault(_metalSrcCore);

        var _dom = _interopRequireDefault(_metalSrcDomDom);

        var _Attribute2 = _interopRequireDefault(_metalSrcAttributeAttribute);

        var _EventEmitter = _interopRequireDefault(_metalSrcEventsEventEmitter);

        var _EventEmitterProxy = _interopRequireDefault(_metalSrcEventsEventEmitterProxy);

        var _Position = _interopRequireDefault(_metalPositionSrcPosition);

        /**
      * Affix utility.
      */

        var _JQueryAdapter = _interopRequireDefault(_metalJqueryAdapterSrcJQueryAdapter);

        var Affix = (function (_Attribute) {
            _inherits(Affix, _Attribute);

            /**
       * @inheritDoc
       */

            function Affix(opt_config) {
                _classCallCheck(this, Affix);

                _get(Object.getPrototypeOf(Affix.prototype), 'constructor', this).call(this, opt_config);

                if (!Affix.emitter_) {
                    Affix.emitter_ = new _EventEmitter['default']();
                    Affix.proxy_ = new _EventEmitterProxy['default'](document, Affix.emitter_, null, {
                        scroll: true
                    });
                }

                /**
        * Holds the last position.
        * @type {Position.Bottom|Position.Default|Position.Top}
        * @private
        */
                this.lastPosition_ = null;

                /**
        * Holds event handle that listens scroll shared event emitter proxy.
        * @type {EventHandle}
        * @protected
        */
                this.scrollHandle_ = Affix.emitter_.on('scroll', this.checkPosition.bind(this));

                this.on('elementChanged', this.checkPosition);
                this.on('offsetTopChanged', this.checkPosition);
                this.on('offsetBottomChanged', this.checkPosition);
                this.checkPosition();
            }

            /**
       * Holds positions enum.
       * @enum {string}
       */

            /**
       * @inheritDoc
       */

            _createClass(Affix, [{
                key: 'disposeInternal',
                value: function disposeInternal() {
                    _dom['default'].removeClasses(this.element, Affix.Position.Bottom + ' ' + Affix.Position.Default + ' ' + Affix.Position.Top);
                    this.scrollHandle_.dispose();
                    _get(Object.getPrototypeOf(Affix.prototype), 'disposeInternal', this).call(this);
                }

                /**
        * Synchronize bottom, top and element regions and checks if position has
        * changed. If position has changed syncs position.
        */
            }, {
                key: 'checkPosition',
                value: function checkPosition() {
                    if (this.intersectTopRegion()) {
                        this.syncPosition(Affix.Position.Top);
                    } else if (this.intersectBottomRegion()) {
                        this.syncPosition(Affix.Position.Bottom);
                    } else {
                        this.syncPosition(Affix.Position.Default);
                    }
                }

                /**
        * Whether the element is intersecting with bottom region defined by
        * offsetBottom.
        * @return {boolean}
        */
            }, {
                key: 'intersectBottomRegion',
                value: function intersectBottomRegion() {
                    if (!_core['default'].isDef(this.offsetBottom)) {
                        return false;
                    }
                    var clientHeight = _Position['default'].getHeight(this.scrollElement);
                    var scrollElementClientHeight = _Position['default'].getClientHeight(this.scrollElement);
                    return _Position['default'].getScrollTop(this.scrollElement) + scrollElementClientHeight >= clientHeight - this.offsetBottom;
                }

                /**
        * Whether the element is intersecting with top region defined by
        * offsetTop.
        * @return {boolean}
        */
            }, {
                key: 'intersectTopRegion',
                value: function intersectTopRegion() {
                    if (!_core['default'].isDef(this.offsetTop)) {
                        return false;
                    }
                    return _Position['default'].getScrollTop(this.scrollElement) <= this.offsetTop;
                }

                /**
        * Synchronizes element css classes to match with the specified position.
        * @param {Position.Bottom|Position.Default|Position.Top} position
        */
            }, {
                key: 'syncPosition',
                value: function syncPosition(position) {
                    if (this.lastPosition_ !== position) {
                        _dom['default'].addClasses(this.element, position);
                        _dom['default'].removeClasses(this.element, this.lastPosition_);
                        this.lastPosition_ = position;
                    }
                }
            }]);

            return Affix;
        })(_Attribute2['default']);

        Affix.Position = {
            Top: 'affix-top',
            Bottom: 'affix-bottom',
            Default: 'affix-default'
        };

        Affix.ATTRS = {
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
       * Defines the offset bottom that triggers affix.
       * @type {number}
       */
            offsetTop: {
                validator: _core['default'].isNumber
            },

            /**
       * Defines the offset top that triggers affix.
       * @type {number}
       */
            offsetBottom: {
                validator: _core['default'].isNumber
            },

            /**
       * Element to be used as alignment reference of affix.
       * @type {Element}
       */
            element: {
                setter: _dom['default'].toElement
            }
        };

        module.exports = Affix;
        _JQueryAdapter['default'].register('affix', Affix);
    }
);
define(
    "frontend-js-metal-web@1.0.0/metal/src/events/EventEmitterProxy",
    ['exports', 'module', 'metal/src/core', 'metal/src/dom/dom', 'metal/src/disposable/Disposable'],
    function (exports, module, _metalSrcCore, _metalSrcDomDom, _metalSrcDisposableDisposable) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

        var _core = _interopRequireDefault(_metalSrcCore);

        var _dom = _interopRequireDefault(_metalSrcDomDom);

        var _Disposable2 = _interopRequireDefault(_metalSrcDisposableDisposable);

        /**
      * EventEmitterProxy utility. It's responsible for linking two EventEmitter
      * instances together, emitting events from the first emitter through the
      * second one. That means that listening to a supported event on the target
      * emitter will mean listening to it on the origin emitter as well.
      * @param {EventEmitter | Element} originEmitter Events originated on this emitter
      *   will be fired for the target emitter's listeners as well. Can be either a real
      *   EventEmitter instance or a DOM element.
      * @param {EventEmitter} targetEmitter Event listeners attached to this emitter
      *   will also be triggered when the event is fired by the origin emitter.
      * @param {Object} opt_blacklist Optional blacklist of events that should not be
      *   proxied.
      * @constructor
      * @extends {Disposable}
      */

        var EventEmitterProxy = (function (_Disposable) {
            _inherits(EventEmitterProxy, _Disposable);

            function EventEmitterProxy(originEmitter, targetEmitter, opt_blacklist, opt_whitelist) {
                _classCallCheck(this, EventEmitterProxy);

                _get(Object.getPrototypeOf(EventEmitterProxy.prototype), 'constructor', this).call(this);

                /**
        * Map of events that should not be proxied.
        * @type {Object}
        * @protected
        */
                this.blacklist_ = opt_blacklist || {};

                /**
        * The origin emitter. This emitter's events will be proxied through the
        * target emitter.
        * @type {EventEmitter}
        * @protected
        */
                this.originEmitter_ = originEmitter;

                /**
        * Holds a map of events from the origin emitter that are already being proxied.
        * @type {Object}
        * @protected
        */
                this.proxiedEvents_ = {};

                /**
        * The target emitter. This emitter will emit all events that come from
        * the origin emitter.
        * @type {EventEmitter}
        * @protected
        */
                this.targetEmitter_ = targetEmitter;

                /**
        * Map of events that should be proxied. If whitelist is set blacklist is ignored.
        * @type {Object}
        * @protected
        */
                this.whitelist_ = opt_whitelist;

                this.startProxy_();
            }

            /**
       * @inheritDoc
       */

            _createClass(EventEmitterProxy, [{
                key: 'disposeInternal',
                value: function disposeInternal() {
                    var removeFnName = this.originEmitter_.removeEventListener ? 'removeEventListener' : 'removeListener';
                    for (var event in this.proxiedEvents_) {
                        this.originEmitter_[removeFnName](event, this.proxiedEvents_[event]);
                    }

                    this.proxiedEvents_ = null;
                    this.originEmitter_ = null;
                    this.targetEmitter_ = null;
                }

                /**
        * Proxies the given event from the origin to the target emitter.
        * @param {string} event
        */
            }, {
                key: 'proxyEvent_',
                value: function proxyEvent_(event) {
                    if (!this.shouldProxyEvent_(event)) {
                        return;
                    }

                    var self = this;
                    this.proxiedEvents_[event] = function () {
                        var args = [event].concat(Array.prototype.slice.call(arguments, 0));
                        self.targetEmitter_.emit.apply(self.targetEmitter_, args);
                    };

                    if (_core['default'].isElement(this.originEmitter_) || _core['default'].isDocument(this.originEmitter_)) {
                        _dom['default'].on(this.originEmitter_, event, this.proxiedEvents_[event]);
                    } else {
                        this.originEmitter_.on(event, this.proxiedEvents_[event]);
                    }
                }

                /**
        * Checks if the given event should be proxied.
        * @param {string} event
        * @return {boolean}
        * @protected
        */
            }, {
                key: 'shouldProxyEvent_',
                value: function shouldProxyEvent_(event) {
                    if (this.whitelist_ && !this.whitelist_[event]) {
                        return false;
                    }
                    if (this.blacklist_[event]) {
                        return false;
                    }
                    return !this.proxiedEvents_[event] && (!(this.originEmitter_.removeEventListener || this.originEmitter_.addEventListener) || _dom['default'].supportsEvent(this.originEmitter_, event));
                }

                /**
        * Starts proxying all events from the origin to the target emitter.
        * @protected
        */
            }, {
                key: 'startProxy_',
                value: function startProxy_() {
                    this.targetEmitter_.on('newListener', this.proxyEvent_.bind(this));
                }
            }]);

            return EventEmitterProxy;
        })(_Disposable2['default']);

        module.exports = EventEmitterProxy;
    }
);
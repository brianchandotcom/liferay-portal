define(
    "frontend-js-metal-web@1.0.0/metal/src/events/EventHandle",
    ['exports', 'module', 'metal/src/disposable/Disposable'],
    function (exports, module, _metalSrcDisposableDisposable) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

        var _Disposable2 = _interopRequireDefault(_metalSrcDisposableDisposable);

        /**
      * EventHandle utility. Holds information about an event subscription, and
      * allows removing them easily.
      * EventHandle is a Disposable, but it's important to note that the
      * EventEmitter that created it is not the one responsible for disposing it.
      * That responsibility is for the code that holds a reference to it.
      * @param {!EventEmitter} emitter Emitter the event was subscribed to.
      * @param {string} event The name of the event that was subscribed to.
      * @param {!Function} listener The listener subscribed to the event.
      * @constructor
      * @extends {Disposable}
      */

        var EventHandle = (function (_Disposable) {
            _inherits(EventHandle, _Disposable);

            function EventHandle(emitter, event, listener) {
                _classCallCheck(this, EventHandle);

                _get(Object.getPrototypeOf(EventHandle.prototype), 'constructor', this).call(this);

                /**
        * The EventEmitter instance that the event was subscribed to.
        * @type {EventEmitter}
        * @protected
        */
                this.emitter_ = emitter;

                /**
        * The name of the event that was subscribed to.
        * @type {string}
        * @protected
        */
                this.event_ = event;

                /**
        * The listener subscribed to the event.
        * @type {Function}
        * @protected
        */
                this.listener_ = listener;
            }

            /**
       * Disposes of this instance's object references.
       * @override
       */

            _createClass(EventHandle, [{
                key: 'disposeInternal',
                value: function disposeInternal() {
                    this.removeListener();
                    this.emitter_ = null;
                    this.listener_ = null;
                }

                /**
        * Removes the listener subscription from the emitter.
        */
            }, {
                key: 'removeListener',
                value: function removeListener() {
                    if (!this.emitter_.isDisposed()) {
                        this.emitter_.removeListener(this.event_, this.listener_);
                    }
                }
            }]);

            return EventHandle;
        })(_Disposable2['default']);

        module.exports = EventHandle;
    }
);
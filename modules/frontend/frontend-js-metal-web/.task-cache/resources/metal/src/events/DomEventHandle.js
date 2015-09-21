define(
    "frontend-js-metal-web@1.0.0/metal/src/events/DomEventHandle",
    ['exports', 'module', 'metal/src/events/EventHandle'],
    function (exports, module, _metalSrcEventsEventHandle) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

        var _EventHandle2 = _interopRequireDefault(_metalSrcEventsEventHandle);

        /**
      * This is a special EventHandle, that is responsible for dom events, instead
      * of EventEmitter events.
      * @extends {EventHandle}
      */

        var DomEventHandle = (function (_EventHandle) {
            _inherits(DomEventHandle, _EventHandle);

            /**
       * The constructor for `DomEventHandle`.
       * @param {!EventEmitter} emitter Emitter the event was subscribed to.
       * @param {string} event The name of the event that was subscribed to.
       * @param {!Function} listener The listener subscribed to the event.
       * @param {boolean} opt_capture Flag indicating if listener should be triggered
       *   during capture phase, instead of during the bubbling phase. Defaults to false.
       * @constructor
       */

            function DomEventHandle(emitter, event, listener, opt_capture) {
                _classCallCheck(this, DomEventHandle);

                _get(Object.getPrototypeOf(DomEventHandle.prototype), 'constructor', this).call(this, emitter, event, listener);
                this.capture_ = opt_capture;
            }

            /**
       * @inheritDoc
       */

            _createClass(DomEventHandle, [{
                key: 'removeListener',
                value: function removeListener() {
                    this.emitter_.removeEventListener(this.event_, this.listener_, this.capture_);
                }
            }]);

            return DomEventHandle;
        })(_EventHandle2['default']);

        module.exports = DomEventHandle;
    }
);
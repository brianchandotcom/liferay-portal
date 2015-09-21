define(
    "frontend-js-metal-web@1.0.0/metal-drag-drop/src/helpers/DragScrollDelta",
    ['exports', 'module', 'metal/src/dom/dom', 'metal/src/events/EventEmitter', 'metal/src/events/EventHandler', 'metal-position/src/Position'],
    function (exports, module, _metalSrcDomDom, _metalSrcEventsEventEmitter, _metalSrcEventsEventHandler, _metalPositionSrcPosition) {
        'use strict';

        var _createClass = (function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ('value' in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; })();

        var _get = function get(_x, _x2, _x3) { var _again = true; _function: while (_again) { var object = _x, property = _x2, receiver = _x3; desc = parent = getter = undefined; _again = false; if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { _x = parent; _x2 = property; _x3 = receiver; _again = true; continue _function; } } else if ('value' in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } } };

        function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { 'default': obj }; }

        function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError('Cannot call a class as a function'); } }

        function _inherits(subClass, superClass) { if (typeof superClass !== 'function' && superClass !== null) { throw new TypeError('Super expression must either be null or a function, not ' + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

        var _dom = _interopRequireDefault(_metalSrcDomDom);

        var _EventEmitter2 = _interopRequireDefault(_metalSrcEventsEventEmitter);

        var _EventHandler = _interopRequireDefault(_metalSrcEventsEventHandler);

        var _Position = _interopRequireDefault(_metalPositionSrcPosition);

        /**
      * Helper called by the `Drag` instance that emits an event whenever
      * the scroll position of the given containers change.
      */

        var DragScrollDelta = (function (_EventEmitter) {
            _inherits(DragScrollDelta, _EventEmitter);

            /**
       * @inheritDoc
       */

            function DragScrollDelta() {
                _classCallCheck(this, DragScrollDelta);

                _get(Object.getPrototypeOf(DragScrollDelta.prototype), 'constructor', this).call(this);
                /**
        * `EventHandler` for the scroll events.
        * @type {EventHandler}
        * @protected
        */
                this.handler_ = new _EventHandler['default']();

                /**
        * The scroll positions for the scroll elements that are being listened to.
        * @type {Array}
        * @protected
        */
                this.scrollPositions_ = [];
            }

            /**
       * @inheritDoc
       */

            _createClass(DragScrollDelta, [{
                key: 'disposeInternal',
                value: function disposeInternal() {
                    _get(Object.getPrototypeOf(DragScrollDelta.prototype), 'disposeInternal', this).call(this);
                    this.stop();
                    this.handler_ = null;
                }

                /**
        * Handles a "scroll" event, emitting a "scrollDelta" event with the
        * difference between the previous and new values.
        * @param {number} index
        * @param {!Event} event
        * @protected
        */
            }, {
                key: 'handleScroll_',
                value: function handleScroll_(index, event) {
                    var newPosition = {
                        scrollLeft: _Position['default'].getScrollLeft(event.currentTarget),
                        scrollTop: _Position['default'].getScrollTop(event.currentTarget)
                    };
                    var position = this.scrollPositions_[index];
                    this.scrollPositions_[index] = newPosition;

                    this.emit('scrollDelta', {
                        deltaX: newPosition.scrollLeft - position.scrollLeft,
                        deltaY: newPosition.scrollTop - position.scrollTop
                    });
                }

                /**
        * Starts listening to scroll changes on the given elements that contain
        * the current drag node.
        * @param {!Element} dragNode
        * @param {!Array<!Element>} scrollContainers
        */
            }, {
                key: 'start',
                value: function start(dragNode, scrollContainers) {
                    if (getComputedStyle(dragNode).position === 'fixed') {
                        // If the drag node's position is "fixed", then its coordinates don't need to
                        // be updated when parents are scrolled.
                        return;
                    }

                    for (var i = 0; i < scrollContainers.length; i++) {
                        if (_dom['default'].contains(scrollContainers[i], dragNode)) {
                            this.scrollPositions_.push({
                                scrollLeft: _Position['default'].getScrollLeft(scrollContainers[i]),
                                scrollTop: _Position['default'].getScrollTop(scrollContainers[i])
                            });

                            var index = this.scrollPositions_.length - 1;
                            this.handler_.add(_dom['default'].on(scrollContainers[i], 'scroll', this.handleScroll_.bind(this, index)));
                        }
                    }
                }

                /**
        * Stops listening to scroll changes.
        */
            }, {
                key: 'stop',
                value: function stop() {
                    this.handler_.removeAllListeners();
                    this.scrollPositions_ = [];
                }
            }]);

            return DragScrollDelta;
        })(_EventEmitter2['default']);

        module.exports = DragScrollDelta;
    }
);
define("frontend-js-metal-web@1.0.6/metal-drag-drop/src/helpers/DragScrollDelta", ['exports', 'metal-dom/src/all/dom', 'metal-events/src/events', 'metal-position/src/all/position'], function (exports, _dom, _events, _position) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _dom2 = _interopRequireDefault(_dom);

	var _position2 = _interopRequireDefault(_position);

	function _interopRequireDefault(obj) {
		return obj && obj.__esModule ? obj : {
			default: obj
		};
	}

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	function _possibleConstructorReturn(self, call) {
		if (!self) {
			throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
		}

		return call && (typeof call === "object" || typeof call === "function") ? call : self;
	}

	function _inherits(subClass, superClass) {
		if (typeof superClass !== "function" && superClass !== null) {
			throw new TypeError("Super expression must either be null or a function, not " + typeof superClass);
		}

		subClass.prototype = Object.create(superClass && superClass.prototype, {
			constructor: {
				value: subClass,
				enumerable: false,
				writable: true,
				configurable: true
			}
		});
		if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass;
	}

	var DragScrollDelta = function (_EventEmitter) {
		_inherits(DragScrollDelta, _EventEmitter);

		/**
   * @inheritDoc
   */

		function DragScrollDelta() {
			_classCallCheck(this, DragScrollDelta);

			var _this = _possibleConstructorReturn(this, _EventEmitter.call(this));

			/**
    * `EventHandler` for the scroll events.
    * @type {EventHandler}
    * @protected
    */
			_this.handler_ = new _events.EventHandler();

			/**
    * The scroll positions for the scroll elements that are being listened to.
    * @type {Array}
    * @protected
    */
			_this.scrollPositions_ = [];
			return _this;
		}

		/**
   * @inheritDoc
   */


		DragScrollDelta.prototype.disposeInternal = function disposeInternal() {
			_EventEmitter.prototype.disposeInternal.call(this);
			this.stop();
			this.handler_ = null;
		};

		DragScrollDelta.prototype.handleScroll_ = function handleScroll_(index, event) {
			var newPosition = {
				scrollLeft: _position2.default.getScrollLeft(event.currentTarget),
				scrollTop: _position2.default.getScrollTop(event.currentTarget)
			};
			var position = this.scrollPositions_[index];
			this.scrollPositions_[index] = newPosition;

			this.emit('scrollDelta', {
				deltaX: newPosition.scrollLeft - position.scrollLeft,
				deltaY: newPosition.scrollTop - position.scrollTop
			});
		};

		DragScrollDelta.prototype.start = function start(dragNode, scrollContainers) {
			if (getComputedStyle(dragNode).position === 'fixed') {
				// If the drag node's position is "fixed", then its coordinates don't need to
				// be updated when parents are scrolled.
				return;
			}

			for (var i = 0; i < scrollContainers.length; i++) {
				if (_dom2.default.contains(scrollContainers[i], dragNode)) {
					this.scrollPositions_.push({
						scrollLeft: _position2.default.getScrollLeft(scrollContainers[i]),
						scrollTop: _position2.default.getScrollTop(scrollContainers[i])
					});

					var index = this.scrollPositions_.length - 1;
					this.handler_.add(_dom2.default.on(scrollContainers[i], 'scroll', this.handleScroll_.bind(this, index)));
				}
			}
		};

		DragScrollDelta.prototype.stop = function stop() {
			this.handler_.removeAllListeners();
			this.scrollPositions_ = [];
		};

		return DragScrollDelta;
	}(_events.EventEmitter);

	exports.default = DragScrollDelta;
});
//# sourceMappingURL=DragScrollDelta.js.map
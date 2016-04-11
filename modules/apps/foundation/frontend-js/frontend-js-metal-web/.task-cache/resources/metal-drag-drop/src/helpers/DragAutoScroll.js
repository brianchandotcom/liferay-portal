define("frontend-js-metal-web@1.0.6/metal-drag-drop/src/helpers/DragAutoScroll", ['exports', 'metal/src/metal', 'metal-state/src/State', 'metal-position/src/all/position'], function (exports, _metal, _State2, _position) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

	var _State3 = _interopRequireDefault(_State2);

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

	var DragAutoScroll = function (_State) {
		_inherits(DragAutoScroll, _State);

		/**
   * @inheritDoc
   */

		function DragAutoScroll(opt_config) {
			_classCallCheck(this, DragAutoScroll);

			var _this = _possibleConstructorReturn(this, _State.call(this, opt_config));

			/**
    * The handler for the current call to `setTimeout`.
    * @type {?number}
    * @protected
    */
			_this.scrollTimeout_ = null;
			return _this;
		}

		/**
   * @inheritDoc
   */


		DragAutoScroll.prototype.disposeInternal = function disposeInternal() {
			_State.prototype.disposeInternal.call(this);
			this.stop();
		};

		DragAutoScroll.prototype.getRegionWithoutScroll_ = function getRegionWithoutScroll_(scrollContainer) {
			if (_metal2.default.isDocument(scrollContainer)) {
				var height = window.innerHeight;
				var width = window.innerWidth;
				return _position2.default.makeRegion(height, height, 0, width, 0, width);
			} else {
				return _position2.default.getRegion(scrollContainer);
			}
		};

		DragAutoScroll.prototype.scroll = function scroll(scrollContainers, mouseX, mouseY) {
			this.stop();
			this.scrollTimeout_ = setTimeout(this.scrollInternal_.bind(this, scrollContainers, mouseX, mouseY), this.delay);
		};

		DragAutoScroll.prototype.scrollElement_ = function scrollElement_(element, deltaX, deltaY) {
			if (_metal2.default.isDocument(element)) {
				window.scrollBy(deltaX, deltaY);
			} else {
				element.scrollTop += deltaY;
				element.scrollLeft += deltaX;
			}
		};

		DragAutoScroll.prototype.scrollInternal_ = function scrollInternal_(scrollContainers, mouseX, mouseY) {
			for (var i = 0; i < scrollContainers.length; i++) {
				var scrollRegion = this.getRegionWithoutScroll_(scrollContainers[i]);
				if (!_position2.default.pointInsideRegion(mouseX, mouseY, scrollRegion)) {
					continue;
				}

				var deltaX = 0;
				var deltaY = 0;
				var scrollTop = _position2.default.getScrollTop(scrollContainers[i]);
				var scrollLeft = _position2.default.getScrollLeft(scrollContainers[i]);
				if (scrollLeft > 0 && Math.abs(mouseX - scrollRegion.left) <= this.maxDistance) {
					deltaX -= this.speed;
				} else if (Math.abs(mouseX - scrollRegion.right) <= this.maxDistance) {
					deltaX += this.speed;
				}
				if (scrollTop > 0 && Math.abs(mouseY - scrollRegion.top) <= this.maxDistance) {
					deltaY -= this.speed;
				} else if (Math.abs(mouseY - scrollRegion.bottom) <= this.maxDistance) {
					deltaY += this.speed;
				}

				if (deltaX || deltaY) {
					this.scrollElement_(scrollContainers[i], deltaX, deltaY);
					this.scroll(scrollContainers, mouseX, mouseY);
					break;
				}
			}
		};

		DragAutoScroll.prototype.stop = function stop() {
			clearTimeout(this.scrollTimeout_);
		};

		return DragAutoScroll;
	}(_State3.default);

	/**
  * State definition.
  * @type {!Object}
  * @static
  */
	DragAutoScroll.STATE = {
		/**
   * The delay in ms before an element is scrolled automatically.
   * @type {number}
   * @default 200
   */
		delay: {
			validator: _metal2.default.isNumber,
			value: 50
		},

		/**
   * The maximum distance the mouse needs to be from an element before
   * it will be scrolled automatically.
   * @type {number}
   * @default 10
   */
		maxDistance: {
			validator: _metal2.default.isNumber,
			value: 20
		},

		/**
   * The number of pixels that will be scrolled each time.
   * @type {number}
   * @default 10
   */
		speed: {
			validator: _metal2.default.isNumber,
			value: 20
		}
	};

	exports.default = DragAutoScroll;
});
//# sourceMappingURL=DragAutoScroll.js.map
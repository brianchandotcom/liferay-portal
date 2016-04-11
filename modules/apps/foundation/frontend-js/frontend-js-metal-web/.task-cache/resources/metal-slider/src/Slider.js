define("frontend-js-metal-web@1.0.6/metal-slider/src/Slider", ['exports', 'metal/src/metal', 'metal-dom/src/all/dom', 'metal-component/src/all/component', 'metal-drag-drop/src/all/drag', 'metal-position/src/all/position', 'metal-soy/src/Soy', './Slider.soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _metal, _dom, _component, _drag, _position, _Soy, _Slider, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

	var _dom2 = _interopRequireDefault(_dom);

	var _component2 = _interopRequireDefault(_component);

	var _position2 = _interopRequireDefault(_position);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _Slider2 = _interopRequireDefault(_Slider);

	var _JQueryAdapter2 = _interopRequireDefault(_JQueryAdapter);

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

	var Slider = function (_Component) {
		_inherits(Slider, _Component);

		function Slider() {
			_classCallCheck(this, Slider);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		Slider.prototype.attached = function attached() {
			/**
    * Manages dragging the rail handle to update the slider value.
    * @type {Drag}
    * @protected
    */
			this.drag_ = new _drag.Drag({
				constrain: this.element.querySelector('.rail'),
				handles: this.element.querySelector('.handle'),
				sources: this.element.querySelector('.rail-handle')
			});

			this.attachDragEvents_();
		};

		Slider.prototype.attachDragEvents_ = function attachDragEvents_() {
			this.drag_.on(_drag.Drag.Events.DRAG, this.updateValueFromDragData_.bind(this));
			this.drag_.on(_drag.Drag.Events.END, this.updateValueFromDragData_.bind(this));
		};

		Slider.prototype.disposeInternal = function disposeInternal() {
			_Component.prototype.disposeInternal.call(this);
			this.drag_.dispose();
		};

		Slider.prototype.onRailMouseDown_ = function onRailMouseDown_(event) {
			if (_dom2.default.hasClass(event.target, 'rail') || _dom2.default.hasClass(event.target, 'rail-active')) {
				this.updateValue_(event.offsetX, 0);
			}
		};

		Slider.prototype.syncMax = function syncMax(newVal) {
			if (newVal < this.value) {
				this.value = newVal;
			} else {
				this.updateHandlePosition_();
			}
		};

		Slider.prototype.syncMin = function syncMin(newVal) {
			if (newVal > this.value) {
				this.value = newVal;
			} else {
				this.updateHandlePosition_();
			}
		};

		Slider.prototype.syncValue = function syncValue() {
			this.updateHandlePosition_();
		};

		Slider.prototype.updateHandlePosition_ = function updateHandlePosition_() {
			if (!this.drag_ || !this.drag_.isDragging()) {
				var positionValue = 100 * (this.value - this.min) / (this.max - this.min) + '%';
				this.element.querySelector('.rail-handle').style.left = positionValue;
			}
		};

		Slider.prototype.updateValue_ = function updateValue_(handlePosition, offset) {
			var region = _position2.default.getRegion(this.element);
			this.value = Math.round(offset + handlePosition / region.width * (this.max - this.min));
		};

		Slider.prototype.updateValueFromDragData_ = function updateValueFromDragData_(data) {
			this.updateValue_(data.relativeX, this.min);
		};

		return Slider;
	}(_component2.default);

	_Soy2.default.register(Slider, _Slider2.default);

	/**
  * `Slider`'s state definition.
  */
	Slider.STATE = {
		/**
   * Name of the hidden input field that holds the slider value. Useful when slider is embedded
   * inside a form so it can automatically send its value.
   * @type {string}
   */
		inputName: {
			validator: _metal2.default.isString
		},

		/**
   * Defines the maximum value handled by the slider.
   * @type {number}
   * @default 100
   */
		max: {
			value: 100
		},

		/**
   * Defines the minimum value handled by the slider.
   * @type {number}
   * @default 0
   */
		min: {
			value: 0
		},

		/**
   * Defines the currently selected value on the slider.
   * @type {number}
   * @default 0
   */
		value: {
			validator: function validator(val) {
				return _metal2.default.isNumber(val) && this.min <= val && val <= this.max;
			},
			value: 0
		}
	};

	exports.default = Slider;
	_JQueryAdapter2.default.register('slider', Slider);
});
//# sourceMappingURL=Slider.js.map
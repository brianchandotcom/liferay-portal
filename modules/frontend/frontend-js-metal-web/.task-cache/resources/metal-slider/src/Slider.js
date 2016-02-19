define("frontend-js-metal-web@1.0.0/metal-slider/src/Slider", ['exports', 'metal/src/metal', 'metal-drag-drop/src/all/drag', 'metal-position/src/all/position', './Slider.soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _metal, _drag, _position, _Slider, _JQueryAdapter) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _metal2 = _interopRequireDefault(_metal);

	var _position2 = _interopRequireDefault(_position);

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

	var Slider = function (_SliderBase) {
		_inherits(Slider, _SliderBase);

		/**
   * @inheritDoc
   */

		function Slider(opt_config) {
			_classCallCheck(this, Slider);

			var _this = _possibleConstructorReturn(this, _SliderBase.call(this, opt_config));

			/**
    * Map of different slider DOM elements. Used as a cache to prevent unnecessary dom lookups
    * on succesive queries.
    * @type {Map}
    * @protected
    */
			_this.elements_ = new Map();
			return _this;
		}

		/**
   * @inheritDoc
   */


		Slider.prototype.attached = function attached() {
			/**
    * Manages dragging the rail handle to update the slider value.
    * @type {Drag}
    * @protected
    */
			this.drag_ = new _drag.Drag({
				constrain: this.getElement_('.rail'),
				handles: this.getElement_('.handle'),
				sources: this.getElement_('.rail-handle')
			});

			/**
    * Position and dimensions of the slider element.
    * @type {DOMRect}
    * @protected
    */
			this.elementRegion_ = _position2.default.getRegion(this.element);

			this.attachDragEvents_();
		};

		Slider.prototype.attachDragEvents_ = function attachDragEvents_() {
			this.drag_.on(_drag.Drag.Events.DRAG, this.updateValueFromDragData_.bind(this));
			this.drag_.on(_drag.Drag.Events.END, this.updateValueFromDragData_.bind(this));
		};

		Slider.prototype.disposeInternal = function disposeInternal() {
			_SliderBase.prototype.disposeInternal.call(this);

			this.drag_.dispose();
			this.elements_ = null;
			this.elementRegion_ = null;
		};

		Slider.prototype.getElement_ = function getElement_(query) {
			var element = this.elements_.get(query);

			if (!element) {
				element = this.element.querySelector(query);

				this.elements_.set(query, element);
			}

			return element;
		};

		Slider.prototype.onRailMouseDown_ = function onRailMouseDown_(event) {
			if (event.target === this.getElement_('.rail') || event.target === this.getElement_('.rail-active')) {
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
			var positionValue = 100 * (this.value - this.min) / (this.max - this.min) + '%';

			if (!(this.drag_ && this.drag_.isDragging())) {
				this.getElement_('.rail-handle').style.left = positionValue;
			}

			this.getElement_('.rail-active').style.width = positionValue;
		};

		Slider.prototype.updateValue_ = function updateValue_(handlePosition, offset) {
			this.value = Math.round(offset + handlePosition / this.elementRegion_.width * (this.max - this.min));
		};

		Slider.prototype.updateValueFromDragData_ = function updateValueFromDragData_(data) {
			this.updateValue_(data.relativeX, this.min);
		};

		return Slider;
	}(_Slider2.default);

	Slider.prototype.registerMetalComponent && Slider.prototype.registerMetalComponent(Slider, 'Slider')


	Slider.ATTRS = {
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
   * @default 50
   */
		value: {
			validator: function validator(val) {
				return _metal2.default.isNumber(val) && this.min <= val && val <= this.max;
			},
			value: 80
		}
	};

	/**
  * Default slider elementClasses.
  * @default slider
  * @type {string}
  * @static
  */
	Slider.ELEMENT_CLASSES = 'slider';

	exports.default = Slider;
	_JQueryAdapter2.default.register('slider', Slider);
});
//# sourceMappingURL=Slider.js.map
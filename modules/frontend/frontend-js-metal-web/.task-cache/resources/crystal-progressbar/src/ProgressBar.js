'use strict';

function _typeof(obj) { return obj && typeof Symbol !== "undefined" && obj.constructor === Symbol ? "symbol" : typeof obj; }

define("frontend-js-metal-web@1.0.0/crystal-progressbar/src/ProgressBar", ['exports', 'metal/src/core', 'metal/src/dom/dom', 'crystal-progressbar/src/ProgressBar.soy', 'metal-jquery-adapter/src/JQueryAdapter'], function (exports, _core, _dom, _ProgressBar, _JQueryAdapter) {
	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _core2 = _interopRequireDefault(_core);

	var _dom2 = _interopRequireDefault(_dom);

	var _ProgressBar2 = _interopRequireDefault(_ProgressBar);

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

		return call && ((typeof call === 'undefined' ? 'undefined' : _typeof(call)) === "object" || typeof call === "function") ? call : self;
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

	var ProgressBar = (function (_ProgressBarBase) {
		_inherits(ProgressBar, _ProgressBarBase);

		function ProgressBar() {
			_classCallCheck(this, ProgressBar);

			return _possibleConstructorReturn(this, _ProgressBarBase.apply(this, arguments));
		}

		ProgressBar.prototype.getBarElement = function getBarElement() {
			if (!this.barElement_) {
				this.barElement_ = this.element.childNodes[0];
			}

			return this.barElement_;
		};

		ProgressBar.prototype.setterValueFn_ = function setterValueFn_(value) {
			if (value < this.min) {
				value = this.min;
			}

			if (value > this.max) {
				value = this.max;
			}

			return value;
		};

		ProgressBar.prototype.syncBarClass = function syncBarClass(barClass, prevBarClass) {
			var barElement = this.getBarElement();

			_dom2.default.removeClasses(barElement, prevBarClass);

			_dom2.default.addClasses(barElement, barClass);
		};

		ProgressBar.prototype.syncLabel = function syncLabel() {
			var barElement = this.getBarElement();

			_dom2.default.removeChildren(barElement);

			if (this.label) {
				_dom2.default.append(barElement, this.label);
			}
		};

		ProgressBar.prototype.syncMax = function syncMax(max) {
			if (max < this.value) {
				this.value = max;
			} else {
				this.updateBar_();
			}

			this.element.setAttribute('aria-valuemax', this.max);
		};

		ProgressBar.prototype.syncMin = function syncMin(min) {
			if (min > this.value) {
				this.value = min;
			} else {
				this.updateBar_();
			}

			this.element.setAttribute('aria-valuemin', this.min);
		};

		ProgressBar.prototype.syncValue = function syncValue() {
			this.updateBar_();
			this.element.setAttribute('aria-valuenow', this.value);
		};

		ProgressBar.prototype.updateBar_ = function updateBar_() {
			var barElement = this.getBarElement();
			var percentage = Math.floor((this.value - this.min) * 100 / (this.max - this.min));
			barElement.style.width = percentage + '%';
		};

		return ProgressBar;
	})(_ProgressBar2.default);

	ProgressBar.prototype.registerMetalComponent && ProgressBar.prototype.registerMetalComponent(ProgressBar, 'ProgressBar')
	ProgressBar.ATTRS = {
		barClass: {
			validator: _core2.default.isString
		},
		label: {
			validator: function validator(label) {
				return !_core2.default.isDefAndNotNull(label) || _core2.default.isString(label);
			}
		},
		max: {
			validator: _core2.default.isNumber,
			value: 100
		},
		min: {
			validator: _core2.default.isNumber,
			value: 0
		},
		value: {
			setter: 'setterValueFn_',
			validator: _core2.default.isNumber,
			value: 0
		}
	};
	ProgressBar.ELEMENT_CLASSES = 'progress';
	exports.default = ProgressBar;

	_JQueryAdapter2.default.register('progressBar', ProgressBar);
});
//# sourceMappingURL=ProgressBar.js.map
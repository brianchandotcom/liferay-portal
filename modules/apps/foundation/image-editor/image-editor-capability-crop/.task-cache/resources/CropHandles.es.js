define("liferay-image-editor-capability-crop@1.0.0/CropHandles.es", ['exports', 'metal-component/src/Component', 'metal-soy/src/Soy', 'metal/src/async/async', 'metal/src/core', 'metal-drag-drop/src/Drag', './CropHandles.soy'], function (exports, _Component2, _Soy, _async, _core, _Drag, _CropHandles) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _Component3 = _interopRequireDefault(_Component2);

	var _Soy2 = _interopRequireDefault(_Soy);

	var _async2 = _interopRequireDefault(_async);

	var _core2 = _interopRequireDefault(_core);

	var _Drag2 = _interopRequireDefault(_Drag);

	var _CropHandles2 = _interopRequireDefault(_CropHandles);

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

	var CropHandles = function (_Component) {
		_inherits(CropHandles, _Component);

		function CropHandles() {
			_classCallCheck(this, CropHandles);

			return _possibleConstructorReturn(this, _Component.apply(this, arguments));
		}

		CropHandles.prototype.attached = function attached() {
			var _this2 = this;

			this.parentNode_ = this.element.parentNode;

			_async2.default.nextTick(function () {
				var imageContainer = _this2.getEditorImageContainer();

				_this2.element.classList.remove('hide');
				imageContainer.appendChild(_this2.element);

				_this2.selectionDrag_ = new _Drag2.default({
					constrain: '#' + _this2.imageContainerId,
					handles: _this2.element,
					sources: _this2.element
				});

				var resizer = _this2.element.querySelector('.resize-handle');

				_this2.sizeDrag_ = new _Drag2.default({
					constrain: '#' + _this2.imageContainerId,
					handles: resizer,
					sources: resizer
				});

				resizer.addEventListener('mousedown', function (event) {
					return event.stopPropagation();
				});

				_this2.sizeDrag_.on(_Drag2.default.Events.DRAG, function (data, event) {
					_this2.element.style.width = data.relativeX + 'px';
					_this2.element.style.height = data.relativeY + 'px';
				});
			});
		};

		return CropHandles;
	}(_Component3.default);

	/**
  * State definition.
  * @type {!Object}
  * @static
  */
	CropHandles.STATE = {
		/**
   * Injected helper to get the editor image container
   * @type {Function}
   */
		getEditorImageContainer: {
			validator: _core2.default.isFunction
		}
	};

	// Register component
	_Soy2.default.register(CropHandles, _CropHandles2.default);

	exports.default = CropHandles;
});
//# sourceMappingURL=CropHandles.es.js.map
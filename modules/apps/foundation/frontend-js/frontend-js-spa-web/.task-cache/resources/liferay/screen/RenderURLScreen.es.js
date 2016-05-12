define("frontend-js-spa-web@1.0.6/liferay/screen/RenderURLScreen.es", ['exports', './EventScreen.es'], function (exports, _EventScreen2) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _EventScreen3 = _interopRequireDefault(_EventScreen2);

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

	var RenderURLScreen = function (_EventScreen) {
		_inherits(RenderURLScreen, _EventScreen);

		function RenderURLScreen() {
			_classCallCheck(this, RenderURLScreen);

			var _this = _possibleConstructorReturn(this, _EventScreen.call(this));

			_this.cacheable = true;
			return _this;
		}

		return RenderURLScreen;
	}(_EventScreen3.default);

	exports.default = RenderURLScreen;
});
//# sourceMappingURL=RenderURLScreen.es.js.map
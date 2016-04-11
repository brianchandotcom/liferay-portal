define("frontend-js-spa-web@1.0.6/liferay/screen/ActionURLScreen.es", ['exports', './EventScreen.es', 'metal-uri/src/Uri', 'senna/src/utils/utils'], function (exports, _EventScreen2, _Uri, _utils) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _EventScreen3 = _interopRequireDefault(_EventScreen2);

	var _Uri2 = _interopRequireDefault(_Uri);

	var _utils2 = _interopRequireDefault(_utils);

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

	var ActionURLScreen = function (_EventScreen) {
		_inherits(ActionURLScreen, _EventScreen);

		function ActionURLScreen() {
			_classCallCheck(this, ActionURLScreen);

			var _this = _possibleConstructorReturn(this, _EventScreen.call(this));

			_this.httpMethod = 'POST';
			return _this;
		}

		ActionURLScreen.prototype.getRequestPath = function getRequestPath() {
			var request = this.getRequest();

			if (request) {
				var uri = new _Uri2.default(_EventScreen.prototype.getRequestPath.call(this));

				if (uri.getParameterValue('p_p_lifecycle') === '1') {
					uri.setParameterValue('p_p_lifecycle', '0');
				}

				return _utils2.default.getUrlPath(uri.toString());
			}

			return null;
		};

		return ActionURLScreen;
	}(_EventScreen3.default);

	exports.default = ActionURLScreen;
});
//# sourceMappingURL=ActionURLScreen.es.js.map
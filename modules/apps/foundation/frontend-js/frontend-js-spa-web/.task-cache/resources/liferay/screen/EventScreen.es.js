define("frontend-js-spa-web@1.0.6/liferay/screen/EventScreen.es", ['exports', 'metal-dom/src/dom', 'senna/src/screen/HtmlScreen', 'metal-dom/src/globalEval', 'metal-promise/src/promise/Promise', '../util/Utils.es'], function (exports, _dom, _HtmlScreen2, _globalEval, _Promise, _Utils) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _dom2 = _interopRequireDefault(_dom);

	var _HtmlScreen3 = _interopRequireDefault(_HtmlScreen2);

	var _globalEval2 = _interopRequireDefault(_globalEval);

	var _Utils2 = _interopRequireDefault(_Utils);

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

	var EventScreen = function (_HtmlScreen) {
		_inherits(EventScreen, _HtmlScreen);

		function EventScreen() {
			_classCallCheck(this, EventScreen);

			var _this = _possibleConstructorReturn(this, _HtmlScreen.call(this));

			_this.cacheable = false;
			_this.timeout = Liferay.PropsValues.JAVASCRIPT_SINGLE_PAGE_APPLICATION_TIMEOUT;
			return _this;
		}

		EventScreen.prototype.dispose = function dispose() {
			_HtmlScreen.prototype.dispose.call(this);

			Liferay.fire('screenDispose', {
				app: Liferay.SPA.app,
				screen: this
			});
		};

		EventScreen.prototype.activate = function activate() {
			_HtmlScreen.prototype.activate.call(this);

			Liferay.fire('screenActivate', {
				app: Liferay.SPA.app,
				screen: this
			});
		};

		EventScreen.prototype.deactivate = function deactivate() {
			_HtmlScreen.prototype.deactivate.call(this);

			Liferay.fire('screenDeactivate', {
				app: Liferay.SPA.app,
				screen: this
			});
		};

		EventScreen.prototype.beforeScreenFlip = function beforeScreenFlip() {
			Liferay.fire('beforeScreenFlip', {
				app: Liferay.SPA.app,
				screen: this
			});
		};

		EventScreen.prototype.flip = function flip(surfaces) {
			var _this2 = this;

			document.body.className = this.virtualDocument.querySelector('body').className;

			return _Promise.CancellablePromise.resolve(_Utils2.default.resetAllPortlets()).then(_Promise.CancellablePromise.resolve(this.beforeScreenFlip())).then(_HtmlScreen.prototype.flip.call(this, surfaces)).then(function () {
				Liferay.fire('screenFlip', {
					app: Liferay.SPA.app,
					screen: _this2
				});
			});
		};

		EventScreen.prototype.isValidResponseStatusCode = function isValidResponseStatusCode(statusCode) {
			var validStatusCodes = Liferay.SPA.app.getValidStatusCodes();

			return _HtmlScreen.prototype.isValidResponseStatusCode.call(this, statusCode) || validStatusCodes.indexOf(statusCode) > -1;
		};

		EventScreen.prototype.load = function load(path) {
			var _this3 = this;

			return _HtmlScreen.prototype.load.call(this, path).then(function (content) {
				Liferay.fire('screenLoad', {
					app: Liferay.SPA.app,
					content: content,
					screen: _this3
				});

				return content;
			});
		};

		return EventScreen;
	}(_HtmlScreen3.default);

	exports.default = EventScreen;
});
//# sourceMappingURL=EventScreen.es.js.map
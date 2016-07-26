define("frontend-js-spa-web@1.0.9/liferay/screen/EventScreen.es", ['exports', 'senna/src/screen/HtmlScreen', 'senna/src/globals/globals', 'metal-promise/src/promise/Promise', '../util/Utils.es'], function (exports, _HtmlScreen2, _globals, _Promise, _Utils) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _HtmlScreen3 = _interopRequireDefault(_HtmlScreen2);

	var _globals2 = _interopRequireDefault(_globals);

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
			_this.timeout = Math.max(Liferay.SPA.requestTimeout, 0) || _Utils2.default.getMaxTimeout();
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

		EventScreen.prototype._clearRequestTimer = function _clearRequestTimer() {
			if (this.requestTimer) {
				clearTimeout(this.requestTimer);
			}

			if (this.timeoutAlert) {
				this.timeoutAlert.hide();
			}
		};

		EventScreen.prototype._createTimeoutNotification = function _createTimeoutNotification() {
			var instance = this;

			AUI().use('liferay-notification', function () {
				instance.timeoutAlert = new Liferay.Notification({
					closeable: true,
					delay: {
						hide: 0,
						show: 0
					},
					duration: 500,
					message: Liferay.SPA.userNotification.message,
					title: Liferay.SPA.userNotification.title,
					type: 'warning'
				}).render('body');
			});
		};

		EventScreen.prototype._startRequestTimer = function _startRequestTimer(path) {
			var _this2 = this;

			if (Liferay.SPA.userNotification.timeout > 0) {
				this._clearRequestTimer();

				this.requestTimer = setTimeout(function () {
					Liferay.fire('spaRequestTimeout', {
						path: path
					});

					if (!_this2.timeoutAlert) {
						_this2._createTimeoutNotification();
					} else {
						_this2.timeoutAlert.show();
					}
				}, Liferay.SPA.userNotification.timeout);
			}
		};

		EventScreen.prototype.addCache = function addCache(content) {
			_HtmlScreen.prototype.addCache.call(this, content);

			this.cacheLastModified = new Date().getTime();
		};

		EventScreen.prototype.checkRedirectPath = function checkRedirectPath(redirectPath) {
			var app = Liferay.SPA.app;

			if (!_globals2.default.capturedFormElement && !app.findRoute(redirectPath)) {
				window.location.href = redirectPath;
			}
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

		EventScreen.prototype.copyBodyAttributes = function copyBodyAttributes() {
			var virtualBody = this.virtualDocument.querySelector('body');

			document.body.className = virtualBody.className;
			document.body.onload = virtualBody.onload;
		};

		EventScreen.prototype.flip = function flip(surfaces) {
			var _this3 = this;

			this.copyBodyAttributes();

			return _Promise.CancellablePromise.resolve(_Utils2.default.resetAllPortlets()).then(_Promise.CancellablePromise.resolve(this.beforeScreenFlip())).then(_HtmlScreen.prototype.flip.call(this, surfaces)).then(function () {
				_this3.runBodyOnLoad();

				Liferay.fire('screenFlip', {
					app: Liferay.SPA.app,
					screen: _this3
				});
			});
		};

		EventScreen.prototype.getCache = function getCache() {
			var app = Liferay.SPA.app;

			if (app.isCacheEnabled() && !app.isScreenCacheExpired(this)) {
				return _HtmlScreen.prototype.getCache.call(this);
			}

			return null;
		};

		EventScreen.prototype.getCacheLastModified = function getCacheLastModified() {
			return this.cacheLastModified;
		};

		EventScreen.prototype.isValidResponseStatusCode = function isValidResponseStatusCode(statusCode) {
			var validStatusCodes = Liferay.SPA.app.getValidStatusCodes();

			return _HtmlScreen.prototype.isValidResponseStatusCode.call(this, statusCode) || validStatusCodes.indexOf(statusCode) > -1;
		};

		EventScreen.prototype.load = function load(path) {
			var _this4 = this;

			this._startRequestTimer(path);

			return _HtmlScreen.prototype.load.call(this, path).then(function (content) {
				_this4._clearRequestTimer();

				var redirectPath = _this4.beforeUpdateHistoryPath(path);

				_this4.checkRedirectPath(redirectPath);

				Liferay.fire('screenLoad', {
					app: Liferay.SPA.app,
					content: content,
					screen: _this4
				});

				return content;
			});
		};

		EventScreen.prototype.runBodyOnLoad = function runBodyOnLoad() {
			var onLoad = document.body.onload;

			if (onLoad) {
				onLoad();
			}
		};

		return EventScreen;
	}(_HtmlScreen3.default);

	exports.default = EventScreen;
});
//# sourceMappingURL=EventScreen.es.js.map
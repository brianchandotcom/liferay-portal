define("frontend-js-spa-web@1.0.6/liferay/init.es", ['exports', './screen/ActionURLScreen.es', './app/App.es', 'metal/src/async/async', 'senna/src/globals/globals', './screen/RenderURLScreen.es', 'metal-uri/src/Uri', 'senna/src/utils/utils'], function (exports, _ActionURLScreen, _App, _async, _globals, _RenderURLScreen, _Uri, _utils) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	var _ActionURLScreen2 = _interopRequireDefault(_ActionURLScreen);

	var _App2 = _interopRequireDefault(_App);

	var _async2 = _interopRequireDefault(_async);

	var _globals2 = _interopRequireDefault(_globals);

	var _RenderURLScreen2 = _interopRequireDefault(_RenderURLScreen);

	var _Uri2 = _interopRequireDefault(_Uri);

	var _utils2 = _interopRequireDefault(_utils);

	function _interopRequireDefault(obj) {
		return obj && obj.__esModule ? obj : {
			default: obj
		};
	}

	var initSPA = function initSPA(callback) {
		var app = new _App2.default();

		app.addRoutes([{
			handler: _ActionURLScreen2.default,
			path: function path(url) {
				var uri = new _Uri2.default(url);

				var loginRedirect = new _Uri2.default(Liferay.SPA.loginRedirect);

				var hostname = loginRedirect.getHostname() || window.location.hostname;

				if (!app.isLinkSameOrigin_(hostname)) {
					return false;
				}

				return uri.getParameterValue('p_p_lifecycle') === '1';
			}
		}, {
			handler: _RenderURLScreen2.default,
			path: function path(url) {
				if (url.indexOf(themeDisplay.getPathMain()) === 0) {
					return false;
				}

				var excluded = Liferay.SPA.excludedPaths.some(function (excludedPath) {
					return url.indexOf(excludedPath) === 0;
				});

				if (excluded) {
					return false;
				}

				var uri = new _Uri2.default(url);

				var lifecycle = uri.getParameterValue('p_p_lifecycle');

				return lifecycle === '0' || !lifecycle;
			}
		}]);

		Liferay.Util.submitForm = function (form) {
			_async2.default.nextTick(function () {
				var formElement = form.getDOM();
				var url = formElement.action;

				if (app.canNavigate(url) && formElement.method !== 'get' && !app.isInPortletBlacklist(formElement)) {

					Liferay.Util._submitLocked = false;

					_globals2.default.capturedFormElement = formElement;

					app.navigate(_utils2.default.getUrlPath(url));
				} else {
					formElement.submit();
				}
			});
		};

		Liferay.SPA.app = app;

		Liferay.fire('SPAReady');

		return app;
	};

	exports.default = {
		init: function init(callback) {
			var _this = this;

			if (_globals2.default.document.readyState == 'loading') {
				_globals2.default.document.addEventListener('DOMContentLoaded', function () {
					callback.call(_this, initSPA());
				});
			} else {
				callback.call(this, initSPA());
			}
		}
	};
});
//# sourceMappingURL=init.es.js.map
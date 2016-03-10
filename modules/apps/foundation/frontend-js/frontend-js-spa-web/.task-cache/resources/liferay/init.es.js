define("frontend-js-spa-web@1.0.0/liferay/init.es", ['exports', './screen/ActionURLScreen.es', './app/App.es', 'metal/src/async/async', 'senna/src/globals/globals', './screen/RenderURLScreen.es', 'metal-uri/src/Uri', 'senna/src/utils/utils'], function (exports, _ActionURLScreen, _App, _async, _globals, _RenderURLScreen, _Uri, _utils) {
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

	var app = new _App2.default();

	app.addRoutes([{
		handler: _ActionURLScreen2.default,
		path: function path(url) {
			var uri = new _Uri2.default(url);

			return uri.getParameterValue('p_p_lifecycle') === '1';
		}
	}, {
		handler: _RenderURLScreen2.default,
		path: function path(url) {
			if (url.indexOf(themeDisplay.getPathMain()) === 0 || url.indexOf('/documents') === 0 || url.indexOf('/image') === 0) {

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

			if (app.canNavigate(url) && formElement.method !== 'get') {
				Liferay.Util._submitLocked = false;

				_globals2.default.capturedFormElement = formElement;

				app.navigate(_utils2.default.getUrlPath(url));
			} else {
				formElement.submit();
			}
		});
	};

	Liferay.SPA = Liferay.SPA || {};

	Liferay.SPA.app = app;

	exports.default = Liferay.SPA;
});
//# sourceMappingURL=init.es.js.map
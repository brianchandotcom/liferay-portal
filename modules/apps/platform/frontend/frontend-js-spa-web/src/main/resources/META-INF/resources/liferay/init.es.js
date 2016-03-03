'use strict';

import ActionURLScreen from './screen/ActionURLScreen.es';
import App from './app/App.es';
import async from 'metal/src/async/async';
import globals from 'senna/src/globals/globals';
import RenderURLScreen from './screen/RenderURLScreen.es';
import Uri from 'metal-uri/src/Uri';
import utils from 'senna/src/utils/utils';

let app = new App();

app.addRoutes(
	[
		{
			handler: ActionURLScreen,
			path: function(url) {
				var uri = new Uri(url);

				return uri.getParameterValue('p_p_lifecycle') === '1';
			}
		},
		{
			handler: RenderURLScreen,
			path: function(url) {
				if (url.indexOf(themeDisplay.getPathMain()) === 0 ||
					url.indexOf('/documents') === 0 ||
					url.indexOf('/image') === 0) {

					return false;
				}

				var uri = new Uri(url);

				var lifecycle = uri.getParameterValue('p_p_lifecycle');

				return lifecycle === '0' || !lifecycle;
			}
		}
	]
);

Liferay.Util.submitForm = function(form) {
	async.nextTick(
		() => {
			let formElement = form.getDOM();
			let url = formElement.action;

			if (app.canNavigate(url) && formElement.method !== 'get') {
				Liferay.Util._submitLocked = false;

				globals.capturedFormElement = formElement;

				app.navigate(utils.getUrlPath(url));
			}
			else {
				formElement.submit();
			}
		}
	);
};

Liferay.SPA = Liferay.SPA || {};

Liferay.SPA.app = app;

export default Liferay.SPA;
'use strict';

import ActionURLScreen from './screen/ActionURLScreen.es';
import App from './app/App.es';
import async from 'metal/src/async/async'
import globals from 'senna/src/globals/globals'
import RenderURLScreen from './screen/RenderURLScreen.es';
import Uri from 'metal-uri/src/Uri'

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
				if (url.indexOf(themeDisplay.getPathMain()) === 0) {
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

			if (formElement === globals.capturedFormElement) {
				Liferay.Util._submitLocked = false;

				var uri = new Uri(formElement.action);
				app.navigate(uri.getPathname() + uri.getSearch() + uri.getHash());
			}
			else {
				formElement.submit();
			}
		}
	);
}

Liferay.SPA = {
	app: app
};

export default Liferay.SPA;
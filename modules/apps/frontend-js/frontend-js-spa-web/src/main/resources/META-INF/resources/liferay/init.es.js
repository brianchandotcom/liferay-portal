'use strict';

import Uri from 'metal-uri/lib/Uri';
import async from 'metal/lib/async/async';
import globals from 'senna/lib/globals/globals';
import utils from 'senna/lib/utils/utils';
import version from 'senna/lib/app/version';
import {match} from 'metal-dom';

import ActionURLScreen from './screen/ActionURLScreen.es';
import App from './app/App.es';
import RenderURLScreen from './screen/RenderURLScreen.es';

/**
 * Initializes a Senna App with routes that match both ActionURLs and RenderURLs.
 * It also overrides Liferay's default Liferay.Util.submitForm to makes sure
 * forms are properly submitted using SPA.
 * @return {!App} The Senna App initialized
 */

let initSPA = function() {
	let app = new App();

	app.addRoutes(
		[
			{
				handler: ActionURLScreen,
				path: function(url) {
					let match = false;

					const uri = new Uri(url);

					const loginRedirect = new Uri(Liferay.SPA.loginRedirect);

					const host = loginRedirect.getHost() || window.location.host;

					if (app.isLinkSameOrigin_(host)) {
						match = uri.getParameterValue('p_p_lifecycle') === '1';
					}

					return match;
				}
			},
			{
				handler: RenderURLScreen,
				path: function(url) {
					let match = false;

					if ((url + '/').indexOf(themeDisplay.getPathMain() + '/') !== 0) {
						const excluded = Liferay.SPA.excludedPaths.some(
							(excludedPath) => url.indexOf(excludedPath) === 0
						);

						if (!excluded) {
							const uri = new Uri(url);

							const lifecycle = uri.getParameterValue('p_p_lifecycle');

							match = lifecycle === '0' || !lifecycle;
						}
					}

					return match;
				}
			}
		]
	);

	Liferay.Util.submitForm = function(form) {
		async.nextTick(
			() => {
				let formElement = form.getDOM();
				let formSelector = 'form' + Liferay.SPA.navigationExceptionSelectors;
				let url = formElement.action;

				if (match(formElement, formSelector) && app.canNavigate(url) && (formElement.method !== 'get') && !app.isInPortletBlacklist(formElement)) {
					Liferay.Util._submitLocked = false;

					globals.capturedFormElement = formElement;

					const buttonSelector = 'button:not([type]),button[type=submit],input[type=submit]';

					if (match(globals.document.activeElement, buttonSelector)) {
						globals.capturedFormButtonElement = globals.document.activeElement;
					}
					else {
						globals.capturedFormButtonElement = form.one(buttonSelector);
					}

					app.navigate(utils.getUrlPath(url));
				}
				else {
					formElement.submit();
				}
			}
		);
	};

	Liferay.SPA.app = app;
	Liferay.SPA.version = version;

	Liferay.fire('SPAReady');

	return app;
};

export default {
	init: function(callback) {
		if (globals.document.readyState == 'loading') {
			globals.document.addEventListener(
				'DOMContentLoaded',
				() => {
					callback.call(this, initSPA());
				}
			);
		}
		else {
			callback.call(this, initSPA());
		}
	}
};
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import globals from './globals/globals';
import {version} from './app/version';
import {getUrlPath} from './util/utils';
import App from './app/App.es';
import ActionURLScreen from './screen/ActionURLScreen.es';
import RenderURLScreen from './screen/RenderURLScreen.es';

/**
 * Initializes a Senna App with routes that match both ActionURLs and RenderURLs.
 * It also overrides Liferay's default Liferay.Util.submitForm to makes sure
 * forms are properly submitted using SPA.
 * @return {!App} The Senna App initialized
 */

const initSPA = function () {
	const app = new App();

	app.addRoutes([
		{
			handler: ActionURLScreen,
			path(url) {
				let match = false;

				const uri = new URL(url, window.location.origin);

				const loginRedirect = new URL(
					Liferay.SPA.loginRedirect,
					window.location.origin
				);

				const host = loginRedirect.host || window.location.host;

				if (app.isLinkSameOrigin_(host)) {
					match = uri.searchParams.get('p_p_lifecycle') === '1';
				}

				return match;
			},
		},
		{
			handler: RenderURLScreen,
			path(url) {
				let match = false;

				if (
					(url + '/').indexOf(themeDisplay.getPathMain() + '/') !== 0
				) {
					const excluded = Liferay.SPA.excludedPaths.some(
						(excludedPath) => url.indexOf(excludedPath) === 0
					);

					if (!excluded) {
						const uri = new URL(url, window.location.origin);

						const lifecycle = uri.searchParams.get('p_p_lifecycle');

						match = lifecycle === '0' || !lifecycle;
					}
				}

				return match;
			},
		},
	]);

	Liferay.Util.submitForm = function (form) {
		setTimeout(() => {
			const formElement = Object.isPrototypeOf.call(
				HTMLFormElement.prototype,
				form
			)
				? form
				: form.getDOM();
			const formSelector =
				'form' + Liferay.SPA.navigationExceptionSelectors;
			const url = formElement.action;

			if (
				formElement.matches(formSelector) &&
				app.canNavigate(url) &&
				formElement.method !== 'get' &&
				!app.isInPortletBlacklist(formElement)
			) {
				Liferay.Util._submitLocked = false;

				globals.capturedFormElement = formElement;

				const buttonSelector =
					'button:not([type]),button[type=submit],input[type=submit]';

				if (globals.document.activeElement.matches(buttonSelector)) {
					globals.capturedFormButtonElement =
						globals.document.activeElement;
				}
				else {
					globals.capturedFormButtonElement = formElement.querySelector(
						buttonSelector
					);
				}

				app.navigate(getUrlPath(url));
			}
			else {
				formElement.submit();
			}
		});
	};

	Liferay.initComponentCache();

	Liferay.SPA.app = app;
	Liferay.SPA.version = version;

	Liferay.fire('SPAReady');

	return app;
};

export default {
	init(callback) {
		if (globals.document.readyState == 'loading') {
			globals.document.addEventListener('DOMContentLoaded', () => {
				callback.call(this, initSPA());
			});
		}
		else {
			callback.call(this, initSPA());
		}
	},
};

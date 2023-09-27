/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import App from './app/LiferayApp';
import ActionURLScreen from './screen/ActionURLScreen';
import RenderURLScreen from './screen/RenderURLScreen';
import {getUrlPath} from './util/utils';

/**
 * Initializes a Senna App with routes that match both ActionURLs and RenderURLs.
 * It also overrides Liferay's default Liferay.Util.submitForm to makes sure
 * forms are properly submitted using SPA.
 * @return {!App} The Senna App initialized
 */
const initSPA = function (config) {
	const app = new App(config);

	app.addRoutes([
		{
			handler: ActionURLScreen,
			path(url) {
				let match = false;

				const uri = new URL(url, window.location.origin);

				const loginRedirectURL = new URL(
					config.loginRedirect,
					window.location.origin
				);

				const host = loginRedirectURL.host || window.location.host;

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
					const excluded = config.excludedPaths.some(
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
			const formSelector = 'form' + config.navigationExceptionSelectors;
			const url = formElement.action;

			if (
				formElement.matches(formSelector) &&
				app.canNavigate(url) &&
				formElement.method !== 'get' &&
				!app.isInPortletBlacklist(formElement)
			) {
				Liferay.Util._submitLocked = false;

				Liferay.SPA.__capturedFormElement__ = formElement;

				const buttonSelector =
					'button:not([type]),button[type=submit],input[type=submit]';

				if (document.activeElement.matches(buttonSelector)) {
					Liferay.SPA.__capturedFormButtonElement__ =
						document.activeElement;
				}
				else {
					Liferay.SPA.__capturedFormButtonElement__ = formElement.querySelector(
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

	Liferay.SPA = {app};

	Liferay.fire('SPAReady');

	return app;
};

export default function init(config) {
	if (document.readyState === 'loading') {
		document.addEventListener('DOMContentLoaded', () => {
			initSPA(config);
		});
	}
	else {
		initSPA(config);
	}
}

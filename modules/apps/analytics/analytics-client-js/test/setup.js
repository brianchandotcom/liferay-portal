/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * The analytics-client-js implementation relies on the non-standard
 * `innerText` property, which jsdom does not implement, so we need this
 * special helper in tests that sets `innerText` whenever `innerHTML` is
 * set.
 *
 * @see https://github.com/jsdom/jsdom/issues/1245
 */
global.setInnerHTML = (element, html) => {
	element.innerHTML = html;
	element.innerText = element.textContent;
};

if (!global.performance.timing) {
	Object.defineProperty(global.performance, 'timing', {
		get() {
			return {
				loadEventStart: 1,
				navigationStart: 0,
			};
		},
	});
}

Liferay.Util.Cookie = {
	TYPES: {
		FUNCTIONAL: 'CONSENT_TYPE_FUNCTIONAL',
		NECESSARY: 'CONSENT_TYPE_NECESSARY',
		PERFORMANCE: 'CONSENT_TYPE_PERFORMANCE',
		PERSONALIZATION: 'CONSENT_TYPE_PERSONALIZATION',
	},
	set: jest.fn(),
};

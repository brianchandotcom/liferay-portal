/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export const Liferay = window.Liferay || {
	BREAKPOINTS: {
		PHONE: 0,
		TABLET: 0,
	},
	FeatureFlags: {},
	ThemeDisplay: {
		getBCP47LanguageId: () => 'en-US',
		getCanonicalURL: () => window.location.href,
		getCompanyGroupId: () => 0,
		getLanguageId: () => 'en_US',
		getLayoutRelativeURL: () => '',
		getPathThemeImages: () => null,
		getPortalURL: () => window.location.origin,
		getScopeGroupId: () => 0,
		getSiteGroupId: () => 0,
		getUserId: () => '0',
	},
	Util: {
		SessionStorage: Object.assign(sessionStorage, {
			Types: {},
		}),
		isTablet: () => false,
		navigate: (path) => window.location.assign(path),
		openToast: (options) => alert(options),
	},
	authToken: '',
	detach: (type, callback) => window.removeEventListener(type, callback),
	on: (type, callback) => window.addEventListener(type, callback),
	once: (type, callback) =>
		window.addEventListener(type, function handler() {
			this.removeEventListener(type, handler);

			callback();
		}),
	publish: (name, _options) => ({
		fire: (data) =>
			window.dispatchEvent(
				new CustomEvent(name, {
					bubbles: true,
					composed: true,
					...data,
				})
			),
	}),
};

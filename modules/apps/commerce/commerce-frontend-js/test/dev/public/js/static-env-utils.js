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

const Liferay = {
	Language: {
		get: (key) => {
			let counter = 0;

			return key.replace(new RegExp('(^x-)|(-x-)|(-x$)', 'gm'), (match) =>
				match.replace('x', `{${counter++}}`)
			);
		},
	},
	ThemeDisplay: {
		getBCP47LanguageId: () => 'en-US',
		getCanonicalURL: () => '/',
		getDefaultLanguageId: () => 'en_US',
		getLanguageId: () => 'it_IT',
		getPathThemeImages: () => '/assets',
		getPortalURL: () => window.location.origin,
		getScopeGroupId: () => '123',
	},
	Util: {
		sub: (key, ...values) => {
			return values.reduce(
				(acc, value, i) => acc.replace(new RegExp(`{[${i}]}`), value),
				key
			);
		},
	},
	component: () => {},
	detach: (name, fn) => {
		window.removeEventListener(name, fn);
	},
	fire: (name, payload) => {
		var event = document.createEvent('CustomEvent');
		event.initCustomEvent(name);
		if (payload) {
			Object.keys(payload).forEach((key) => {
				event[key] = payload[key];
			});
		}
		window.dispatchEvent(event);
	},
	on: (name, fn) => {
		window.addEventListener(name, fn);
	},
	staticEnvTestUtils: {
		print: (message, type) => ({message, type}),
	},
};

window.defaultFetch = fetch;

window.fetch = (resource, {headers, ...init}) => {
	headers = new Headers({
		Accept: 'application/json',
		Authorization: `Basic ${window.btoa('test@liferay.com:test')}`,
		'Content-Type': 'application/json',
	});

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	return window.defaultFetch(resource, {
		...init,
		headers,
	});
};

window.Liferay = Liferay;
window.themeDisplay = Liferay.ThemeDisplay;

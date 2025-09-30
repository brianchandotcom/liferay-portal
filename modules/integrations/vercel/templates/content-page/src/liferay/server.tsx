/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const LIFERAY_HOST = process.env.LIFERAY_HOST || '';
const LIFERAY_CONTENT_PATH = process.env.LIFERAY_CONTENT_PATH || '';
const LIFERAY_LANGUAGES = process.env.LIFERAY_LANGUAGES || '';

export const liferay = {
	fetch: async (
		resource: string | URL | globalThis.Request,
		{lang}: {lang: string},
		init?: RequestInit
	) => {
		const endpoint =
			typeof resource === 'string' && resource.startsWith('/')
				? `${LIFERAY_HOST}${resource}`
				: resource;

		const formattedLang = lang.replace('_', '-');

		const response = await fetch(endpoint, {
			method: init?.method || 'GET',
			headers: {
				'accept': '*/*',
				'accept-language': `${formattedLang};q=0.5`,
				'content-type': 'application/json',
				...init?.headers,
			},
			next: {
				revalidate: 3600,
			},
		});

		return response;
	},

	getSupportedLanguages: () => {
		return LIFERAY_LANGUAGES.split(',');
	},

	getDocument: (documentPath: string) => {
		if (documentPath.startsWith('/')) {
			return `${LIFERAY_HOST}${documentPath}`;
		}

		return documentPath;
	},

	contentEndpoints: {
		getContentURL: () => {
			return `${LIFERAY_HOST}${LIFERAY_CONTENT_PATH}`;
		},
	},
};

export type Liferay = typeof liferay;

export type WithLiferay<TParams = unknown> = TParams & {liferay: Liferay};

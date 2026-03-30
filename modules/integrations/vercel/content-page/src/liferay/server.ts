/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const LIFERAY_CONTENT_PATH = process.env.LIFERAY_CONTENT_PATH || '';
const LIFERAY_HOST = process.env.LIFERAY_HOST || '';
const LIFERAY_LANGUAGES = process.env.LIFERAY_LANGUAGES || 'en-US';
const LIFERAY_SPACE_ID = process.env.LIFERAY_SPACE_ID || '';

export const liferay = {
	contentEndpoints: {
		getEventURL: (id: number) => {
			return `${LIFERAY_HOST}${LIFERAY_CONTENT_PATH}/${id}`;
		},

		getEventsURL: () => {
			return `${LIFERAY_HOST}${LIFERAY_CONTENT_PATH}/scopes/${LIFERAY_SPACE_ID}`;
		},
	},

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
			headers: {
				'accept': '*/*',
				'accept-language': `${formattedLang};q=0.5`,
				'content-type': 'application/json',
				...init?.headers,
			},
			method: init?.method || 'GET',
			next: {
				revalidate: 3600,
			},
		});

		return response;
	},

	getDocument: (documentPath: string) => {
		if (documentPath.startsWith('/')) {
			return `${LIFERAY_HOST}${documentPath}`;
		}

		return documentPath;
	},

	getMissingEnvVars: () => {
		const required = [
			'LIFERAY_CONTENT_PATH',
			'LIFERAY_HOST',
			'LIFERAY_SPACE_ID',
		];

		return required.filter((key) => !process.env[key]);
	},

	getSupportedLanguages: () => {
		return LIFERAY_LANGUAGES.split(',');
	},
};

export type Liferay = typeof liferay;

export type WithLiferay<TParams = unknown> = TParams & {liferay: Liferay};

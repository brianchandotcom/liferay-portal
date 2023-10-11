/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNullOrUndefined} from '@liferay/layout-js-components-web';

let cache: Map<String, any> | null = null;

export const CACHE_KEYS = {
	actionError: 'actionError',
	allowedInputTypes: 'allowedInputTypes',
	collectionConfigurationUrl: 'collectionConfigurationUrl',
	collectionVariations: 'collectionVariations',
	collectionWarningMessage: 'collectionWarningMessage',
	formFields: 'formFields',
	users: 'users',
};

export const CACHE_STATUS = {
	loading: 'loading',
	saved: 'saved',
} as const;

export function initializeCache() {
	cache = new Map();
}

export function disposeCache() {
	cache = null;
}

export function getCacheKey(key: string | string[]) {
	if (Array.isArray(key)) {
		return key.every((subkey) => subkey) ? key.join('-') : null;
	}

	return key;
}

export function getCacheItem(key: string | null) {
	if (!cache) {
		throw new Error('cache is not initialized');
	}

	if (!key) {
		return {};
	}

	return cache.get(key) || {};
}

export function deleteCacheItem(key: string) {
	if (!cache) {
		throw new Error('cache is not initialized');
	}

	cache.delete(key);
}

export function setCacheItem({
	data,
	key,
	loadPromise,
	status,
}: {
	data?: Record<string, any>;
	key: string;
	loadPromise?: Promise<Response & {error: string}>;
	status: typeof CACHE_STATUS[keyof typeof CACHE_STATUS];
}) {
	if (!cache) {
		throw new Error('cache is not initialized');
	}

	cache.set(key, {
		...(isNullOrUndefined(data) ? {} : {data}),
		...(loadPromise && {loadPromise}),
		status,
	});
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNullOrUndefined} from '@liferay/layout-js-components-web';

let cache: Map<String, any> | null = null;

export type FetcherReturn<T> = Promise<T & {error?: string}>;

export type CacheData<T> = {
	data?: T;
	key: string;
	loadPromise?: FetcherReturn<T>;
	status: (typeof CACHE_STATUS)[keyof typeof CACHE_STATUS];
};

export const CACHE_KEYS = {
	actionError: 'actionError',
	allowedInputTypes: 'allowedInputTypes',
	collectionConfigurationUrl: 'collectionConfigurationUrl',
	collectionVariations: 'collectionVariations',
	collectionWarningMessage: 'collectionWarningMessage',
	formFields: 'formFields',
	relationships: 'relationships',
	roles: 'roles',
	users: 'users',
} as const;

export type CacheKey = (typeof CACHE_KEYS)[keyof typeof CACHE_KEYS];

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

export function getCacheKey(
	key: CacheKey | [CacheKey, ...string[]]
): string | null {
	if (Array.isArray(key)) {
		return key.every((subkey) => subkey) ? key.join('-') : null;
	}

	return key;
}

export function getCacheItem<T>(
	key: string | null
): CacheData<T> | Record<string, never> {
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

export function setCacheItem<T>({
	data,
	key,
	loadPromise,
	status,
}: CacheData<T>) {
	if (!cache) {
		throw new Error('cache is not initialized');
	}

	cache.set(key, {
		...(isNullOrUndefined(data) ? {} : {data}),
		...(loadPromise && {loadPromise}),
		status,
	});
}

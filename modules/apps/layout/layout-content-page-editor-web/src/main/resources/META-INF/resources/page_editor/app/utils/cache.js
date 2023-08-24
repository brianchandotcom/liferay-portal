/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNullOrUndefined} from '@liferay/layout-js-components-web';

let cache = null;

export const CACHE_KEYS = {
	actionError: 'actionError',
	allowedInputTypes: 'allowedInputTypes',
	collectionConfigurationUrl: 'collectionConfigurationUrl',
	collectionVariations: 'collectionVariations',
	collectionWarningMessage: 'collectionWarningMessage',
	formFields: 'formFields',
};

export const CACHE_STATUS = {
	loading: 'loading',
	saved: 'saved',
};

export function initializeCache() {
	cache = new Map();
}

export function disposeCache() {
	cache = null;
}

export function getCacheKey(key) {
	if (Array.isArray(key)) {
		return key.every((subkey) => subkey) ? key.join('-') : null;
	}

	return key;
}

export function getCacheItem(key) {
	if (!cache) {
		throw new Error('cache is not initialized');
	}

	return cache.get(key) || {};
}

export function deleteCacheItem(key) {
	if (!cache) {
		throw new Error('cache is not initialized');
	}

	cache.delete(key);
}

export function setCacheItem({data, key, loadPromise, status}) {
	cache.set(key, {
		...(isNullOrUndefined(data) ? {} : {data}),
		...(loadPromise && {loadPromise}),
		status,
	});
}

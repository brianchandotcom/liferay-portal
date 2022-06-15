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

let cache = null;

export const CACHE_KEYS = {
	allowedInputTypes: 'allowedInputTypes',
	formFields: 'formFields',
	itemTypes: 'itemTypes',
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
		...(data && {data}),
		...(loadPromise && {loadPromise}),
		status,
	});
}

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

const cache = {};

export const CACHE_KEYS = {
	allowedInputTypes: 'allowedInputTypes',
	formFields: 'formFields',
	itemTypes: 'itemTypes',
};

export const CACHE_STATUS = {
	loading: 'loading',
	saved: 'saved',
};

export function getCacheKey(key) {
	if (Array.isArray(key)) {
		return key.every((subkey) => subkey) ? key.join('-') : null;
	}

	return key;
}

export function getCacheItem(key) {
	return cache[key] || {};
}

export function setCacheItem({data, key, loadPromise, status}) {
	cache[key] = {
		data,
		loadPromise,
		status,
	};
}

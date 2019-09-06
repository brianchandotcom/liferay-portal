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

const DEFAULT_INIT = {
	credentials: 'include'
};

/**
 * Fetches a resource. A thin wrapper around ES6 Fetch API, with standardized
 * default configuration.
 * @param {!string|!Request} resource The URL to the resource, or a Resource
 * object.
 * @param {Object=} init An optional object containing custom configuration.
 * @return {Promise} A Promise that resolves to a Response object.
 */

export default function defaultFetch(resource, init = {}) {
	const headers = new Headers({'x-csrf-token': Liferay.authToken});

	new Headers(init.headers || {}).forEach((value, key) => {
		headers.set(key, value);
	});

	const mergedInit = {
		...DEFAULT_INIT,
		...init
	};

	mergedInit.headers = headers;

	// eslint-disable-next-line liferay-portal/no-global-fetch
	return fetch(resource, mergedInit);
}

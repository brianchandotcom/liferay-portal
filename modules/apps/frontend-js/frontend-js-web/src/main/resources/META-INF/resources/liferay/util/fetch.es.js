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

/**
 * @param {!string|!Request|!URL} resource
 * @param {!string} newLocation
 * @return {!string|!Request|!URL}
 */
function setNewLocation(resource, newLocation) {
	if (typeof resource === 'string') {
		resource = newLocation;
	}
	else if (resource instanceof URL) {
		resource = new URL(newLocation);
	}
	else if (resource instanceof Request) {
		resource = new Request(newLocation, resource);
	}
	else {
		console.warn(
			'Resource passed to `fetch()` must either be a string, Request, or URL.'
		);
	}

	return resource;
}

/**
 * Fetches a resource. A thin wrapper around ES6 Fetch API, with standardized
 * default configuration.
 * @param {!string|!Request|!URL} resource The URL to the resource, or a Resource
 * object.
 * @param {Object=} init An optional object containing custom configuration.
 * @return {Promise} A Promise that resolves to a Response object.
 */

export default function defaultFetch(resource, init = {}) {
	if (!resource) {
		resource = '/o/';
	}

	let resourceLocation = resource.url ? resource.url : resource.toString();

	if (resourceLocation.startsWith('/')) {
		const pathContext = Liferay.ThemeDisplay.getPathContext();

		if (pathContext && !resourceLocation.startsWith(pathContext)) {
			resourceLocation = pathContext + resourceLocation;

			resource = setNewLocation(resource, resourceLocation);
		}

		resourceLocation = window.location.origin + resourceLocation;
	}

	const resourceURL = new URL(resourceLocation);

	const headers = new Headers({});
	const config = {};

	if (resourceURL.origin === window.location.origin) {
		headers.set('x-csrf-token', Liferay.authToken);
		config.credentials = 'include';

		const doAsUserIdEncoded = Liferay.ThemeDisplay.getDoAsUserIdEncoded();

		if (doAsUserIdEncoded) {
			resourceURL.searchParams.set('doAsUserId', doAsUserIdEncoded);

			resourceLocation = resourceURL.toString();

			resource = setNewLocation(resource, resourceLocation);
		}
	}

	new Headers(init.headers || {}).forEach((value, key) => {
		headers.set(key, value);
	});

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	return fetch(resource, {...config, ...init, headers});
}

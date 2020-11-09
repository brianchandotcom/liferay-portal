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

import {fetch} from 'frontend-js-web';

import {fetchParams} from '../index';

const BASE_OPTIONS = {
	...fetchParams,
};

function _fetch(url, options = {}, params = {}) {
	const formattedUrl = new URL(url, Liferay.ThemeDisplay.getPortalURL());

	Object.entries(params).map(([key, value]) => {
		formattedUrl.searchParams.append(key, value);
	});

	return fetch(formattedUrl, {...BASE_OPTIONS, ...options})
		.then((response) => {
			if (!response.ok) {
				return response
					.json()
					.catch((parseError) =>
						Promise.reject(new Error(parseError))
					)
					.then((reason) => Promise.reject(reason));
			}

			if (response.status === 204) {
				return Promise.resolve();
			}

			return response.json();
		})
		.catch((error) => Promise.reject(error));
}

const AJAX = {
	DELETE(apiUrl, customOptions = {}, params = {}) {
		const options = {
			method: 'DELETE',
			...customOptions,
		};

		return _fetch(apiUrl, options, params);
	},

	GET(apiUrl, customOptions = {}, params = {}) {
		return _fetch(apiUrl, customOptions, params);
	},

	PATCH(apiUrl, jsonProps = {}, customOptions = {}, params = {}) {
		const options = {
			body: JSON.stringify(jsonProps),
			method: 'PATCH',
			...customOptions,
		};

		return _fetch(apiUrl, options, params);
	},

	POST(apiUrl, json = {}, customOptions = {}, params = {}) {
		const options = {
			body: JSON.stringify(json),
			method: 'POST',
			...customOptions,
		};

		return _fetch(apiUrl, options, params);
	},

	PUT(apiUrl, json = {}, customOptions = {}, params = {}) {
		const options = {
			body: JSON.stringify(json),
			method: 'PUT',
			...customOptions,
		};

		return _fetch(apiUrl, options, params);
	},
};

export default AJAX;

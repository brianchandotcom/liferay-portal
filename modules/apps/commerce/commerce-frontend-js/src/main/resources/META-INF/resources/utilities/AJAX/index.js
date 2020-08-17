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

function _fetch(url, options = {}) {
	return fetch(url, {...BASE_OPTIONS, ...options})
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
	DELETE(apiUrl, customOptions = {}) {
		const options = {
			method: 'DELETE',
			...customOptions,
		};

		return _fetch(apiUrl, options);
	},

	GET(apiUrl, customOptions = {}) {
		return _fetch(apiUrl, customOptions);
	},

	PATCH(apiUrl, jsonProps = {}, customOptions = {}) {
		const options = {
			body: JSON.stringify(jsonProps),
			method: 'PATCH',
			...customOptions,
		};

		return _fetch(apiUrl, options);
	},

	POST(apiUrl, json = {}, customOptions = {}) {
		const options = {
			body: JSON.stringify(json),
			method: 'POST',
			...customOptions,
		};

		return _fetch(apiUrl, options);
	},

	PUT(apiUrl, json = {}, customOptions = {}) {
		const options = {
			body: JSON.stringify(json),
			method: 'PUT',
			...customOptions,
		};

		return _fetch(apiUrl, options);
	},
};

export default AJAX;

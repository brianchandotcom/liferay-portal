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

const apiFetch = (url, method = 'get', data, contentType, headers) => {
	const request = {
		headers: getHeaders(headers),
		method: method.toUpperCase(),
	};

	if (method === 'post' || method === 'put') {
		if (contentType === 'application/json') {
			request.body = JSON.stringify(data);
			request.headers['Content-Type'] = 'application/json';
		}
		else if (contentType === 'multipart/form-data') {
			const formData = new FormData();

			for (let i = 0; i < data.length; i++) {
				const name = data[i];
				formData.append(name, data[name]);
			}

			request.body = formData;
		}
	}

	return fetch(url, request).then((res) => {
		let retVal;

		if (method === 'delete' && res.status === 204) {
			retVal = 'Deleted Successfully';
		}
		else {
			retVal = res.json();
		}

		return retVal;
	});

	function getHeaders(headers) {
		if (headers && headers.filter((obj) => obj.key).length) {
			return Object.assign(
				...headers.map((obj) => ({[obj.key]: obj.value}))
			);
		}

		return {};
	}
};

export default apiFetch;

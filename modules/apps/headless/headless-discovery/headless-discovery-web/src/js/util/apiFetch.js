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

const apiFetch = (url, method = 'get', data, contentType, headers) => {
	const request = {
		headers: {
			...getHeaders(headers),
			'x-csrf-token': document.querySelector('meta[name="csrf-token"]')
				.content,
		},
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

	return window.fetch(url, request).then((res) => {
		if (method === 'delete' && res.status === 204) {
			return 'Deleted Successfully';
		}

		return res.json();
	});

	function getHeaders(headers) {
		if (headers && headers.filter((object) => object.key).length) {
			return Object.assign(
				...headers.map((object) => ({[object.key]: object.value}))
			);
		}

		return {};
	}
};

export default apiFetch;

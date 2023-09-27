/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

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

const defaultHeaders = {
	Accept: 'application/json'
};

export const makeFetch = ({
	url,
	body,
	headers = defaultHeaders,
	method = 'POST'
}) => {
	const fetchData = {
		credentials: 'include',
		headers,
		method
	};

	if (method === 'POST') {
		fetchData.body = body;
	}

	return fetch(url, fetchData)
		.then(response => response.json())
		.catch(error => {
			const sessionStatus = Liferay.Session.get('sessionState');

			if (sessionStatus === 'expired' || error.status === 401) {
				window.location.reload();
			}
		});
};

export const convertToFormData = body => {
	if (body instanceof HTMLFormElement) {
		return new FormData(body);
	} else if (body instanceof FormData) {
		return body;
	} else if (typeof body === 'object') {
		const formData = new FormData();

		Object.entries(body).forEach(([key, value]) =>
			formData.append(key, value)
		);

		return formData;
	}

	throw new Error('Unsupported body type.');
};

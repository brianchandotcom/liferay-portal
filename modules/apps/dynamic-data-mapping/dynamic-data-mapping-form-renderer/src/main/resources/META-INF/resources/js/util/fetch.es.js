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
	Accept: 'application/json',
	'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
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

export const convertToSearchParams = body => {
	let searchParams = new URLSearchParams();

	if (body instanceof HTMLFormElement) {
		const formData = new FormData(body);

		formData.forEach((value, key) => searchParams.set(key, value));
	} else if (body instanceof FormData) {
		body.forEach((value, key) => searchParams.set(key, value));
	} else if (typeof body === 'object') {
		Object.entries(body).forEach(([key, value]) =>
			searchParams.append(key, value)
		);
	} else {
		searchParams = body;
	}

	return searchParams;
};

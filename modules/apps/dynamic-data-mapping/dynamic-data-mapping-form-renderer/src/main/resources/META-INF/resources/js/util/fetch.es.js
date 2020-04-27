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

import {fetch, objectToFormData} from 'frontend-js-web';

const defaultHeaders = {
	Accept: 'application/json',
};

export const makeFetch = ({
	body,
	headers = defaultHeaders,
	method = 'POST',
	url,
	...otherProps
}) => {
	const fetchData = {
		headers,
		method,
		...otherProps,
	};

	if (method === 'POST') {
		fetchData.body = body;
	}

	return fetch(url, fetchData)
		.then((response) => response.json())
		.catch((error) => {
			const sessionStatus = Liferay.Session.get('sessionState');

			if (sessionStatus === 'expired' || error.status === 401) {
				window.location.reload();
			}
			else {
				throw error;
			}
		});
};

export const convertToFormData = (body) => {
	let requestBody = body;

	if (body instanceof FormData) {
		requestBody = body;
	}
	else if (body instanceof HTMLFormElement) {
		requestBody = new FormData(body);
	}
	else if (typeof body === 'object') {
		requestBody = objectToFormData(body);
	}
	else {
		requestBody = body;
	}

	return requestBody;
};

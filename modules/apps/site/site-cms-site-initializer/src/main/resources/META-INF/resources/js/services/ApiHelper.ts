/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

const HEADERS = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
});

export const HEADERS_ALL_LANGUAGES = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
	'X-Accept-All-Languages': 'true',
});

const UNEXPECTED_ERROR_MESSAGE = Liferay.Language.get(
	'an-unexpected-error-occurred'
);

type RequestResult<T> =
	| {
			data: null;
			error: string;
	  }
	| {
			data: T;
			error: null;
	  };

async function handleRequest<T>(
	fetcher: () => Promise<Response>
): Promise<RequestResult<T>> {
	try {
		const response = await fetcher();

		if (response.status === 401) {
			window.location.reload();
		}

		if (!response.ok) {
			let errorMessage = UNEXPECTED_ERROR_MESSAGE;

			const {message, title} = await response.json();

			errorMessage = title ?? message ?? UNEXPECTED_ERROR_MESSAGE;

			if (Array.isArray(errorMessage)) {
				errorMessage = JSON.stringify(errorMessage);
			}

			return {
				data: null,
				error: errorMessage,
			};
		}

		const data: T = await response.json();

		return {
			data,
			error: null,
		};
	}
	catch (error) {
		return {
			data: null,
			error: (error as Error).message || UNEXPECTED_ERROR_MESSAGE,
		};
	}
}

async function get(url: string) {
	const response = await fetch(url, {
		headers: HEADERS,
		method: 'GET',
	});

	if (response.ok) {
		return await response.json();
	}

	const {title} = await response.json();

	throw new Error(title);
}

async function post<T>(url: string, data?: Record<string, any>) {
	return handleRequest<T>(() =>
		fetch(url, {
			body: JSON.stringify(data),
			headers: HEADERS,
			method: 'POST',
		})
	);
}

async function put<T>(url: string, data?: Record<string, any>) {
	return handleRequest<T>(() =>
		fetch(url, {
			body: JSON.stringify(data),
			headers: HEADERS,
			method: 'PUT',
		})
	);
}

async function postFormData(formData: FormData, url: string) {
	return handleRequest(() =>
		fetch(url, {
			body: formData,
			method: 'POST',
		})
	);
}

async function patch<T>(data: any, url: string) {
	return handleRequest<T>(() =>
		fetch(url, {
			body: JSON.stringify(data),
			headers: HEADERS,
			method: 'PATCH',
		})
	);
}

export default {get, patch, post, postFormData, put};

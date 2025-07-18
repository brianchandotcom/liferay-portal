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

const HEADERS_ALL_LANGUAGES = new Headers({
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
			status?: string | null;
	  }
	| {
			data: T;
			error: null;
			status?: string | null;
	  };

async function deleteRequest(url: string) {
	return handleRequest<null>(() =>
		fetch(url, {
			headers: HEADERS,
			method: 'DELETE',
		})
	);
}

async function handleRequest<T>(
	fetcher: () => Promise<Response>
): Promise<RequestResult<T>> {
	try {
		const response = await fetcher();

		if (response.status === 401) {
			window.location.reload();
		}

		if (!response.ok) {
			const {message, status, title} = await response.json();

			let error = title ?? message ?? UNEXPECTED_ERROR_MESSAGE;

			if (Array.isArray(error)) {
				error = JSON.stringify(error);
			}

			return {
				data: null,
				error,
				status,
			};
		}

		if (response.status === 204) {
			return {
				data: {} as T,
				error: null,
				status: null,
			};
		}

		const data: T | {error: string} = await response.json();

		if (data && typeof data === 'object' && 'error' in data) {
			return {
				data: null,
				error: data.error || UNEXPECTED_ERROR_MESSAGE,
				status: null,
			};
		}

		return {
			data,
			error: null,
			status: null,
		};
	}
	catch (error) {
		return {
			data: null,
			error: (error as Error).message || UNEXPECTED_ERROR_MESSAGE,
			status: null,
		};
	}
}

async function get<T>(url: string) {
	return handleRequest<T>(() =>
		fetch(url, {
			headers: HEADERS_ALL_LANGUAGES,
			method: 'GET',
		})
	);
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

export default {delete: deleteRequest, get, patch, post, postFormData, put};

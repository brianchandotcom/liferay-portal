/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

export type RequestResult<T> =
	| {data: null; error: string; status?: string | null}
	| {data: T; error: null; status?: string | null};

interface ApiErrorResponse {
	message?: string;
	title?: string;
}

const UNEXPECTED_ERROR_MESSAGE = Liferay.Language.get(
	'an-unexpected-error-occurred'
);

const HEADERS = {
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
};

const XHR_HEADERS = {
	...HEADERS,
	'X-CSRF-Token': Liferay.authToken,
};

async function get<T>(url: string): Promise<RequestResult<T>> {
	try {
		const response = await fetch(url, {
			headers: HEADERS,
		});

		if (response.status === 401) {
			window.location.reload();
		}

		if (response.status === 204) {
			return {data: {} as T, error: null};
		}

		let responseData: T | ApiErrorResponse;

		try {
			responseData = await response.json();
		}
		catch (error) {
			return {data: null, error: UNEXPECTED_ERROR_MESSAGE};
		}

		if (response.ok) {
			return {data: responseData as T, error: null};
		}

		const errorResponse = responseData as ApiErrorResponse;

		return {
			data: null,
			error:
				errorResponse?.title ||
				errorResponse?.message ||
				errorResponse?.error ||
				UNEXPECTED_ERROR_MESSAGE,
			status: response.status.toString(),
		};
	}
	catch (error) {
		return {data: null, error: UNEXPECTED_ERROR_MESSAGE};
	}
}

async function postFormDataWithProgress<T>(
	url: string,
	formData: FormData,
	onProgress?: (percent: number) => void,
	signal?: AbortSignal
): Promise<RequestResult<T>> {
	return new Promise((resolve) => {
		const xhr = new XMLHttpRequest();

		const handleAbort = () => {
			xhr.abort();
			resolve({data: null, error: 'Aborted'});
		};

		xhr.open('POST', url);

		Object.entries(XHR_HEADERS).forEach(([key, value]) => {
			xhr.setRequestHeader(key, value);
		});

		if (signal) {
			signal.addEventListener('abort', handleAbort);
		}

		if (onProgress && xhr.upload) {
			xhr.upload.onprogress = (event) => {
				if (event.lengthComputable) {
					onProgress(Math.round((event.loaded / event.total) * 100));
				}
			};
		}

		xhr.onload = () => {
			let responseData: T | ApiErrorResponse;

			try {
				responseData = JSON.parse(xhr.responseText);
			}
			catch (error) {
				return resolve({
					data: null,
					error: UNEXPECTED_ERROR_MESSAGE,
				});
			}

			if (xhr.status >= 200 && xhr.status < 300) {
				resolve({data: responseData as T, error: null});
			}
			else {
				const errorResponse = responseData as ApiErrorResponse;

				const errorMessage =
					errorResponse?.title ||
					errorResponse?.message ||
					UNEXPECTED_ERROR_MESSAGE;

				resolve({
					data: null,
					error: errorMessage,
					status: xhr.status.toString(),
				});
			}
		};

		xhr.onerror = () =>
			resolve({
				data: null,
				error: UNEXPECTED_ERROR_MESSAGE,
			});

		xhr.onloadend = () => {
			if (signal) {
				signal.removeEventListener('abort', handleAbort);
			}
		};

		xhr.send(formData);
	});
}

export default {
	get,
	postFormDataWithProgress,
};

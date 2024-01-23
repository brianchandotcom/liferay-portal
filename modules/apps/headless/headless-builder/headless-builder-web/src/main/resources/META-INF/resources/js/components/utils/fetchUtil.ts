/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

export const headers = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
});

export async function deleteData({
	onError,
	onSuccess,
	url,
}: {
	onError: (error: string) => void;
	onSuccess: voidReturn;
	url: string;
}) {
	fetch(url, {
		headers,
		method: 'DELETE',
	})
		.then((response) => {
			if (response.ok) {
				onSuccess();
			}
			else {
				throw response.json();
			}
		})
		.catch((error) => {
			error.then((response: {message: string; title: string}) => {
				onError(response.title ?? response.message);
			});
		});
}

export async function fetchJSON<T>({
	init,
	input,
}: {
	init?: RequestInit;
	input: RequestInfo;
}) {
	const result = await fetch(input, {headers, method: 'GET', ...init});

	return (await result.json()) as T;
}

export async function getAllItems<T>({
	filter,
	url,
}: {
	filter?: string;
	url: string;
}) {
	const {items} = await fetchJSON<{items: T[]}>({
		input: filter ? `${url}?filter=${filter}&page=-1` : `${url}?page=-1`,
	});

	return items;
}

export async function getItems<T>({url}: {url: string}) {
	const {items} = await fetchJSON<{items: T[]}>({input: url});

	return items;
}

export async function postData<T>({
	data,
	onError,
	onSuccess,
	url,
}: {
	data: Partial<T>;
	onError: (error: string) => void;
	onSuccess: (responseJSON: T) => void;
	url: string;
}) {
	fetch(url, {
		body: JSON.stringify(data),
		headers,
		method: 'POST',
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
			else {
				throw response.json();
			}
		})
		.then((responseJSON) => {
			onSuccess(responseJSON);
		})
		.catch((error) => {
			error.then((response: {message: string; title: string}) => {
				onError(response.title ?? response.message);
			});
		});
}

export async function updateData<T>({
	dataToUpdate,
	method,
	onError,
	onSuccess,
	url,
}: {
	dataToUpdate: Partial<T>;
	method: 'PATCH' | 'PUT';
	onError: (error: string) => void;
	onSuccess: (responseJSON: T) => void;
	url: string;
}) {
	fetch(url, {
		body: JSON.stringify(dataToUpdate),
		headers,
		method,
	})
		.then((response) => {
			if (response.ok) {
				return response.json();
			}
			else {
				throw response.json();
			}
		})
		.then((responseJSON) => {
			onSuccess(responseJSON);
		})
		.catch((error) => {
			error.then((response: {message: string; title: string}) => {
				onError(response.title ?? response.message);
			});
		});
}

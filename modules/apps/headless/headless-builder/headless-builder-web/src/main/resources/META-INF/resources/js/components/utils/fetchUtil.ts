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

export async function getAllItems<T>({url}: {url: string}) {
	let allItems: T[] = [];
	let currentPage = 1;
	let lastPage;

	do {
		const {items, lastPage: lastPageFromAPI, page} = await fetchJSON<{
			items: T[];
			lastPage: number;
			page: number;
		}>({input: url + `?page=${currentPage}`});

		allItems = [...allItems, ...items];
		currentPage = page + 1;
		lastPage = lastPageFromAPI;
	} while (currentPage <= lastPage);

	return allItems;
}

export async function getItems<T>({url}: {url: string}) {
	const {items} = await fetchJSON<{items: T[]}>({input: url});

	return items;
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

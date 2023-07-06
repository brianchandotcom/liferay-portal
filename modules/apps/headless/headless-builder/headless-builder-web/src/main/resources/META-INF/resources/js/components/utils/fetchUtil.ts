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

export async function getItems<T>({url}: {url: string}) {
	const {items} = await fetchJSON<{items: T[]}>({input: url});

	return items;
}

export async function updateData({
	dataToUpdate,
	onError,
	onSuccess,
	url,
}: {
	dataToUpdate: Partial<APIApplicationItem>;
	onError: (error: string) => void;
	onSuccess: voidReturn;
	url: string;
}) {
	fetch(url, {
		body: JSON.stringify(dataToUpdate),
		headers,
		method: 'PATCH',
	})
		.then((response) => {
			if (response.ok) {
				onSuccess();
			}
			else {
				return response.json();
			}
		})
		.then((errorResponse) => {
			if (errorResponse) {
				throw new Error(errorResponse.title);
			}
		})
		.catch((error) => {
			onError(error);
		});
}

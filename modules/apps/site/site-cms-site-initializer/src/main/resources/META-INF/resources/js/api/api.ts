/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API as objectAPI} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';

export const {save: saveObject} = objectAPI;

const UNEXPECTED_ERROR_MESSAGE = Liferay.Language.get(
	'an-unexpected-error-occurred'
);

type PostFormDataResult = {
	errorMessage?: string | undefined;
	success: boolean;
};

export async function postFormData(
	formData: FormData,
	url: string
): Promise<PostFormDataResult> {
	try {
		await saveObject({
			item: formData,
			method: 'POST',
			url,
		});

		return {
			success: true,
		};
	}
	catch (error) {
		return {
			errorMessage: (error as Error).message || UNEXPECTED_ERROR_MESSAGE,
			success: false,
		};
	}
}

const headers = new Headers({
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

export async function fetchJSON<T>(input: RequestInfo, init?: RequestInit) {
	const result = await fetch(input, {headers, method: 'GET', ...init});

	return (await result.json()) as T;
}

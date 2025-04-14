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

type RequestHandlerResult<T> = {
	data?: T;
	errorMessage?: string;
	success: boolean;
};

export async function handleRequest<T>(
	fetcher: () => Promise<Response>,
	{returnValue = false}: {returnValue?: boolean} = {}
): Promise<RequestHandlerResult<T>> {
	try {
		const response = await fetcher();

		if (response.status === 401) {
			window.location.reload();
		}

		if (!response.ok) {
			let errorMessage = UNEXPECTED_ERROR_MESSAGE;

			try {
				const {message, title} = await response.json();

				errorMessage = title ?? message ?? UNEXPECTED_ERROR_MESSAGE;

				if (Array.isArray(errorMessage)) {
					errorMessage = JSON.stringify(errorMessage);
				}
			}
			catch (error) {
				throw new Error(UNEXPECTED_ERROR_MESSAGE);
			}

			throw new Error(errorMessage);
		}

		let data: T | undefined;

		if (returnValue) {
			data = await response.json();
		}

		return {
			data,
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

export async function fetchJSON<T>(input: RequestInfo, init?: RequestInit) {
	const result = await fetch(input, {headers, method: 'GET', ...init});

	return (await result.json()) as T;
}

export async function postScopeScopeKeyObjectEntryFolder(
	scopeKey: string,
	name: string,
	parentObjectEntryFolderExternalReferenceCode: string
) {
	return await handleRequest(() =>
		fetch(
			`/o/headless-object/v1.0/scopes/${scopeKey}/object-entry-folders`,
			{
				body: JSON.stringify({
					name,
					parentObjectEntryFolderExternalReferenceCode,
				}),
				headers,
				method: 'POST',
			}
		)
	);
}

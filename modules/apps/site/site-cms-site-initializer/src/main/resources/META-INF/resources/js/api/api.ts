/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {API as objectAPI} from '@liferay/object-js-components-web';

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

const delay = (ms: number) => new Promise((resolve) => setTimeout(resolve, ms));

export async function getAssetsLibrariesByCompany() {
	await delay(1000);

	return [
		{
			id: 1,
			name: 'Space 1',
		},
		{
			id: 2,
			name: 'Space 2',
		},
	];
}

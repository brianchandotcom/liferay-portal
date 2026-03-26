/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {fetch, sub} from 'frontend-js-web';

import {OverwriteStrategy} from './ImportOptionsModal';
import {Results} from './ImportResults';

interface ResponseAPI {
	hasConflicts: boolean;
	importResults: Results;
}

interface ImportZipFileProps {
	file: File | null;
	handleResponse?: (response: ResponseAPI, file: File) => void;
	importURL: string;
	marketplace?: boolean;
	overwriteStrategy?: OverwriteStrategy;
	portletNamespace: string;
}

export default async function importZipFile({
	file,
	handleResponse,
	importURL,
	marketplace = false,
	overwriteStrategy,
	portletNamespace,
}: ImportZipFileProps) {
	const formData = new FormData();

	if (!file) {
		if (process.env.NODE_ENV === 'development') {
			console.error('importZipFile: No file provided for import.');
		}

		return;
	}

	formData.append(`${portletNamespace}file`, file);
	formData.append(`${portletNamespace}marketplace`, String(marketplace));

	if (overwriteStrategy) {
		formData.append(`${portletNamespace}importType`, overwriteStrategy);
	}

	try {
		const response = await fetch(importURL, {
			body: formData,
			method: 'POST',
		});

		if (!response.ok) {
			throw new Error(`HTTP error! status: ${response.status}`);
		}

		const jsonResponse: ResponseAPI = await response.json();

		handleResponse?.(jsonResponse, file);
	}
	catch (error: any) {
		if (process.env.NODE_ENV === 'development') {
			console.error('importZipFile: Import failed.', error);
		}

		openToast({
			message: sub(
				Liferay.Language.get(
					'something-went-wrong-and-the-x-could-not-be-imported'
				),
				file?.name || ''
			),
			type: 'danger',
		});
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {fetch, sub} from 'frontend-js-web';

import {OverwriteStrategy} from './ImportOptionsModal';
import {Results} from './ImportResults';

interface ResponseAPI {
	error?: string;
	hasConflicts: boolean;
	importResults?: Results;
	needsFragmentCollection?: boolean;
}

interface ImportZipFileProps {
	file: File | null;
	fragmentCollectionId?: number;
	handleResponse?: (response: ResponseAPI, file: File) => void;
	importURL: string;
	marketplace?: boolean;
	overwriteStrategy?: OverwriteStrategy;
	portletNamespace: string;
}

export default async function importZipFile({
	file,
	fragmentCollectionId,
	handleResponse,
	importURL,
	marketplace = false,
	overwriteStrategy,
	portletNamespace,
}: ImportZipFileProps): Promise<ResponseAPI | void> {
	if (!file) {
		if (process.env.NODE_ENV === 'development') {
			console.error('importZipFile: No file provided for import.');
		}

		return;
	}

	const formData = new FormData();

	formData.append(`${portletNamespace}file`, file);
	formData.append(`${portletNamespace}marketplace`, String(marketplace));

	if (fragmentCollectionId) {
		formData.append(
			`${portletNamespace}fragmentCollectionId`,
			String(fragmentCollectionId)
		);
	}

	if (overwriteStrategy) {
		formData.append(`${portletNamespace}importType`, overwriteStrategy);
	}

	const openToastError = () => {
		openToast({
			message: sub(
				Liferay.Language.get(
					'something-went-wrong-and-the-x-could-not-be-imported'
				),
				file.name
			) as string,
			type: 'danger',
		});
	};

	try {
		const response = await fetch(importURL, {
			body: formData,
			method: 'POST',
		});

		if (!response.ok) {
			throw new Error(`HTTP error! status: ${response.status}`);
		}

		const jsonResponse: ResponseAPI = await response.json();

		if (jsonResponse.error) {
			if (process.env.NODE_ENV === 'development') {
				console.error(
					'importZipFile: Import failed.',
					new Error(jsonResponse.error)
				);
			}

			openToastError();

			return;
		}

		handleResponse?.(jsonResponse, file);

		return jsonResponse;
	}
	catch (error: unknown) {
		if (process.env.NODE_ENV === 'development') {
			console.error('importZipFile: Import failed.', error);
		}

		openToastError();
	}
}

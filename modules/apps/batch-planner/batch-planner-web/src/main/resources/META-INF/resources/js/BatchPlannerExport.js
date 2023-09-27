/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {downloadFile} from './DownloadHelper';
import {
	EXPORT_FILE_NAME,
	HEADERS,
	HEADLESS_BATCH_ENGINE_URL,
} from './constants';

export function getExportTaskStatusURL(externalReferenceCode) {
	return `${HEADLESS_BATCH_ENGINE_URL}/export-task/by-external-reference-code/${externalReferenceCode}`;
}

export async function exportStatus(externalReferenceCode) {
	const response = await fetch(
		getExportTaskStatusURL(externalReferenceCode),
		{
			headers: HEADERS,
		}
	);

	if (!response.ok) {
		throw new Error(response);
	}

	return await response.json();
}

export function fetchExportedFile(externalReferenceCode) {
	downloadFile({
		externalReferenceCode,
		fileName: EXPORT_FILE_NAME,
		fileType: 'exportFile',
	});
}

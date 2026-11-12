/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ImportUpload, ImportUploadRequest} from '../types/importUpload';
import ApiHelper from './ApiHelper';

function deleteImportUpload(
	importUploadAPIURL: string,
	importUploadId: string
) {
	return ApiHelper.delete(
		getImportUploadURL(importUploadAPIURL, importUploadId)
	);
}

function getImportUpload(importUploadAPIURL: string, importUploadId: string) {
	return ApiHelper.get<ImportUpload>(
		getImportUploadURL(importUploadAPIURL, importUploadId)
	);
}

function getImportUploadURL(
	importUploadAPIURL: string,
	importUploadId: string
) {
	const [importUploadsURL] = importUploadAPIURL.split('?');

	return `${importUploadsURL}/${importUploadId}`;
}

function postImportUpload(
	importUploadRequest: ImportUploadRequest,
	importUploadsURL: string
) {
	return ApiHelper.post<ImportUpload>(importUploadsURL, importUploadRequest);
}

function putImportUploadChunk(
	blob: Blob,
	chunkIndex: number,
	importUploadAPIURL: string,
	importUploadId: string,
	onProgress?: (loadedBytes: number) => void,
	signal?: AbortSignal
) {
	return ApiHelper.putBlobWithProgress<ImportUpload>(
		`${getImportUploadURL(importUploadAPIURL, importUploadId)}/chunks/${chunkIndex}`,
		blob,
		onProgress,
		signal
	);
}

export default {
	deleteImportUpload,
	getImportUpload,
	postImportUpload,
	putImportUploadChunk,
};

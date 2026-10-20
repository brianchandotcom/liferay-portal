/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ImportUpload, ImportUploadRequest} from '../types/importUpload';
import ApiHelper from './ApiHelper';

function deleteImportUpload(importUploadURL: string) {
	return ApiHelper.delete(importUploadURL);
}

function getImportUpload(importUploadURL: string) {
	return ApiHelper.get<ImportUpload>(importUploadURL);
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
	importUploadURL: string,
	onProgress?: (loadedBytes: number) => void,
	signal?: AbortSignal
) {
	return ApiHelper.putBlobWithProgress<ImportUpload>(
		`${importUploadURL}/chunks/${chunkIndex}`,
		blob,
		onProgress,
		signal
	);
}

export default {
	deleteImportUpload,
	getImportUpload,
	getImportUploadURL,
	postImportUpload,
	putImportUploadChunk,
};

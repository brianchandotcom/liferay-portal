/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ImportPreview} from './exportImportPreview';

export interface ImportUpload {
	chunkSize: number;
	errorMessage?: string;
	fileName: string;
	id: string;
	importPreview?: ImportPreview;
	percentage?: number;
	size: number;
	status: ImportUploadStatus;
	totalChunksCount: number;
	uploadedChunksCount: number;
}

export interface ImportUploadRequest {
	contentType?: string;
	fileName: string;
	size: number;
}

export type ImportUploadStatus =
	| 'FAILED'
	| 'READY'
	| 'UPLOADING'
	| 'VALIDATING';

export interface UploadProgress {
	percent: number;
	phase: 'UPLOADING' | 'VALIDATING';
}

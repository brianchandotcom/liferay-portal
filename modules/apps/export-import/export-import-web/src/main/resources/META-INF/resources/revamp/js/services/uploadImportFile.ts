/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ImportPreview} from '../types/exportImportPreview';
import {ImportUpload, UploadProgress} from '../types/importUpload';
import {ABORTED_ERROR, RequestResult} from './ApiHelper';
import ImportUploadService from './ImportUploadService';

const ABORTED_RESULT = {data: null, error: ABORTED_ERROR} as const;

const MAX_RETRY_COUNT = 8;

const MAX_RETRY_DELAY = 30000;

const POLLING_INTERVAL = 1000;

const RETRY_DELAY = 1000;

export interface UploadImportFileResult {
	importPreview: ImportPreview;
	importUploadId: string;
}

function isRetryable(result: RequestResult<unknown>) {
	return (
		result.error !== ABORTED_RESULT.error &&
		(!result.status || Number(result.status) >= 500)
	);
}

function wait(delay: number, signal?: AbortSignal) {
	return new Promise<void>((resolve) => {
		const abortController = new AbortController();

		const timeout = setTimeout(() => {
			abortController.abort();

			resolve();
		}, delay);

		signal?.addEventListener(
			'abort',
			() => {
				clearTimeout(timeout);

				resolve();
			},
			{signal: abortController.signal}
		);
	});
}

async function withRetries<T>(
	request: () => Promise<RequestResult<T>>,
	signal?: AbortSignal
): Promise<RequestResult<T>> {
	let result = await request();

	for (
		let retryCount = 0;
		retryCount < MAX_RETRY_COUNT && result.error && isRetryable(result);
		retryCount++
	) {
		await wait(
			Math.min(RETRY_DELAY * 2 ** retryCount, MAX_RETRY_DELAY),
			signal
		);

		if (signal?.aborted) {
			return ABORTED_RESULT;
		}

		result = await request();
	}

	return result;
}

async function uploadChunks(
	file: File,
	importUpload: ImportUpload,
	importUploadAPIURL: string,
	onProgress: (progress: UploadProgress) => void,
	signal?: AbortSignal
): Promise<RequestResult<ImportUpload>> {
	let result: RequestResult<ImportUpload> = {data: importUpload, error: null};

	for (
		let chunkIndex = Math.min(
			importUpload.uploadedChunksCount,
			importUpload.totalChunksCount - 1
		);
		chunkIndex < importUpload.totalChunksCount;
		chunkIndex++
	) {
		const start = chunkIndex * importUpload.chunkSize;

		result = await withRetries(
			() =>
				ImportUploadService.putImportUploadChunk(
					file.slice(start, start + importUpload.chunkSize),
					chunkIndex,
					importUploadAPIURL,
					importUpload.id,
					(loadedBytes) =>
						onProgress({
							percent: Math.min(
								Math.floor(
									((start + loadedBytes) * 100) /
										Math.max(file.size, 1)
								),
								100
							),
							phase: 'UPLOADING',
						}),
					signal
				),
			signal
		);

		if (result.error) {
			return result;
		}
	}

	return result;
}

async function waitForValidation(
	importUploadAPIURL: string,
	importUploadId: string,
	onProgress: (progress: UploadProgress) => void,
	signal?: AbortSignal
): Promise<RequestResult<ImportUpload>> {
	onProgress({percent: 0, phase: 'VALIDATING'});

	while (!signal?.aborted) {
		const result = await withRetries(
			() =>
				ImportUploadService.getImportUpload(
					importUploadAPIURL,
					importUploadId
				),
			signal
		);

		if (signal?.aborted) {
			break;
		}

		if (!result.data) {
			return result;
		}

		const {errorMessage, percentage, status} = result.data;

		if (status === 'FAILED') {
			ImportUploadService.deleteImportUpload(
				importUploadAPIURL,
				importUploadId
			);

			return {
				data: null,
				error:
					errorMessage ||
					Liferay.Language.get('an-unexpected-error-occurred'),
			};
		}

		if (status === 'READY') {
			return result;
		}

		if (status === 'UPLOADING') {
			return {
				data: null,
				error: Liferay.Language.get(
					'the-upload-was-interrupted-select-the-same-file-again-to-resume-it'
				),
			};
		}

		onProgress({percent: percentage ?? 0, phase: 'VALIDATING'});

		await wait(POLLING_INTERVAL, signal);
	}

	return ABORTED_RESULT;
}

async function getOrPostImportUpload(
	file: File,
	importUploadAPIURL: string,
	importUploadId?: string
): Promise<RequestResult<ImportUpload>> {
	if (importUploadId) {
		const importUpload = await getResumableImportUpload(
			file,
			importUploadAPIURL,
			importUploadId
		);

		if (importUpload) {
			return {data: importUpload, error: null};
		}
	}

	return ImportUploadService.postImportUpload(
		{
			contentType: file.type || 'application/zip',
			fileName: file.name,
			size: file.size,
		},
		importUploadAPIURL
	);
}

async function getResumableImportUpload(
	file: File,
	importUploadAPIURL: string,
	importUploadId: string
): Promise<ImportUpload | null> {
	const result = await ImportUploadService.getImportUpload(
		importUploadAPIURL,
		importUploadId
	);

	if (
		!result.data ||
		result.data.status === 'FAILED' ||
		result.data.fileName !== file.name ||
		result.data.size !== file.size
	) {
		return null;
	}

	return result.data;
}

export async function uploadImportFile({
	file,
	importUploadAPIURL,
	importUploadId,
	onImportUpload,
	onProgress,
	signal,
}: {
	file: File;
	importUploadAPIURL: string;
	importUploadId?: string;
	onImportUpload?: (importUploadId: string) => void;
	onProgress: (progress: UploadProgress) => void;
	signal?: AbortSignal;
}): Promise<RequestResult<UploadImportFileResult>> {
	const importUploadResult = await getOrPostImportUpload(
		file,
		importUploadAPIURL,
		importUploadId
	);

	if (!importUploadResult.data) {
		return importUploadResult;
	}

	const importUpload = importUploadResult.data;

	onImportUpload?.(importUpload.id);

	const handleAbort = () => {
		ImportUploadService.deleteImportUpload(
			importUploadAPIURL,
			importUpload.id
		);
	};

	if (signal?.aborted) {
		handleAbort();

		return ABORTED_RESULT;
	}

	signal?.addEventListener('abort', handleAbort, {once: true});

	try {
		if (importUpload.status === 'UPLOADING') {
			const uploadResult = await uploadChunks(
				file,
				importUpload,
				importUploadAPIURL,
				onProgress,
				signal
			);

			if (!uploadResult.data) {
				if (uploadResult.error === ABORTED_RESULT.error) {
					return uploadResult;
				}

				if (isRetryable(uploadResult)) {
					return {
						data: null,
						error: Liferay.Language.get(
							'the-upload-was-interrupted-select-the-same-file-again-to-resume-it'
						),
					};
				}

				ImportUploadService.deleteImportUpload(
					importUploadAPIURL,
					importUpload.id
				);

				return uploadResult;
			}
		}

		const result = await waitForValidation(
			importUploadAPIURL,
			importUpload.id,
			onProgress,
			signal
		);

		if (!result.data) {
			return result;
		}

		return {
			data: {
				importPreview: result.data.importPreview as ImportPreview,
				importUploadId: result.data.id,
			},
			error: null,
		};
	}
	finally {
		signal?.removeEventListener('abort', handleAbort);
	}
}

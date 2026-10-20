/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetch from 'jest-fetch-mock';

import {uploadImportFile} from '../../../../src/main/resources/META-INF/resources/revamp/js/services/uploadImportFile';
import {FakeXMLHttpRequest} from '../mocks/FakeXMLHttpRequest';

const IMPORT_UPLOADS_URL = '/o/export-import/v1.0/sites/L_GUEST/import-uploads';

const IMPORT_UPLOAD_URL = `${IMPORT_UPLOADS_URL}/upload`;

const IMPORT_UPLOADS_API_URL = `${IMPORT_UPLOADS_URL}?portletId=portlet&sitePageExternalReferenceCode=page`;

const mockImportUpload = (properties: object) =>
	JSON.stringify({
		chunkSize: 4,
		fileName: 'site.lar',
		id: 'upload',
		size: 10,
		status: 'UPLOADING',
		totalChunksCount: 3,
		uploadedChunksCount: 0,
		...properties,
	});

const getFetchCall = (index: number) => ({
	method: fetch.mock.calls[index][1]?.method ?? 'GET',
	url: String(fetch.mock.calls[index][0]),
});

const onImportUpload = jest.fn();

const onProgress = jest.fn();

const runTimersImmediately = () =>
	jest.spyOn(window, 'setTimeout').mockImplementation(((
		callback: () => void
	) => {
		callback();

		return 0;
	}) as unknown as typeof setTimeout);

const upload = ({
	importUploadId,
	signal,
}: {importUploadId?: string; signal?: AbortSignal} = {}) =>
	uploadImportFile({
		file: new File(['0123456789'], 'site.lar'),
		importUploadAPIURL: IMPORT_UPLOADS_API_URL,
		importUploadId,
		onImportUpload,
		onProgress,
		signal,
	});

describe('uploadImportFile', () => {
	beforeEach(() => {
		jest.clearAllMocks();

		FakeXMLHttpRequest.reset();

		FakeXMLHttpRequest.defaultResponse = {body: {}, status: 200};

		jest.spyOn(window, 'XMLHttpRequest').mockImplementation(
			() => new FakeXMLHttpRequest() as unknown as XMLHttpRequest
		);
	});

	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('uploads the file in chunks and resolves the preview once the server validates it', async () => {
		fetch.mockResponses(
			mockImportUpload({}),
			mockImportUpload({
				importPreview: {fileName: 'site.lar'},
				status: 'READY',
			})
		);

		const result = await upload();

		expect(result).toEqual({
			data: {
				importPreview: {fileName: 'site.lar'},
				importUploadId: 'upload',
			},
			error: null,
		});

		expect(JSON.parse(String(fetch.mock.calls[0][1]?.body))).toEqual({
			contentType: 'application/zip',
			fileName: 'site.lar',
			size: 10,
		});
		expect(getFetchCall(0)).toEqual({
			method: 'POST',
			url: IMPORT_UPLOADS_API_URL,
		});

		expect(
			FakeXMLHttpRequest.requests.map(({body, url}) => [url, body?.size])
		).toEqual([
			[`${IMPORT_UPLOAD_URL}/chunks/0`, 4],
			[`${IMPORT_UPLOAD_URL}/chunks/1`, 4],
			[`${IMPORT_UPLOAD_URL}/chunks/2`, 2],
		]);

		expect(onProgress).toHaveBeenCalledWith({
			percent: 40,
			phase: 'UPLOADING',
		});
		expect(onProgress).toHaveBeenCalledWith({
			percent: 100,
			phase: 'UPLOADING',
		});
	});

	it('reports the validation progress of the server until the upload is ready', async () => {
		fetch.mockResponses(
			mockImportUpload({totalChunksCount: 1}),
			mockImportUpload({percentage: 30, status: 'VALIDATING'}),
			mockImportUpload({importPreview: {}, status: 'READY'})
		);

		await upload();

		expect(onProgress).toHaveBeenLastCalledWith({
			percent: 30,
			phase: 'VALIDATING',
		});
	});

	it('waits longer before each retry of a chunk', async () => {
		const setTimeoutSpy = runTimersImmediately();

		FakeXMLHttpRequest.responses = [
			'networkError',
			'networkError',
			'networkError',
		];

		fetch.mockResponses(
			mockImportUpload({totalChunksCount: 1}),
			mockImportUpload({importPreview: {}, status: 'READY'})
		);

		const result = await upload();

		expect(result.error).toBeNull();
		expect(FakeXMLHttpRequest.requests).toHaveLength(4);
		expect(
			setTimeoutSpy.mock.calls
				.map(([, delay]) => delay)
				.filter((delay) => delay !== undefined)
		).toEqual([1000, 2000, 4000]);
	});

	it('keeps the upload and asks for the same file again when a chunk keeps failing', async () => {
		runTimersImmediately();

		FakeXMLHttpRequest.responses = Array(9).fill('networkError');

		fetch.mockResponses(mockImportUpload({totalChunksCount: 1}));

		const result = await upload();

		expect(result).toEqual({
			data: null,
			error: 'the-upload-was-interrupted-select-the-same-file-again-to-resume-it',
		});
		expect(FakeXMLHttpRequest.requests).toHaveLength(9);
		expect(fetch).toHaveBeenCalledTimes(1);
		expect(onImportUpload).toHaveBeenCalledWith('upload');
	});

	it('keeps the upload and asks for the same file again when a chunk is missing after the upload', async () => {
		fetch.mockResponses(
			mockImportUpload({totalChunksCount: 1}),
			mockImportUpload({totalChunksCount: 1})
		);

		const result = await upload();

		expect(result).toEqual({
			data: null,
			error: 'the-upload-was-interrupted-select-the-same-file-again-to-resume-it',
		});
		expect(fetch).toHaveBeenCalledTimes(2);
		expect(getFetchCall(1)).toEqual({
			method: 'GET',
			url: IMPORT_UPLOAD_URL,
		});
	});

	it('retries the validation status after a network error', async () => {
		runTimersImmediately();

		fetch
			.mockResponseOnce(mockImportUpload({totalChunksCount: 1}))
			.mockRejectOnce(new Error('Network error'))
			.mockResponseOnce(
				mockImportUpload({importPreview: {}, status: 'READY'})
			);

		const result = await upload();

		expect(result.error).toBeNull();
		expect(fetch).toHaveBeenCalledTimes(3);
	});

	it('resumes an interrupted upload from the first missing chunk', async () => {
		fetch.mockResponses(
			mockImportUpload({uploadedChunksCount: 2}),
			mockImportUpload({importPreview: {}, status: 'READY'})
		);

		const result = await upload({importUploadId: 'upload'});

		expect(result.error).toBeNull();
		expect(getFetchCall(0)).toEqual({
			method: 'GET',
			url: IMPORT_UPLOAD_URL,
		});
		expect(
			FakeXMLHttpRequest.requests.map(({body, url}) => [url, body?.size])
		).toEqual([[`${IMPORT_UPLOAD_URL}/chunks/2`, 2]]);
		expect(onProgress).toHaveBeenCalledWith({
			percent: 100,
			phase: 'UPLOADING',
		});
	});

	it('sends the last chunk again to validate an interrupted upload that has every chunk', async () => {
		fetch.mockResponses(
			mockImportUpload({uploadedChunksCount: 3}),
			mockImportUpload({importPreview: {}, status: 'READY'})
		);

		const result = await upload({importUploadId: 'upload'});

		expect(result.error).toBeNull();
		expect(
			FakeXMLHttpRequest.requests.map(({body, url}) => [url, body?.size])
		).toEqual([[`${IMPORT_UPLOAD_URL}/chunks/2`, 2]]);
	});

	it('starts a new upload when the interrupted one no longer exists', async () => {
		fetch
			.mockResponseOnce(JSON.stringify({title: 'Not Found'}), {
				status: 404,
			})
			.mockResponseOnce(mockImportUpload({totalChunksCount: 1}))
			.mockResponseOnce(
				mockImportUpload({importPreview: {}, status: 'READY'})
			);

		const result = await upload({importUploadId: 'gone'});

		expect(result.error).toBeNull();
		expect(getFetchCall(1)).toEqual({
			method: 'POST',
			url: IMPORT_UPLOADS_API_URL,
		});
	});

	it('does not retry a chunk that the server rejects and deletes the upload', async () => {
		FakeXMLHttpRequest.responses = [
			{body: {title: 'Wrong size'}, status: 400},
		];

		fetch.mockResponses(mockImportUpload({totalChunksCount: 1}), '');

		const result = await upload();

		expect(result).toEqual({
			data: null,
			error: 'Wrong size',
			status: '400',
		});
		expect(FakeXMLHttpRequest.requests).toHaveLength(1);
		expect(getFetchCall(1)).toEqual({
			method: 'DELETE',
			url: IMPORT_UPLOAD_URL,
		});
	});

	it('resolves the validation error and deletes the upload', async () => {
		fetch.mockResponses(
			mockImportUpload({totalChunksCount: 1}),
			mockImportUpload({errorMessage: 'Invalid LAR', status: 'FAILED'}),
			''
		);

		const result = await upload();

		expect(result).toEqual({data: null, error: 'Invalid LAR'});
		expect(getFetchCall(2)).toEqual({
			method: 'DELETE',
			url: IMPORT_UPLOAD_URL,
		});
	});

	it('deletes the upload when it is aborted', async () => {
		const abortController = new AbortController();

		fetch.mockResponses(mockImportUpload({totalChunksCount: 1}), '');

		onProgress.mockImplementationOnce(() => abortController.abort());

		const result = await upload({signal: abortController.signal});

		expect(result).toEqual({data: null, error: 'Aborted'});
		expect(getFetchCall(1)).toEqual({
			method: 'DELETE',
			url: IMPORT_UPLOAD_URL,
		});
	});
});

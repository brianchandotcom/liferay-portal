/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ApiHelper from '../../../../src/main/resources/META-INF/resources/revamp/js/services/ApiHelper';
import {FakeXMLHttpRequest} from '../mocks/FakeXMLHttpRequest';

describe('ApiHelper.putBlobWithProgress', () => {
	beforeEach(() => {
		FakeXMLHttpRequest.reset();

		jest.spyOn(window, 'XMLHttpRequest').mockImplementation(
			() => new FakeXMLHttpRequest() as unknown as XMLHttpRequest
		);
	});

	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('sends the blob as the raw body of a PUT request', () => {
		const blob = new Blob(['chunk']);

		ApiHelper.putBlobWithProgress('/chunks/0', blob);

		const xhr = FakeXMLHttpRequest.current;

		expect(xhr.body).toBe(blob);
		expect(xhr.headers['Content-Type']).toBe('application/octet-stream');
		expect(xhr.method).toBe('PUT');
		expect(xhr.url).toBe('/chunks/0');
	});

	it('reports the bytes sent so far', () => {
		const onProgress = jest.fn();

		ApiHelper.putBlobWithProgress('/chunks/0', new Blob(), onProgress);

		FakeXMLHttpRequest.current.upload.onprogress!({loaded: 512});

		expect(onProgress).toHaveBeenLastCalledWith(512);
	});

	it('resolves the response body when the server accepts the chunk', async () => {
		const promise = ApiHelper.putBlobWithProgress('/chunks/0', new Blob());

		FakeXMLHttpRequest.current.respond(200, {id: 'upload'});

		await expect(promise).resolves.toEqual({
			data: {id: 'upload'},
			error: null,
		});
	});

	it('resolves the error and the status when the server rejects the chunk', async () => {
		const promise = ApiHelper.putBlobWithProgress('/chunks/0', new Blob());

		FakeXMLHttpRequest.current.respond(400, {title: 'Wrong size'});

		await expect(promise).resolves.toEqual({
			data: null,
			error: 'Wrong size',
			status: '400',
		});
	});

	it('resolves an aborted result when the signal is aborted', async () => {
		const abortController = new AbortController();

		const promise = ApiHelper.putBlobWithProgress(
			'/chunks/0',
			new Blob(),
			undefined,
			abortController.signal
		);

		abortController.abort();

		await expect(promise).resolves.toEqual({data: null, error: 'Aborted'});
	});
});

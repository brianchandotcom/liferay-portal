/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {downloadBlob} from '../../../../src/main/resources/META-INF/resources/js/common/utils/downloadBlob';

describe('downloadBlob', () => {
	let createObjectURLMock: jest.Mock;
	let revokeObjectURLMock: jest.Mock;
	let appendChildMock: jest.SpyInstance;
	let removeChildMock: jest.SpyInstance;
	let clickMock: jest.Mock;
	let lastCreatedLink: {
		click: jest.Mock;
		download: string;
		href: string;
	};

	beforeEach(() => {
		createObjectURLMock = jest.fn().mockReturnValue('blob:mock-url');
		revokeObjectURLMock = jest.fn();
		clickMock = jest.fn();

		global.URL.createObjectURL = createObjectURLMock;
		global.URL.revokeObjectURL = revokeObjectURLMock;

		appendChildMock = jest
			.spyOn(document.body, 'appendChild')
			.mockImplementation(() => ({}) as any);
		removeChildMock = jest
			.spyOn(document.body, 'removeChild')
			.mockImplementation(() => ({}) as any);

		jest.spyOn(document, 'createElement').mockImplementation(
			(tagName: string) => {
				if (tagName === 'a') {
					lastCreatedLink = {
						click: clickMock,
						download: '',
						href: '',
					};

					return lastCreatedLink as any;
				}

				return {} as any;
			}
		);
	});

	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('downloads a file from a Blob', async () => {
		const blob = new Blob(['test content'], {type: 'text/plain'});

		await downloadBlob(blob, 'test.txt');

		expect(createObjectURLMock).toHaveBeenCalledWith(blob);
		expect(clickMock).toHaveBeenCalled();
		expect(revokeObjectURLMock).toHaveBeenCalledWith('blob:mock-url');
		expect(appendChildMock).toHaveBeenCalled();
		expect(removeChildMock).toHaveBeenCalled();
	});

	it('downloads a file from a Promise<Blob>', async () => {
		const blob = new Blob(['test content'], {type: 'text/plain'});
		const promise = Promise.resolve(blob);

		await downloadBlob(promise, 'test.txt');

		expect(createObjectURLMock).toHaveBeenCalledWith(blob);
		expect(clickMock).toHaveBeenCalled();
	});

	it('downloads a file from a Response with Content-Disposition header', async () => {
		const blob = new Blob(['test content'], {type: 'text/plain'});
		const response = new Response(blob, {
			headers: {
				'Content-Disposition':
					'attachment; filename="filename-from-header.txt"',
			},
		});

		jest.spyOn(response, 'blob').mockResolvedValue(blob);

		await downloadBlob(response);

		expect(createObjectURLMock).toHaveBeenCalledWith(blob);
		expect(clickMock).toHaveBeenCalled();

		expect(lastCreatedLink.download).toBe('filename-from-header.txt');
	});

	it('downloads a file from a Response without Content-Disposition header (using default)', async () => {
		const blob = new Blob(['test content'], {type: 'text/plain'});
		const response = new Response(blob);

		jest.spyOn(response, 'blob').mockResolvedValue(blob);

		await downloadBlob(response);

		expect(createObjectURLMock).toHaveBeenCalledWith(blob);

		expect(lastCreatedLink.download).toBe('export.zip');
	});

	it('uses the provided filename even if Response has Content-Disposition', async () => {
		const blob = new Blob(['test content'], {type: 'text/plain'});
		const response = new Response(blob, {
			headers: {
				'Content-Disposition': 'attachment; filename="ignored.txt"',
			},
		});

		jest.spyOn(response, 'blob').mockResolvedValue(blob);

		await downloadBlob(response, 'preferred.txt');

		expect(createObjectURLMock).toHaveBeenCalledWith(blob);

		expect(lastCreatedLink.download).toBe('preferred.txt');
	});

	it('throws an error if no filename is provided for non-Response data', async () => {
		const blob = new Blob(['test content'], {type: 'text/plain'});

		await expect(downloadBlob(blob)).rejects.toThrow(
			'Filename is required'
		);
	});
});

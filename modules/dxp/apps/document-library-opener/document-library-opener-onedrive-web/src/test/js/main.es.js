/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

'use strict';

import DocumentLibraryOpener from '../../../src/main/resources/META-INF/resources/js/main.es.js';

function replyAndWait({body = {}, ms}) {
	return () => {
		jest.useRealTimers();

		setTimeout(() => {
			jest.useFakeTimers();
			jest.advanceTimersByTime(ms);
		}, 0);

		return Promise.resolve({
			body: JSON.stringify(body)
		});
	};
}

describe('DocumentLibraryOpener', () => {
	const windowOpenSpy = jest
		.spyOn(window, 'open')
		.mockImplementation(() => {});
	global.Liferay.Util.openWindow = jest
		.fn()
		.mockImplementation((_, cb) => cb());
	global.Liferay.Util.getWindow = () => ({hide: jest.fn()});

	describe('.edit()', () => {
		const FETCH_STATUS_URL = '//fecthStatusURL';
		const STATUS_URL = '//statusURL';
		const OFFICE365_EDIT_URL = '//office365EditURL';
		let opener;

		describe('when all is fine at the first call', () => {
			beforeEach(() => {
				windowOpenSpy.mockClear();
				global.Liferay.Util.openWindow.mockClear();

				opener = new DocumentLibraryOpener({namespace: 'namespace'});

				fetch.mockResponses(
					[
						JSON.stringify({
							oneDriveBackgroundTaskStatusURL: STATUS_URL
						})
					],
					[
						replyAndWait({
							body: {
								complete: true,
								office365EditURL: OFFICE365_EDIT_URL
							},
							ms: 2000
						})
					]
				);

				return opener.edit({formSubmitURL: FETCH_STATUS_URL});
			});

			it('Launches a modal with loading', () => {
				expect(global.Liferay.Util.openWindow).toHaveBeenCalledTimes(1);
				expect(
					global.Liferay.Util.openWindow.mock.calls[0][0].dialog
						.bodyContent
				).toContain(
					'you-are-being-redirected-to-an-external-editor-to-edit-this-document'
				);
			});

			it('then fetches the status URL', () => {
				expect(fetch.mock.calls[0][0]).toBe(FETCH_STATUS_URL);
			});

			it('then queries the status URL', () => {
				expect(fetch.mock.calls[1][0]).toBe(STATUS_URL);
			});

			it('windowOpenSpy', () => {
				expect(windowOpenSpy).toHaveBeenCalledTimes(1);
				expect(windowOpenSpy.mock.calls[0][0]).toBe(OFFICE365_EDIT_URL);
			});
		});

		describe('when recive the pulling URL an pulling to it 3 times', () => {
			beforeEach(() => {
				windowOpenSpy.mockClear();
				global.Liferay.Util.openWindow.mockClear();

				opener = new DocumentLibraryOpener({namespace: 'namespace'});

				fetch.mockResponses(
					[
						JSON.stringify({
							oneDriveBackgroundTaskStatusURL: STATUS_URL
						})
					],
					[
						replyAndWait({
							body: {
								complete: false
							},
							ms: 500
						})
					],
					[
						replyAndWait({
							body: {
								complete: true,
								office365EditURL: OFFICE365_EDIT_URL
							},
							ms: 2000
						})
					]
				);

				return opener.edit({formSubmitURL: FETCH_STATUS_URL});
			});

			it('Launches a modal with loading', () => {
				expect(global.Liferay.Util.openWindow).toHaveBeenCalledTimes(1);
				expect(
					global.Liferay.Util.openWindow.mock.calls[0][0].dialog
						.bodyContent
				).toContain(
					'you-are-being-redirected-to-an-external-editor-to-edit-this-document'
				);
			});

			it('then fetches the status URL', () => {
				expect(fetch.mock.calls[0][0]).toBe(FETCH_STATUS_URL);
			});

			it('then queries the status URL and is not complete', () => {
				expect(fetch.mock.calls[1][0]).toBe(STATUS_URL);
			});

			it('then queries the status URL and resolve', () => {
				expect(fetch.mock.calls[2][0]).toBe(STATUS_URL);
			});

			it('windowOpenSpy', () => {
				expect(windowOpenSpy).toHaveBeenCalledTimes(1);
				expect(windowOpenSpy.mock.calls[0][0]).toBe(OFFICE365_EDIT_URL);
			});
		});
	});
});

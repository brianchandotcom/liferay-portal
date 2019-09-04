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

const realSetTimeout = setTimeout;

function replyAndWait({body = {}, ms}) {
	return () => {
		realSetTimeout(() => {
			jest.advanceTimersByTime(ms);
		}, 0);

		return Promise.resolve({
			body: JSON.stringify(body)
		});
	};
}

describe('DocumentLibraryOpener', () => {
	const FETCH_STATUS_URL = '//fecthStatusURL';
	const STATUS_URL = '//statusURL';
	const OFFICE365_EDIT_URL = '//office365EditURL';
	let opener;

	beforeEach(() => {
		jest.spyOn(window, 'open').mockImplementation(() => {});
		global.Liferay.Util.openWindow = jest
			.fn()
			.mockImplementation((_, cb) => cb());
		global.Liferay.Util.getWindow = () => ({hide: jest.fn()});
		global.themeDisplay = {
			getPathThemeImages: jest.fn().mockImplementation(() => '//images/')
		};
	});

	afterEach(() => {
		window.open.mockRestore();
		delete global.Liferay.Util.openWindow;
		delete global.Liferay.Util.getWindow;
		delete global.themeDisplay;
	});

	beforeEach(() => {
		jest.useFakeTimers();
	});

	afterEach(() => {
		jest.useRealTimers();
	});

	beforeEach(() => {
		opener = new DocumentLibraryOpener({namespace: 'namespace'});
		jest.spyOn(opener, 'showError');
	});

	describe('.edit()', () => {
		describe('when the background task finishes before the first polling request', () => {
			beforeEach(() => {
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

			it('opens the loading modal', () => {
				expect(global.Liferay.Util.openWindow).toHaveBeenCalledTimes(1);
				expect(
					global.Liferay.Util.openWindow.mock.calls[0][0].dialog
						.bodyContent
				).toContain(
					'you-are-being-redirected-to-an-external-editor-to-edit-this-document'
				);
			});

			it('then fetches the URL to get the task status URL', () => {
				expect(fetch.mock.calls[0][0]).toBe(FETCH_STATUS_URL);
			});

			it('then fetches the task status URL', () => {
				expect(fetch.mock.calls[1][0]).toBe(STATUS_URL);
			});

			it('and, since the task has already finished, navigates to the edit URL', () => {
				expect(window.open).toHaveBeenCalledTimes(1);
				expect(window.open.mock.calls[0][0]).toBe(OFFICE365_EDIT_URL);
			});
		});

		describe('when the background task finishes right after the first polling request', () => {
			beforeEach(() => {
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

			it('opens the loading modal', () => {
				expect(global.Liferay.Util.openWindow).toHaveBeenCalledTimes(1);
				expect(
					global.Liferay.Util.openWindow.mock.calls[0][0].dialog
						.bodyContent
				).toContain(
					'you-are-being-redirected-to-an-external-editor-to-edit-this-document'
				);
			});

			it('then fetches the URL to get the task status URL', () => {
				expect(fetch.mock.calls[0][0]).toBe(FETCH_STATUS_URL);
			});

			it('then fetches the task status URL', () => {
				expect(fetch.mock.calls[1][0]).toBe(STATUS_URL);
			});

			it('then fetches the task status URL again', () => {
				expect(fetch.mock.calls[2][0]).toBe(STATUS_URL);
			});

			it('and, since the task has finished, navigates to the edit URL', () => {
				expect(window.open).toHaveBeenCalledTimes(1);
				expect(window.open.mock.calls[0][0]).toBe(OFFICE365_EDIT_URL);
			});
		});

		describe('when the background task fails right after the first polling request', () => {
			beforeEach(() => {
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
								error: true
							},
							ms: 2000
						})
					]
				);

				return opener.edit({formSubmitURL: FETCH_STATUS_URL});
			});

			it('opens the loading modal', () => {
				expect(global.Liferay.Util.openWindow).toHaveBeenCalledTimes(1);
				expect(
					global.Liferay.Util.openWindow.mock.calls[0][0].dialog
						.bodyContent
				).toContain(
					'you-are-being-redirected-to-an-external-editor-to-edit-this-document'
				);
			});

			it('then fetches the URL to get the task status URL', () => {
				expect(fetch.mock.calls[0][0]).toBe(FETCH_STATUS_URL);
			});

			it('then fetches the task status URL', () => {
				expect(fetch.mock.calls[1][0]).toBe(STATUS_URL);
			});

			it('then fetches the task status URL again', () => {
				expect(fetch.mock.calls[2][0]).toBe(STATUS_URL);
			});

			it('and, since the task has failed, shows an error message', () => {
				expect(opener.showError).toHaveBeenCalledTimes(1);
			});
		});
	});
});

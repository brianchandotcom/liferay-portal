/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

'use strict';

import DocumentLibraryOpener from '../../src/main/resources/META-INF/resources/js/DocumentLibraryOpener.es';

const realSetTimeout = setTimeout;

function replyAndWait({body = {}, ms}) {
	return () => {
		realSetTimeout(() => {
			jest.advanceTimersByTime(ms);
		}, 0);

		return Promise.resolve({
			body: JSON.stringify(body),
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
		global.Liferay.Portlet = {refresh: jest.fn()};
		global.Liferay.Util.openWindow = jest
			.fn()
			.mockImplementation((_, callback) => callback());
		global.Liferay.Util.getWindow = () => ({hide: jest.fn()});
		global.themeDisplay = {
			getPathThemeImages: jest.fn().mockImplementation(() => '//images/'),
		};

		jest.useFakeTimers();

		opener = new DocumentLibraryOpener({namespace: 'namespace'});
		jest.spyOn(opener, '_showError');
	});

	afterEach(() => {
		window.open.mockRestore();
		delete global.Liferay.Util.openWindow;
		delete global.Liferay.Util.getWindow;
		delete global.themeDisplay;
		delete global.Liferay.Portlet.refresh;

		jest.useRealTimers();
	});

	describe('.edit()', () => {
		describe('when the background task finishes before the first polling request', () => {
			beforeEach(() => {
				fetch.mockResponses(
					[
						JSON.stringify({
							oneDriveBackgroundTaskStatusURL: STATUS_URL,
						}),
					],
					[
						replyAndWait({
							body: {
								complete: true,
								office365EditURL: OFFICE365_EDIT_URL,
							},
							ms: 2000,
						}),
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
							oneDriveBackgroundTaskStatusURL: STATUS_URL,
						}),
					],
					[
						replyAndWait({
							body: {
								complete: false,
							},
							ms: 500,
						}),
					],
					[
						replyAndWait({
							body: {
								complete: true,
								office365EditURL: OFFICE365_EDIT_URL,
							},
							ms: 2000,
						}),
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
							oneDriveBackgroundTaskStatusURL: STATUS_URL,
						}),
					],
					[
						replyAndWait({
							body: {
								complete: false,
							},
							ms: 500,
						}),
					],
					[
						replyAndWait({
							body: {
								error: true,
							},
							ms: 2000,
						}),
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
				expect(opener._showError).toHaveBeenCalledTimes(1);
			});
		});
	});

	describe('.open()', () => {
		describe('when the creation background task finishes at the first polling request without refresh', () => {
			beforeEach(() => {
				fetch.mockResponses([
					replyAndWait({
						body: {
							complete: true,
							office365EditURL: OFFICE365_EDIT_URL,
						},
						ms: 2000,
					}),
				]);

				return opener.open({
					dialogMessage:
						'you-are-being-redirected-to-an-external-editor-to-create-this-document',
					statusURL: STATUS_URL,
				});
			});

			it('opens the loading modal with the creation message', () => {
				expect(global.Liferay.Util.openWindow).toHaveBeenCalledTimes(1);
				expect(
					global.Liferay.Util.openWindow.mock.calls[0][0].dialog
						.bodyContent
				).toContain(
					'you-are-being-redirected-to-an-external-editor-to-create-this-document'
				);
			});

			it('then fetches the task status URL', () => {
				expect(fetch.mock.calls[0][0]).toBe(STATUS_URL);
			});

			it('then since the task has already finished, navigates to the edit URL', () => {
				expect(window.open).toHaveBeenCalledTimes(1);
				expect(window.open.mock.calls[0][0]).toBe(OFFICE365_EDIT_URL);
			});

			it('and the portlet did not refresh', () => {
				expect(global.Liferay.Portlet.refresh).toHaveBeenCalledTimes(0);
			});
		});

		describe('when the creation background task finishes at the first polling request with refresh', () => {
			beforeEach(() => {
				fetch.mockResponses([
					replyAndWait({
						body: {
							complete: true,
							office365EditURL: OFFICE365_EDIT_URL,
						},
						ms: 2000,
					}),
				]);

				return opener.open({
					dialogMessage:
						'you-are-being-redirected-to-an-external-editor-to-create-this-document',
					refresh: true,
					statusURL: STATUS_URL,
				});
			});

			it('opens the loading modal with the creation message', () => {
				expect(global.Liferay.Util.openWindow).toHaveBeenCalledTimes(1);
				expect(
					global.Liferay.Util.openWindow.mock.calls[0][0].dialog
						.bodyContent
				).toContain(
					'you-are-being-redirected-to-an-external-editor-to-create-this-document'
				);
			});

			it('then fetches the task status URL', () => {
				expect(fetch.mock.calls[0][0]).toBe(STATUS_URL);
			});

			it('then since the task has already finished, navigates to the edit URL', () => {
				expect(window.open).toHaveBeenCalledTimes(1);
				expect(window.open.mock.calls[0][0]).toBe(OFFICE365_EDIT_URL);
			});

			it('and the portlet did refresh', () => {
				expect(global.Liferay.Portlet.refresh).toHaveBeenCalledTimes(1);
			});
		});
	});
});

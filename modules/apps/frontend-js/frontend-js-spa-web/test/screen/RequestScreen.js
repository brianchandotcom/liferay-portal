/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import RequestScreen from '../../src/main/resources/META-INF/resources/screen/RequestScreen';

describe('RequestScreen', () => {
	beforeEach(() => {
		Liferay.SPA = {};
	});

	it('is cacheable', () => {
		const screen = new RequestScreen();
		expect(screen.isCacheable()).toBe(true);
	});

	it('sets HTTP method', () => {
		const screen = new RequestScreen();
		expect(screen.getHttpMethod()).toBe(RequestScreen.GET);

		screen.setHttpMethod(RequestScreen.POST);
		expect(screen.getHttpMethod()).toBe(RequestScreen.POST);
	});

	it('sets HTTP headers', () => {
		const screen = new RequestScreen();

		expect(screen.getHttpHeaders()).toHaveProperty('X-PJAX', 'true');
		expect(screen.getHttpHeaders()).toHaveProperty(
			'X-Requested-With',
			'XMLHttpRequest'
		);

		screen.setHttpHeaders({});

		expect(screen.getHttpHeaders()).toEqual({});
	});

	it('sets timeout', () => {
		const screen = new RequestScreen();
		expect(screen.getTimeout()).toBe(30000);
		screen.setTimeout(0);
		expect(screen.getTimeout()).toBe(0);
	});

	it('returns request path if response.url not present on screen beforeUpdateHistoryPath', () => {
		const screen = new RequestScreen();
		jest.spyOn(screen, 'getRequest').mockImplementation(() => {
			return {
				url: '/path',
			};
		});

		expect(screen.beforeUpdateHistoryPath('/path')).toBe('/path');
	});

	it('returns responseURL if present on screen beforeUpdateHistoryPath', () => {
		const screen = new RequestScreen();

		jest.spyOn(screen, 'getResponse').mockImplementation(() => {
			return {
				url: '/redirect',
			};
		});

		jest.spyOn(screen, 'getRequest').mockImplementation(() => {
			return {
				requestPath: '/path',
				responseURL: '/redirect',
			};
		});

		expect(screen.beforeUpdateHistoryPath('/path')).toBe('/redirect');
	});

	it('returns null if form navigate to post-without-redirect-get on screen beforeUpdateHistoryState', () => {
		const screen = new RequestScreen();
		expect(
			screen.beforeUpdateHistoryState({
				form: true,
				path: '/post',
				redirectPath: '/post',
				senna: true,
			})
		).toBeNull();
	});

	it('returns null if no requests were made', () => {
		const screen = new RequestScreen();
		expect(screen.getRequestPath()).toBeNull();
	});

	it('sends request to an url', (done) => {
		fetch.mockResponse('');

		const screen = new RequestScreen();
		screen.load('/url').then(() => {
			expect(screen.getRequest().url).toBe(
				window.location.origin + '/url'
			);
			expect(screen.getRequest().requestHeaders).toHaveProperty(
				'X-PJAX',
				'true'
			);
			expect(screen.getRequest().requestHeaders).toHaveProperty(
				'X-Requested-With',
				'XMLHttpRequest'
			);
			done();
		});
	});

	it('loads response content from cache', (done) => {
		fetch.mockResponse('');

		const screen = new RequestScreen();
		const cache = {};
		screen.addCache(cache);
		screen.load('/url').then((cachedContent) => {
			expect(cachedContent).toBe(cache);
			done();
		});
	});

	it('does not load response content from cache for post requests', (done) => {
		fetch.mockResponse('');

		const screen = new RequestScreen();
		const cache = {};
		screen.setHttpMethod(RequestScreen.POST);
		screen.load('/url').then(() => {
			screen.load('/url').then((cachedContent) => {
				expect(cachedContent).not.toBe(cache);
				done();
			});
		});
	});

	it.skip('cancels load request to an url', (done) => {
		fetch.mockResponse('');

		const screen = new RequestScreen();
		screen
			.load('/url')
			.then(() => done.fail())
			.catch(() => {
				expect(fetch.mock.calls.length).toBe(0);
				done();
			})
			.cancel();
	});

	it('fails for timeout request', (done) => {
		fetch.mockResponse(
			() =>
				new Promise((resolve) => {
					setTimeout(() => {
						resolve('');
					}, 100);
				})
		);

		const screen = new RequestScreen();
		screen.setTimeout(0);

		screen.load('/url').catch((reason) => {
			expect(reason.timeout).toBeTruthy();
			done();
		});
	});

	it('fails for invalid status code response', (done) => {
		fetch.mockResponse('', {status: 404});

		new RequestScreen().load('/url').catch((error) => {
			expect(error.invalidStatus).toBeTruthy();
			done();
		});
	});

	it('returns the correct http status code for "page not found"', (done) => {
		fetch.mockResponse('', {status: 404});

		new RequestScreen().load('/url').catch((error) => {
			expect(error.statusCode).toBe(404);
			done();
		});
	});

	it('returns the correct http status code for "unauthorised"', (done) => {
		fetch.mockResponse('', {status: 401});

		new RequestScreen().load('/url').catch((error) => {
			expect(error.statusCode).toBe(401);
			done();
		});
	});

	it('fails for request errors response', (done) => {
		fetch.mockReject(new Error('Request error'));

		new RequestScreen().load('/url').catch((error) => {
			expect(error.requestError).toBeTruthy();
			done();
		});
	});

	it('forces post method and request body wrapped in FormData', (done) => {
		fetch.mockResponse('');

		Liferay.SPA.__capturedFormElement__ = document.createElement('form');
		const screen = new RequestScreen();
		screen.load('/url').then(() => {
			expect(screen.getRequest().method).toBe(RequestScreen.POST);
			expect(screen.getRequest().requestBody).toBeInstanceOf(FormData);
			Liferay.SPA.__capturedFormElement__ = null;
			done();
		});
	});

	it('adds submit input button value into request FormData', (done) => {
		fetch.mockResponse('');

		Liferay.SPA.__capturedFormElement__ = document.createElement('form');
		const submitButton = document.createElement('button');
		submitButton.name = 'submitButton';
		submitButton.type = 'submit';
		submitButton.value = 'Send';
		Liferay.SPA.__capturedFormElement__.appendChild(submitButton);
		Liferay.SPA.__capturedFormButtonElement__ = submitButton;
		const screen = new RequestScreen();
		const spy = jest.spyOn(FormData.prototype, 'append');
		screen.load('/url').then(() => {
			expect(spy).toHaveBeenCalledWith(
				submitButton.name,
				submitButton.value
			);
			Liferay.SPA.__capturedFormElement__ = null;
			Liferay.SPA.__capturedFormButtonElement__ = null;
			done();
		});
	});

	it('navigates over same protocol the page was viewed on', (done) => {
		fetch.mockResponse('');

		const screen = new RequestScreen();
		const wrongProtocol = window.location.origin.replace('http', 'https');
		screen.load(wrongProtocol + '/url').then(() => {
			const url = screen.getRequest().url;
			expect(url.indexOf('http:')).toBe(0);
			done();
		});
	});
});

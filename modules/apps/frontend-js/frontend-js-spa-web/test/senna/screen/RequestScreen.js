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

import globals from '../../../src/main/resources/META-INF/resources/senna/globals/globals';
import RequestScreen from '../../../src/main/resources/META-INF/resources/senna/screen/RequestScreen';

describe('RequestScreen', function () {
	it('is cacheable', () => {
		var screen = new RequestScreen();
		expect(screen.isCacheable()).toBe(true);
	});

	it('sets HTTP method', () => {
		var screen = new RequestScreen();
		expect(screen.getHttpMethod()).toBe(RequestScreen.GET);

		screen.setHttpMethod(RequestScreen.POST);
		expect(screen.getHttpMethod()).toBe(RequestScreen.POST);
	});

	it('sets HTTP headers', () => {
		var screen = new RequestScreen();

		expect(screen.getHttpHeaders()).toHaveProperty('X-PJAX', 'true');
		expect(screen.getHttpHeaders()).toHaveProperty(
			'X-Requested-With',
			'XMLHttpRequest'
		);

		screen.setHttpHeaders({});

		expect(screen.getHttpHeaders()).toEqual({});
	});

	it('sets timeout', () => {
		var screen = new RequestScreen();
		expect(screen.getTimeout()).toBe(30000);
		screen.setTimeout(0);
		expect(screen.getTimeout()).toBe(0);
	});

	it('returns request path if responseURL or X-Request-URL not present on screen beforeUpdateHistoryPath', () => {
		var screen = new RequestScreen();
		jest.spyOn(screen, 'getRequest').mockImplementation(() => {
			return {
				getResponseHeader() {
					return null;
				},
				requestPath: '/path',
			};
		});

		expect(screen.beforeUpdateHistoryPath('/path')).toBe('/path');
	});

	it('returns responseURL if present on screen beforeUpdateHistoryPath', () => {
		var screen = new RequestScreen();
		jest.spyOn(screen, 'getRequest').mockImplementation(() => {
			return {
				requestPath: '/path',
				responseURL: '/redirect',
			};
		});

		expect(screen.beforeUpdateHistoryPath('/path')).toBe('/redirect');
	});

	it('returns X-Request-URL if present and responseURL is not on screen beforeUpdateHistoryPath', () => {
		var screen = new RequestScreen();
		jest.spyOn(screen, 'getRequest').mockImplementation(() => {
			return {
				getResponseHeader: (header) => {
					return {
						'X-Request-URL': '/redirect',
					}[header];
				},
				requestPath: '/path',
			};
		});

		expect(screen.beforeUpdateHistoryPath('/path')).toBe('/redirect');
	});

	it('returns null if form navigate to post-without-redirect-get on screen beforeUpdateHistoryState', () => {
		var screen = new RequestScreen();
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
		var screen = new RequestScreen();
		expect(screen.getRequestPath()).toBeNull();
	});

	it.skip('sends request to an url', (done) => {
		var screen = new RequestScreen();
		screen.load('/url').then(() => {
			assert.strictEqual(
				globals.window.location.origin + '/url',
				screen.getRequest().url
			);
			assert.deepEqual(
				{
					'X-PJAX': 'true',
					'X-Requested-With': 'XMLHttpRequest',
				},
				screen.getRequest().requestHeaders
			);
			done();
		});
		this.requests[0].respond(200);
	});

	it.skip('should load response content from cache', (done) => {
		var screen = new RequestScreen();
		var cache = {};
		screen.addCache(cache);
		screen.load('/url').then((cachedContent) => {
			assert.strictEqual(cache, cachedContent);
			done();
		});
	});

	it.skip('should not load response content from cache for post requests', (done) => {
		var screen = new RequestScreen();
		var cache = {};
		screen.setHttpMethod(RequestScreen.POST);
		screen.load('/url').then(() => {
			screen.load('/url').then((cachedContent) => {
				assert.notStrictEqual(cache, cachedContent);
				done();
			});
			this.requests[1].respond(200);
		});
		this.requests[0].respond(200);
	});

	it.skip('should cancel load request to an url', (done) => {
		var screen = new RequestScreen();
		screen
			.load('/url')
			.then(() => assert.fail())
			.catch(() => {
				assert.ok(this.requests[0].aborted);
				done();
			})
			.cancel();
	});

	it.skip('should fail for timeout request', (done) => {
		var screen = new RequestScreen();
		screen.setTimeout(0);
		screen.load('/url').catch((reason) => {
			assert.ok(reason.timeout);
			clearTimeout(id);
			done();
		});
		var id = setTimeout(() => this.requests[0].respond(200), 100);
	});

	it.skip('should fail for invalid status code response', (done) => {
		new RequestScreen().load('/url').catch((error) => {
			assert.ok(error.invalidStatus);
			done();
		});
		this.requests[0].respond(404);
	});

	it.skip('should return the correct http status code for "page not found"', (done) => {
		new RequestScreen().load('/url').catch((error) => {
			assert.strictEqual(error.statusCode, 404);
			done();
		});
		this.requests[0].respond(404);
	});

	it.skip('should return the correct http status code for "unauthorised"', (done) => {
		new RequestScreen().load('/url').catch((error) => {
			assert.strictEqual(error.statusCode, 401);
			done();
		});
		this.requests[0].respond(401);
	});

	it.skip('should fail for request errors response', (done) => {
		new RequestScreen().load('/url').catch((error) => {
			assert.ok(error.requestError);
			done();
		});
		this.requests[0].error();
	});

	it.skip('should form navigate force post method and request body wrapped in FormData', (done) => {
		globals.capturedFormElement = globals.document.createElement('form');
		var screen = new RequestScreen();
		screen.load('/url').then(() => {
			assert.strictEqual(RequestScreen.POST, screen.getRequest().method);
			assert.ok(screen.getRequest().requestBody instanceof FormData);
			globals.capturedFormElement = null;
			done();
		});
		this.requests[0].respond(200);
	});

	it.skip('should add submit input button value into request FormData', (done) => {
		globals.capturedFormElement = globals.document.createElement('form');
		const submitButton = globals.document.createElement('button');
		submitButton.name = 'submitButton';
		submitButton.type = 'submit';
		submitButton.value = 'Send';
		globals.capturedFormElement.appendChild(submitButton);
		globals.capturedFormButtonElement = submitButton;
		var screen = new RequestScreen();
		var spy = sinon.spy(FormData.prototype, 'append');
		screen.load('/url').then(() => {
			assert.ok(spy.calledWith(submitButton.name, submitButton.value));
			globals.capturedFormElement = null;
			globals.capturedFormButtonElement = null;
			spy.restore();
			done();
		});
		this.requests[0].respond(200);
	});

	it.skip('should navigate over same protocol the page was viewed on', (done) => {
		var screen = new RequestScreen();
		var wrongProtocol = globals.window.location.origin.replace(
			'http',
			'https'
		);
		screen.load(wrongProtocol + '/url').then(() => {
			var url = screen.getRequest().url;
			assert.ok(url.indexOf('http:') === 0);
			done();
		});
		this.requests[0].respond(200);
	});
});

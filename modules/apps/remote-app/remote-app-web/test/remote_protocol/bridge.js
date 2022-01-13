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

import {
	REMOTE_APP_PROTOCOL,
	VERSION,
} from '../../src/main/resources/META-INF/resources/remote_protocol/bridge';

const originalAddEventListener = window.__proto__.addEventListener;

describe('remote-app-web', () => {
	let iframe;
	let init;
	let ms;
	let receiveMessage;
	let reply;

	beforeEach(() => {
		jest.resetModules();

		init = require('../../src/main/resources/META-INF/resources/remote_protocol/bridge')
			.default;

		iframe = document.createElement('iframe');

		document.body.appendChild(iframe);

		// Hack necessary "source" property into MessageEvent
		// objects because jsdom sets it to null:
		// https://github.com/jsdom/jsdom/blob/020539ed3f4/lib/jsdom/living/post-message.js

		jest.spyOn(window, 'addEventListener').mockImplementation(
			(name, callback) => {
				originalAddEventListener.call(window, name, (event) => {
					callback(
						new window.MessageEvent(event.type, {
							data: event.data,
							lastEventId: event.lastEventId,
							origin: event.origin,
							ports: event.ports,
							source: iframe.contentWindow,
						})
					);
				});
			}
		);

		ms = (delay) => new Promise((resolve) => setTimeout(resolve, delay));

		let messagesReceived = 0;

		reply = () => {
			return new Promise((resolve) => {
				(function (initialCount) {
					const id = setInterval(() => {
						if (messagesReceived > initialCount) {
							clearTimeout(id);
							resolve();
						}
					}, 15);
				})(messagesReceived);
			});
		};

		receiveMessage = jest.fn();

		originalAddEventListener.call(
			iframe.contentWindow,
			'message',
			(event) => {
				receiveMessage(event);

				messagesReceived++;
			}
		);

		init();
	});

	describe('"register" command', () => {
		it('acknowledges registration', async () => {
			iframe.contentWindow.parent.postMessage(
				{
					appID: 'some UUID',
					command: 'register',
					protocol: REMOTE_APP_PROTOCOL,
					role: 'client',
					version: VERSION,
				},
				'*'
			);

			await reply();

			expect(receiveMessage).toHaveBeenCalledTimes(1);
			expect(receiveMessage.mock.calls[0][0].data).toEqual({
				appID: 'some UUID',
				kind: 'registered',
				protocol: REMOTE_APP_PROTOCOL,
				role: 'host',
				version: VERSION,
			});
		});

		it('does not register if protocol is incorrect', async () => {
			iframe.contentWindow.parent.postMessage(
				{
					appID: 'some UUID',
					command: 'register',
					protocol: 'bogus protocol',
					role: 'client',
					version: VERSION,
				},
				'*'
			);

			await ms('15');

			expect(receiveMessage).not.toHaveBeenCalled();
		});

		it('does not register if the version is unsupported', async () => {
			iframe.contentWindow.parent.postMessage(
				{
					appID: 'some UUID',
					command: 'register',
					protocol: REMOTE_APP_PROTOCOL,
					role: 'client',
					version: 9000,
				},
				'*'
			);

			await ms('15');

			expect(receiveMessage).not.toHaveBeenCalled();
		});

		it('does not register if the role is incorrect', async () => {
			iframe.contentWindow.parent.postMessage(
				{
					appID: 'some UUID',
					command: 'register',
					protocol: REMOTE_APP_PROTOCOL,
					role: 'junk',
					version: VERSION,
				},
				'*'
			);

			await ms('15');

			expect(receiveMessage).not.toHaveBeenCalled();
		});
	});

	describe('"fetch" command', () => {
		it('doesn\'t throw an error if "body" is undefined', async () => {
			const headers = new Headers();
			headers.append('Content-Type', 'application/json');

			fetch.mockResponse(
				JSON.stringify({
					headers,
					ok: true,
					redirected: false,
					url: '/o/example-url',
				})
			);

			iframe.contentWindow.parent.postMessage(
				{
					appID: 'some UUID',
					command: 'fetch',
					init: {},
					protocol: REMOTE_APP_PROTOCOL,
					resource: '/o/test/foo',
					role: 'client',
					version: VERSION,
				},
				'*'
			);

			await reply();

			expect(receiveMessage).toHaveBeenCalled();

			expect(receiveMessage.mock.calls[0][0].data).toEqual({
				appID: 'some UUID',
				headers: [['content-type', 'text/plain;charset=UTF-8']],
				kind: 'fetch:resolve',
				ok: true,
				protocol: 'com.liferay.remote.app.protocol',
				redirected: false,
				requestID: undefined,
				role: 'host',
				status: 200,
				statusText: 'OK',
				type: undefined,
				url: '',
				version: VERSION,
			});
		});

		it('allows fetching on same domain', async () => {
			const headers = new Headers();
			headers.append('Content-Type', 'application/json');

			fetch.mockResponse(
				JSON.stringify({
					headers,
					ok: true,
					redirected: false,
					url: window.location.origin + '/foo',
				})
			);

			iframe.contentWindow.parent.postMessage(
				{
					appID: 'some UUID',
					command: 'fetch',
					init: {},
					protocol: REMOTE_APP_PROTOCOL,
					resource: window.location.origin + '/foo',
					role: 'client',
					version: VERSION,
				},
				'*'
			);

			await reply();

			expect(receiveMessage).toHaveBeenCalled();

			expect(receiveMessage.mock.calls[0][0].data).toEqual({
				appID: 'some UUID',
				headers: [['content-type', 'text/plain;charset=UTF-8']],
				kind: 'fetch:resolve',
				ok: true,
				protocol: 'com.liferay.remote.app.protocol',
				redirected: false,
				requestID: undefined,
				role: 'host',
				status: 200,
				statusText: 'OK',
				type: undefined,
				url: '',
				version: VERSION,
			});
		});

		it('prevents fetching resources cross domain', async () => {
			iframe.contentWindow.parent.postMessage(
				{
					appID: 'some UUID',
					command: 'fetch',
					init: {},
					protocol: REMOTE_APP_PROTOCOL,
					resource: 'http://evildomain.com',
					role: 'client',
					version: VERSION,
				},
				'*'
			);

			await reply();

			expect(receiveMessage.mock.calls[0][0].data).toEqual({
				appID: 'some UUID',
				kind: 'fetch:reject',
				protocol: 'com.liferay.remote.app.protocol',
				requestID: undefined,
				role: 'host',
				version: 0,
			});
		});
	});
});

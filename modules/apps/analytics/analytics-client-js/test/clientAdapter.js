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

import fetchMock from 'fetch-mock';

import ClientAdapter from '../src/clientAdapter';

const getMockMessageItem = (id = 0, data = {}) => {
	return {
		foo: 'bar',
		id: `${id}`,
		...data,
	};
};

const ADAPTER_CONFIG = {
	endpointUrl: 'http://ac-server',
	projectId: '1234',
};

describe('Client', () => {
	let client;

	afterEach(() => {
		fetchMock.restore();

		jest.restoreAllMocks();
	});

	beforeEach(() => {
		client = new ClientAdapter(ADAPTER_CONFIG);
	});

	it('default parameters', () => {
		expect(client._getRequestParameters()).toMatchInlineSnapshot(`
		Object {
		  "cache": "default",
		  "credentials": "same-origin",
		  "headers": Object {
		    "Content-Type": "application/json",
		    "OSB-Asah-Project-ID": "1234",
		  },
		  "method": "POST",
		  "mode": "cors",
		}
	`);
	});

	it('send', () => {
		const sentItems = [];

		fetchMock.mock(/ac-server/i, (url, {body}) => {
			sentItems.push(JSON.parse(body));

			return Promise.resolve(200);
		});

		const payload = getMockMessageItem(1);

		client.send(payload);

		expect(sentItems[0]).toEqual(payload);
	});
});

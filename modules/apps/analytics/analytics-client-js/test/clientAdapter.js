/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

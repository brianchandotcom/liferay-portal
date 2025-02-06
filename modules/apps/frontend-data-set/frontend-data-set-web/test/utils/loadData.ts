/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetch from 'jest-fetch-mock';

import {loadData} from '../../src/main/resources/META-INF/resources/utils/loadData';

const responseData = {
	account: 25,
	order: 0,
	people: 44,
	product: 34,
};

beforeEach(() => {
	fetch.resetMocks();
	fetch.mockResponseOnce(JSON.stringify(responseData));
});

describe('loadData util', () => {
	it('is defined', () => {
		expect(loadData).toBeDefined();
	});

	it('retrieves data for a Frontend Data Set using a GET request', async () => {
		const requestResult = await loadData(
			'/o/products',
			'/sample-portlet-page-url',
			[],
			'foo=bar',
			20,
			1,
			[],
			''
		);

		expect(fetch).toHaveBeenCalledTimes(1);
		expect(fetch.mock.calls[0][1]?.method).toEqual('GET');
		expect(requestResult).toEqual({
			data: responseData,
			ok: true,
			status: 200,
		});
	});

	it('requests data for a Frontend Data Set with a GET request', async () => {
		await loadData('/o/products', '/', [''], 'foo=bar', 20, 1, [], '');

		const requestUrl = fetch.mock.calls[0][0];

		expect(requestUrl).toEqual(
			`${Liferay.ThemeDisplay.getPortalURL()}//o/products?currentURL=%2F&filter=%28%29&page=1&pageSize=20&search=foo%3Dbar`
		);
	});

	it('handles "LANG" property when it comes as a sort parameter', async () => {
		await loadData(
			'/o/products',
			'/',
			[''],
			'foo=bar',
			20,
			1,
			[
				{
					active: true,
					direction: 'asc',
					key: 'name,LANG',
				},
			],
			''
		);

		const requestUrl = fetch.mock.calls[0][0];

		expect(requestUrl).toEqual(
			`${Liferay.ThemeDisplay.getPortalURL()}//o/products?currentURL=%2F&filter=%28%29&page=1&pageSize=20&search=foo%3Dbar&sort=name%3Aasc`
		);
	});
});

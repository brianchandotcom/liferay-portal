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

import {cleanup} from '@testing-library/react';

import {
	addItem,
	confirmDelete,
	deleteItem,
	getItem,
	request,
	updateItem,
} from '../../../src/main/resources/META-INF/resources/js/utils/client.es';
import * as toast from '../../../src/main/resources/META-INF/resources/js/utils/toast.es';

describe('client', () => {
	beforeAll(() => {
		jest.spyOn(toast, 'successToast').mockImplementation(() => {});
		jest.spyOn(toast, 'errorToast').mockImplementation(() => {});
	});

	afterEach(() => {
		cleanup();
	});

	afterAll(() => {
		jest.clearAllMocks();
	});

	it('addItem', () => {
		const item = {data: 'hello'};
		fetch.mockResponseOnce(JSON.stringify(item));

		addItem('/test', item).then((res) => {
			expect(res.data).toEqual('hello');
		});

		expect(fetch.mock.calls.length).toEqual(1);

		const call = fetch.mock.calls[0];

		expect(call[0]).toMatch(
			'http://localhost/test?p_auth=default-mocked-auth-token&t='
		);

		const {body, credentials, headers, method} = call[1];

		expect(body).toEqual(JSON.stringify(item));
		expect(credentials).toEqual('include');
		expect(method).toEqual('POST');
		expect(headers.get('Accept')).toEqual('application/json');
		expect(headers.get('Accept-Language')).toEqual(
			Liferay.ThemeDisplay.getBCP47LanguageId()
		);
		expect(headers.get('Content-Type')).toEqual('application/json');
	});

	it('confirmDelete', () => {
		const item = {id: 123};
		window.confirm = jest.fn(() => false);
		confirmDelete('/test')(item).then((confirmed) =>
			expect(confirmed).toBeFalsy()
		);

		fetch.mockResponseOnce('');
		window.confirm = jest.fn(() => true);

		confirmDelete('/test')(item).then((confirmed) =>
			expect(confirmed).toBeTruthy()
		);

		fetch.mockReject(new Error('error'));
		confirmDelete('/test')(item).catch((error) =>
			expect(error.message).toEqual('error')
		);
	});

	it('deleteItem', () => {
		fetch.mockResponseOnce('');

		deleteItem('/test').then((res) => {
			expect(res).toEqual({});
		});

		expect(fetch.mock.calls.length).toEqual(1);

		const call = fetch.mock.calls[0];

		expect(call[0]).toMatch(
			'http://localhost/test?p_auth=default-mocked-auth-token&t='
		);

		const {credentials, headers, method} = call[1];

		expect(credentials).toEqual('include');
		expect(method).toEqual('DELETE');
		expect(headers.get('Accept')).toEqual('application/json');
		expect(headers.get('Accept-Language')).toEqual(
			Liferay.ThemeDisplay.getBCP47LanguageId()
		);
		expect(headers.get('Content-Type')).toEqual('application/json');
	});

	it('getItem', () => {
		const item = {data: 'hello'};
		fetch.mockResponseOnce(JSON.stringify(item));

		getItem('/test').then((res) => {
			expect(res.data).toEqual('hello');
		});

		expect(fetch.mock.calls.length).toEqual(1);

		const call = fetch.mock.calls[0];
		expect(call[0]).toMatch(
			'http://localhost/test?p_auth=default-mocked-auth-token&t='
		);

		const {credentials, headers, method} = call[1];

		expect(credentials).toEqual('include');
		expect(method).toEqual('GET');
		expect(headers.get('Accept')).toEqual('application/json');
		expect(headers.get('Accept-Language')).toEqual(
			Liferay.ThemeDisplay.getBCP47LanguageId()
		);
		expect(headers.get('Content-Type')).toEqual('application/json');
	});

	it('invalid response body', () => {
		fetch.mockResponseOnce('not a valid json object');

		addItem('/', {}).catch((error) => {
			expect(error.message).toEqual(
				'Unexpected token o in JSON at position 1'
			);
		});
	});

	it('reject', () => {
		fetch.mockReject(new Error('error'));

		addItem('/', {}).catch((error) => {
			expect(error.message).toEqual('error');
		});
	});

	it('request', () => {
		const item = {data: 'hello'};
		fetch.mockResponseOnce(JSON.stringify(item));

		request({endpoint: '/test'}).then((res) => {
			expect(res.data).toEqual('hello');
		});

		expect(fetch.mock.calls.length).toEqual(1);

		const call = fetch.mock.calls[0];

		expect(call[0]).toMatch(
			'http://localhost/test?p_auth=default-mocked-auth-token&t='
		);

		const {credentials, headers, method} = call[1];

		expect(credentials).toEqual('include');
		expect(method).toEqual('GET');
		expect(headers.get('Accept')).toEqual('application/json');
		expect(headers.get('Accept-Language')).toEqual(
			Liferay.ThemeDisplay.getBCP47LanguageId()
		);
		expect(headers.get('Content-Type')).toEqual('application/json');
	});

	it('status not ok', () => {
		const res = {message: 'server error'};

		fetch.mockResponseOnce(JSON.stringify(res), {status: 404});

		addItem('/', {}).catch((error) => {
			expect(error).toEqual(res);
		});
	});

	it('updateItem', () => {
		const item = {data: 'hello'};
		fetch.mockResponseOnce(JSON.stringify(item));

		updateItem({endpoint: '/test', item}).then((res) => {
			expect(res.data).toEqual('hello');
		});

		expect(fetch.mock.calls.length).toEqual(1);

		const call = fetch.mock.calls[0];

		expect(call[0]).toMatch(
			'http://localhost/test?p_auth=default-mocked-auth-token&t='
		);

		const {credentials, headers, method} = call[1];

		expect(credentials).toEqual('include');
		expect(method).toEqual('PUT');
		expect(headers.get('Accept')).toEqual('application/json');
		expect(headers.get('Accept-Language')).toEqual(
			Liferay.ThemeDisplay.getBCP47LanguageId()
		);
		expect(headers.get('Content-Type')).toEqual('application/json');
	});
});

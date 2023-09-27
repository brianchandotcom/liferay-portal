/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import 'jest-fetch-mock';
import {fetch} from 'frontend-js-web';

import request from '../request';

jest.mock('frontend-js-web', () => ({
	fetch: jest.fn(),
}));

const original = window.location;

beforeAll(() => {
	Object.defineProperty(window, 'location', {
		configurable: true,
		value: {reload: jest.fn()},
	});
});

afterAll(() => {
	Object.defineProperty(window, 'location', {
		configurable: true,
		value: original,
	});
});

describe('request', () => {
	beforeEach(() => {
		jest.clearAllMocks();
	});

	it('return OK when response status is 204', async () => {
		(fetch as jest.MockedFunction<typeof fetch>).mockResolvedValue(
			new Response(null, {
				headers: {'Content-Type': 'application/json'},
				status: 204,
				statusText: 'NoContent',
			})
		);
		const result = await request('/test', {});
		expect(result).toEqual({ok: true});
	});

	it('return error when response status is 400', async () => {
		(fetch as jest.MockedFunction<typeof fetch>).mockResolvedValue(
			new Response(null, {
				headers: {'Content-Type': 'application/json'},
				status: 400,
				statusText: 'BadRequest',
			})
		);
		const result = await request('/test', {});
		expect(result).toEqual({error: new Error('Request Error')});
	});

	it('return error when response status is 401', async () => {
		(fetch as jest.MockedFunction<typeof fetch>).mockResolvedValue(
			new Response(null, {
				headers: {'Content-Type': 'application/json'},
				status: 401,
				statusText: 'Unauthorized',
			})
		);
		const result = await request('/test', {});
		expect(result).toEqual({error: new Error('Unauthorized Access')});
	});

	it('return error when response status is 403', async () => {
		(fetch as jest.MockedFunction<typeof fetch>).mockResolvedValue(
			new Response(null, {
				headers: {'Content-Type': 'application/json'},
				status: 403,
				statusText: 'Forbidden',
			})
		);
		const result = await request('/test', {});
		expect(result).toEqual(
			new Response(null, {
				headers: {'Content-Type': 'application/json'},
				status: 403,
				statusText: 'Forbidden',
			})
		);
		expect(jest.isMockFunction(window.location.reload)).toBe(true);
	});

	it('return error when response status is 500', async () => {
		(fetch as jest.MockedFunction<typeof fetch>).mockResolvedValue(
			new Response(null, {
				headers: {'Content-Type': 'application/json'},
				status: 500,
				statusText: 'InternalServerError',
			})
		);
		const result = await request('/test', {});
		expect(result).toEqual({error: new Error('Request Error')});
	});

	it('return error when response status is 404', async () => {
		(fetch as jest.MockedFunction<typeof fetch>).mockResolvedValue(
			new Response(null, {
				headers: {'Content-Type': 'application/json'},
				status: 404,
				statusText: 'NotFound',
			})
		);
		const result = await request('/test', {});
		expect(result).toEqual({error: new Error('Request Error')});
	});

	it('return error when response status is 300', async () => {
		(fetch as jest.MockedFunction<typeof fetch>).mockResolvedValue(
			new Response(null, {
				headers: {'Content-Type': 'application/json'},
				status: 300,
				statusText: 'MultipleChoices',
			})
		);
		const result = await request('/test', {});
		expect(result).toEqual({error: new Error('Request Error')});
	});
});

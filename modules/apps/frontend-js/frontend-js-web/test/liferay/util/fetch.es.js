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

import fetchWrapper from '../../../src/main/resources/META-INF/resources/liferay/util/fetch.es';

describe('Liferay.Util.fetch', () => {
	const sampleUrl = 'http://sampleurl.com';

	beforeEach(() => {
		fetch.mockResponse('');
	});

	it('applies default settings if none are given', () => {
		fetchWrapper(sampleUrl);

		const init = {
			credentials: 'include',
			headers: new Headers({
				'x-csrf-token': 'default-mocked-auth-token'
			})
		};

		expect(fetch).toHaveBeenCalledWith(sampleUrl, init);
	});

	it('overrides default settings with given settings', () => {
		const init = {
			credentials: 'omit',
			headers: {
				'x-csrf-token': 'efgh'
			}
		};

		fetchWrapper(sampleUrl, init);

		const mergedInit = {
			credentials: 'omit',
			headers: new Headers({
				'x-csrf-token': 'efgh'
			})
		};

		expect(fetch).toHaveBeenCalledWith(sampleUrl, mergedInit);
	});

	it('merges default settings with given different settings', () => {
		const init = {
			headers: {
				'content-type': 'application/json'
			},
			method: 'GET'
		};

		fetchWrapper(sampleUrl, init);

		const mergedInit = {
			credentials: 'include',
			headers: new Headers({
				'content-type': 'application/json',
				'x-csrf-token': 'default-mocked-auth-token'
			}),
			method: 'GET'
		};

		expect(fetch).toHaveBeenCalledWith(sampleUrl, mergedInit);
	});

	it('sets given headers to lower-case before merging with defaults', () => {
		const init = {
			headers: {
				'Content-Type': 'application/json',
				'X-CSRF-token': 'efgh'
			}
		};

		fetchWrapper(sampleUrl, init);

		const mergedInit = {
			credentials: 'include',
			headers: new Headers({
				'content-type': 'application/json',
				'x-csrf-token': 'efgh'
			})
		};

		expect(fetch).toHaveBeenCalledWith(sampleUrl, mergedInit);
	});

	it('merges given multiple headers, setting name to lower-case', () => {
		const init = {
			headers: {
				'Content-Type': 'application/json',
				'Content-type': 'multipart/form-data'
			}
		};

		fetchWrapper(sampleUrl, init);

		const mergedInit = {
			credentials: 'include',
			headers: new Headers({
				'content-type': 'application/json, multipart/form-data',
				'x-csrf-token': 'default-mocked-auth-token'
			})
		};

		expect(fetch).toHaveBeenCalledWith(sampleUrl, mergedInit);
	});

	it('allows given headers to be an array of arrays', () => {
		const init = {
			headers: [
				['content-type', 'application/json'],
				['x-csrf-token', 'efgh']
			]
		};

		fetchWrapper(sampleUrl, init);

		const mergedInit = {
			credentials: 'include',
			headers: new Headers({
				'content-type': 'application/json',
				'x-csrf-token': 'efgh'
			})
		};

		expect(fetch).toHaveBeenCalledWith(sampleUrl, mergedInit);
	});

	it('allows given headers to be a Headers object', () => {
		const init = {
			headers: new Headers({
				'content-type': 'application/json',
				'x-csrf-token': 'efgh'
			})
		};

		fetchWrapper(sampleUrl, init);

		const mergedInit = {
			credentials: 'include',
			headers: new Headers({
				'content-type': 'application/json',
				'x-csrf-token': 'efgh'
			})
		};

		expect(fetch).toHaveBeenCalledWith(sampleUrl, mergedInit);
	});
});

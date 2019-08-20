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

import {
	getSessionValue,
	setSessionValue
} from '../../../src/main/resources/META-INF/resources/liferay/util/session.es';

describe('Session API', () => {
	beforeEach(() => {
		fetch.mockResponse('');
	});

	describe('getSessionValue', () => {
		it('GETs the session_click endpoint', () => {
			getSessionValue('foo');

			expect(fetch).toHaveBeenCalledTimes(1);

			expect(fetch).toHaveBeenCalledWith(
				'http://localhost:8080/c/portal/session_click',
				expect.anything()
			);
		});

		it('deserializes session serialized objects', () => {
			fetch.mockResponse('serialize://{"key1":"value1","key2":"value2"}');

			getSessionValue('key').then(value => {
				expect(value).toEqual({
					key1: 'value1',
					key2: 'value2'
				});
			});
		});
	});

	describe('setSessionValue', () => {
		it('POSTs a simple key/value to the session_click endpoint for basic values', () => {
			setSessionValue('key', 'value');

			expect(fetch).toHaveBeenCalledTimes(1);

			expect(fetch.mock.calls[0][0]).toBe(
				'http://localhost:8080/c/portal/session_click'
			);

			expect(fetch.mock.calls[0][1].body.get('key')).toBe('value');
		});

		it('POSTs a key/serializedValue to the session_click endpoint for object values', () => {
			setSessionValue('key', {
				key1: 'value1',
				key2: 'value2'
			});

			expect(fetch).toHaveBeenCalledTimes(1);

			expect(fetch.mock.calls[0][0]).toBe(
				'http://localhost:8080/c/portal/session_click'
			);

			expect(fetch.mock.calls[0][1].body.get('key')).toBe(
				'serialize://{"key1":"value1","key2":"value2"}'
			);
		});
	});
});

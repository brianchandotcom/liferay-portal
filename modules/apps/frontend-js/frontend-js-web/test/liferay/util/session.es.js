/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getSessionValue,
	setSessionValue,
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

			getSessionValue('key').then((value) => {
				expect(value).toEqual({
					key1: 'value1',
					key2: 'value2',
				});
			});
		});

		it('propagates `useHttpSession` to server when `true`', () => {
			getSessionValue('key', {useHttpSession: true});

			expect(fetch).toHaveBeenCalledTimes(1);

			expect(fetch.mock.calls[0][1].body.get('useHttpSession')).toBe(
				'true'
			);
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
				key2: 'value2',
			});

			expect(fetch).toHaveBeenCalledTimes(1);

			expect(fetch.mock.calls[0][0]).toBe(
				'http://localhost:8080/c/portal/session_click'
			);

			expect(fetch.mock.calls[0][1].body.get('key')).toBe(
				'serialize://{"key1":"value1","key2":"value2"}'
			);
		});

		it('propagates `useHttpSession` to server when `true`', () => {
			setSessionValue('key', 'value', {useHttpSession: true});

			expect(fetch).toHaveBeenCalledTimes(1);

			expect(fetch.mock.calls[0][1].body.get('useHttpSession')).toBe(
				'true'
			);
		});
	});
});

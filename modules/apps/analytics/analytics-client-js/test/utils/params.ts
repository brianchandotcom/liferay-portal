/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getSearchParams} from '../../src/utils/params';

describe('getSearchParams', () => {
	beforeEach(() => {
		delete (window as any).location;

		(window as any).location = {search: ''};
	});

	it('returns an instance of URLSearchParams when available', () => {
		(window as any).URLSearchParams = jest
			.fn()
			.mockImplementation(() => ({get: jest.fn(() => 'value')}));
		(window as any).location.search = '?key=value';

		const params = getSearchParams();
		expect(params.get('key')).toBe('value');
	});

	it('returns an object when URLSearchParams is not available', () => {
		delete (window as any).URLSearchParams;

		(window as any).location.search = '?key=value';

		const params = getSearchParams();
		expect(params.get('key')).toBe('value');
	});

	it('handles multiple parameters in the query string', () => {
		delete (window as any).URLSearchParams;

		(window as any).location.search = '?key1=value1&key2=value2';

		const params = getSearchParams();
		expect(params.get('key1')).toBe('value1');
		expect(params.get('key2')).toBe('value2');
	});

	it('handles parameters without values', () => {
		delete (window as any).URLSearchParams;

		(window as any).location.search = '?key1=&key2';

		const params = getSearchParams();
		expect(params.get('key1')).toBe('');
		expect(params.get('key2')).toBe('');
	});

	it('returns an empty object when there are no parameters in the URL', () => {
		delete (window as any).URLSearchParams;

		(window as any).location.search = '';

		const params = getSearchParams();
		expect(params.get('key')).toBeUndefined();
	});
});

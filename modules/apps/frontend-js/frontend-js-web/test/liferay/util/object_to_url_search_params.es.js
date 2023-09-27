/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import objectToURLSearchParams from '../../../src/main/resources/META-INF/resources/liferay/util/object_to_url_search_params.es';

describe('Liferay.Util.objectToURLSearchParams', () => {
	it('throws error if obj parameter is not an object', () => {
		expect(() => objectToURLSearchParams('abc')).toThrow(
			'must be an object'
		);
	});

	it('converts given object into URLSearchParams', () => {
		const object = {
			key1: 'value1',
			key2: 123,
		};

		const urlSearchParams = objectToURLSearchParams(object);

		expect(urlSearchParams.constructor.name).toEqual('URLSearchParams');

		expect(urlSearchParams.get('key1')).toEqual('value1');
		expect(urlSearchParams.get('key2')).toEqual('123');
	});

	it('converts given object parameter with array value into multiple request parameters with the same key', () => {
		const object = {
			key: ['abc', 'def'],
		};

		const urlSearchParams = objectToURLSearchParams(object);

		expect(urlSearchParams.getAll('key')).toEqual(['abc', 'def']);
	});
});

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

import objectToURLSearchParams from '../../../src/main/resources/META-INF/resources/liferay/util/object_to_url_search_params.es';

describe('Liferay.Util.objectToURLSearchParams', () => {
	it('throws error if obj parameter is not an object', () => {
		expect(() => objectToURLSearchParams('abc')).toThrow(
			'must be an object'
		);
	});

	it('converts given object into URLSearchParams', () => {
		const obj = {
			key1: 'value1',
			key2: 123
		};

		const urlSearchParams = objectToURLSearchParams(obj);

		expect(urlSearchParams.constructor.name).toEqual('URLSearchParams');

		expect(urlSearchParams.get('key1')).toEqual('value1');
		expect(urlSearchParams.get('key2')).toEqual('123');
	});

	it('converts given object parameter with array value into multiple request parameters with the same key', () => {
		const obj = {
			key: ['abc', 'def']
		};

		const urlSearchParams = objectToURLSearchParams(obj);

		expect(urlSearchParams.getAll('key')).toEqual(['abc', 'def']);
	});
});

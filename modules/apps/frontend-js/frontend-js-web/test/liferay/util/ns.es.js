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

import ns from '../../../src/main/resources/META-INF/resources/liferay/util/ns.es';

describe('Liferay.Util.ns', () => {
	it('returns an object', () => {
		const namespace = '_ns_';

		const payload = {
			arr1: [1, 2, 3],
			arr2: ['foo', 'bar', 'baz'],
			int1: 1,
			int2: 2,
			obj1: {
				arr3: [1, 2, 3, 'foo', 'bar', 'baz'],
				int3: 3,
				int4: 4,
				string3: 'baz',
			},
			string1: 'foo',
			string2: 'bar',
		};

		const result = ns(namespace, payload);

		expect(result).toBeDefined();
		expect(typeof result).toBe('object');
		expect(result).toMatchSnapshot();
	});
});

'use strict';

import ns from '../../../src/main/resources/META-INF/resources/liferay/util/ns.es';

describe('Liferay.Util.ns', () => {
	it('should return an object', () => {
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
				string3: 'baz'
			},
			string1: 'foo',
			string2: 'bar'
		};

		const result = ns(namespace, payload);

		expect(result).toBeDefined();
		expect(typeof result).toBe('object');
		expect(result).toMatchSnapshot();
	});
});

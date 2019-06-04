'use strict';

import getRegions from '../../../../src/main/resources/META-INF/resources/liferay/util/address/get_regions.es';

describe('Liferay.Address.getRegions', () => {
	it('should throw an error if the callback parameter is not a function', () => {
		expect(() => getRegions('')).toThrow('must be a function');
	});

	it('should throw an error if the selectKey parameter is not a string', () => {
		expect(() => getRegions(() => {}, {})).toThrow('must be a string');
	});
});

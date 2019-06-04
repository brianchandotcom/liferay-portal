'use strict';

import getCountries from '../../../../src/main/resources/META-INF/resources/liferay/util/address/get_countries.es';

describe('Liferay.Address.getCountries', () => {
	it('should throw an error if the callback parameter is not a function', () => {
		expect(() => getCountries('')).toThrow('must be a function');
	});
});

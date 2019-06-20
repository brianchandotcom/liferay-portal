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

import getCountries from '../../../../src/main/resources/META-INF/resources/liferay/util/address/get_countries.es';

describe('Liferay.Address.getCountries', () => {
	it('should throw an error if the callback parameter is not a function', () => {
		expect(() => getCountries('')).toThrow('must be a function');
	});
});

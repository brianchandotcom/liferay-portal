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

import getRegions from '../../../../src/main/resources/META-INF/resources/liferay/util/address/get_regions.es';

describe('Liferay.Address.getRegions', () => {
	it('should throw an error if the callback parameter is not a function', () => {
		expect(() => getRegions('')).toThrow('must be a function');
	});

	it('should throw an error if the selectKey parameter is not a string', () => {
		expect(() => getRegions(() => {}, {})).toThrow('must be a string');
	});
});

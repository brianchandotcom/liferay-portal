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

import formatStorage from '../../../src/main/resources/META-INF/resources/liferay/util/format_storage.es';

describe('Liferay.Util.formatStorage', () => {
	it('throws error if size parameter is not a number', () => {
		expect(() => formatStorage({})).toThrow('must be a number');
	});

	it('formats size under 1048575 bytes to kilobytes, with the default KB suffix', () => {
		expect(formatStorage(10400)).toEqual('10KB');
	});

	it('formats size 0 bytes to kilobytes, with the default KB suffix', () => {
		expect(formatStorage(0)).toEqual('0KB');
	});

	it('formats size over 1048575 bytes to megabytes, with the default MB suffix', () => {
		expect(formatStorage(1048576)).toEqual('1.0MB');
	});

	it('formats size over 1048575 bytes to megabytes with custom space, decimal separator, and suffix type parameters', () => {
		expect(
			formatStorage(1048576, {
				addSpaceBeforeSuffix: true,
				decimalSeparator: ',',
				suffixMB: 'megabytes'
			})
		).toEqual('1,0 megabytes');
	});
});

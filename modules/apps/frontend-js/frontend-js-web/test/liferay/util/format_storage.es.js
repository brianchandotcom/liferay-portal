/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
				suffixMB: 'megabytes',
			})
		).toEqual('1,0 megabytes');
	});
});

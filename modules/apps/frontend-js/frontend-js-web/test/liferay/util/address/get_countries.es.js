/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCountries from '../../../../src/main/resources/META-INF/resources/liferay/util/address/get_countries.es';

describe('Liferay.Address.getCountries', () => {
	it('throws an error if the callback parameter is not a function', () => {
		expect(() => getCountries('')).toThrow('must be a function');
	});
});

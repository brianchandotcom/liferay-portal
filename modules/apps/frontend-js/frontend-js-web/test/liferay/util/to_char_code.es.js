/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import toCharCode from '../../../src/main/resources/META-INF/resources/liferay/util/to_char_code.es';

describe('Liferay.Util.toCharCode', () => {
	it('returns string', () => {
		const result = toCharCode('liferay');

		expect(typeof result).toBe('string');
		expect(result).toMatchSnapshot();
	});
});

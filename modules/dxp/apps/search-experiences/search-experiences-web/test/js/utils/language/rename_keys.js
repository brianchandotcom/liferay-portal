/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import renameKeys from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/language/rename_keys';

describe('renameKeys', () => {
	it('replaces the string for locale', () => {
		expect(
			renameKeys({'en-US': 'Hello', 'zh-CN': 'Ni Hao'}, (str) =>
				str.replaceAll('-', '_')
			)
		).toEqual({en_US: 'Hello', zh_CN: 'Ni Hao'});
	});
});

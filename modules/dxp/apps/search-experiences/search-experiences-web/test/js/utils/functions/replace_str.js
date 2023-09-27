/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import replaceStr from '../../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/functions/replace_str';

describe('replaceStr', () => {
	it('replaces the string for locale', () => {
		expect(
			replaceStr(
				'title_${configuration.language}',
				'${configuration.language}',
				'en_US'
			)
		).toEqual('title_en_US');
	});
});

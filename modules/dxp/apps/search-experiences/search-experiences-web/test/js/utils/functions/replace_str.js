/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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

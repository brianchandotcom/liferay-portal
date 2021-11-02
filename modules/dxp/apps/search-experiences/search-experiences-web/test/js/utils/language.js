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

import {renameKeys} from '../../../src/main/resources/META-INF/resources/sxp_blueprint_admin/js/utils/language';

describe('utils', () => {
	describe('renameKeys', () => {
		it('replaces the string for locale', () => {
			expect(
				renameKeys({'en-US': 'Hello', 'zh-CN': 'Ni Hao'}, (str) =>
					str.replace('-', '_')
				)
			).toEqual({en_US: 'Hello', zh_CN: 'Ni Hao'});
		});
	});
});

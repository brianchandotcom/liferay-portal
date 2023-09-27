/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isModifyingKey} from '../../../src/main/resources/META-INF/resources/admin/js/util/dom';

describe('DOM Utilities', () => {
	describe('isModifyingKey', () => {
		it('identity modifying keys', () => {
			expect(isModifyingKey(65)).toEqual(true);
			expect(isModifyingKey(18)).toEqual(false);
		});
	});
});

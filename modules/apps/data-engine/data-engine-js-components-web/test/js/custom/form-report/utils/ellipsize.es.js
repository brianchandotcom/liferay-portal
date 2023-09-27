/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ellipsize from '../../../../../src/main/resources/META-INF/resources/js/custom/form-report/utils/ellipsize';

describe('ellipsize', () => {
	it('add an ellipsis in the end of the text', () => {
		const ellipsized = ellipsize('Very long text', 5);

		expect(ellipsized).toBe('Very ...');
	});
});

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {checkAccessibility} from '../../utils/checkAccessibility';

const test = mergeTests(loginTest());

test('checks the accessibility of the General page configuration', async ({
	page,
}) => {
	await page.goto('/');

	await page.getByLabel('Configure Page').click();

	await checkAccessibility({
		page,
		selectors: ['.input-container[aria-label="General"]'],
	});
});

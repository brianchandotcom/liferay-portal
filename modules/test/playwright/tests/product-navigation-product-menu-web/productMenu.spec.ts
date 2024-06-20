/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {productMenuPageTest} from '../../fixtures/productMenuPageTest';

const test = mergeTests(loginTest(), productMenuPageTest);

test('checks where the focus goes when the Product Menu is open', async ({
	page,
	productMenuPage,
}) => {
	await page.goto('/');

	if (await productMenuPage.contentAndDataButton.isVisible()) {
		productMenuPage.closeProductMenuButton.press('Enter');
	}

	productMenuPage.openProductMenuButton.press('Enter');

	await expect(page.getByLabel('Product Menu', {exact: true})).toBeFocused();
});

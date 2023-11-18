/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {expect, test} = require('@playwright/test');

test('has title', async ({page}) => {
	await page.goto(process.env.PORTAL_URL);

	await expect(page).toHaveTitle('Home - Liferay DXP');
	await expect(page.locator('#main-content img')).toBeVisible();
});

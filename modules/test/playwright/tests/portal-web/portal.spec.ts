/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, test} from '@playwright/test';

import {liferayConfig} from '../../liferay.config';

test('title is Home - Liferay DXP', async ({page}) => {
	await page.goto(liferayConfig.environment.baseUrl);

	await expect(page).toHaveTitle('Home - Liferay DXP');
});

test('has homepage image', async ({page}) => {
	await page.goto(liferayConfig.environment.baseUrl);

	await expect(page.locator('#main-content img')).toBeVisible();
});

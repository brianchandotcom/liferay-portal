/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, test} from '@playwright/test';

import {liferayConfig} from '../../../liferay.config';
import {userData} from '../../../utils/performLogin';

test('LPD-38816 Checking the user has to change the password if the password policy is enabled', async ({
	page,
}) => {
	await page.goto(liferayConfig.environment.baseUrl);

	const screenName = 'test';

	await page.getByRole('button', {name: 'Sign In'}).last().click();

	await page.getByLabel('Email Address').fill(screenName + '@liferay.com');
	await page.getByLabel('Password').fill(userData[screenName].password);

	await page.getByRole('button', {name: 'Sign In'}).last().click();

	await expect(
		page.getByRole('heading', {name: 'Change Password'})
	).toBeVisible({
		timeout: 10 * 1000,
	});
});

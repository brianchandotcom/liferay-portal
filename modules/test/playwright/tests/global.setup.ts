/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, test as setup} from '@playwright/test';

import {liferayConfig} from '../liferay.config';

setup('do login', async ({page}) => {
	await page.goto('/');

	await page.getByRole('button', {name: 'Sign In'}).click();

	await page.getByLabel('Email Address').fill(liferayConfig.user.login);
	await page.getByLabel('Password').fill(liferayConfig.user.password);
	await page.getByLabel('Remember Me').check();

	await page
		.getByLabel('Sign In- Loading')
		.getByRole('button', {name: 'Sign In'})
		.click();

	await expect(page.getByLabel('Open Applications MenuCtrl+')).toBeVisible({
		timeout: 100 * 1000,
	});

	await page.context().storageState({path: 'tmp/.auth/user.json'});
});

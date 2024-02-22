/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Cookie, Page, expect} from '@playwright/test';

export type LoginScreenName =
	| 'default-company-admin'
	| 'test'
	| 'test-organization-owner'
	| 'unprivileged';

async function performLogin(
	page: Page,
	screenName: LoginScreenName
): Promise<Cookie[]> {
	await page.goto('/');

	await page.getByRole('button', {name: 'Sign In'}).click();

	await page.getByLabel('Email Address').fill(`${screenName}@liferay.com`);
	await page.getByLabel('Password').fill('test');
	await page.getByLabel('Remember Me').check();

	await page
		.getByLabel('Sign In- Loading')
		.getByRole('button', {name: 'Sign In'})
		.click();

	await expect(
		page.getByLabel(`${screenName} ${screenName} User Profile`)
	).toBeVisible({
		timeout: 30 * 1000,
	});

	return await page.context().cookies();
}

export default performLogin;

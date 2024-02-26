/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Cookie, Page, expect} from '@playwright/test';

export type LoginScreenName =
	| 'demo.company.admin'
	| 'demo.organization.owner'
	| 'demo.unprivileged'
	| 'test';

const userData = {
	'demo.company.admin': {
		name: 'Demo',
		surname: 'Company Admin',
		password: 'demo',
	},
	'demo.organization.owner': {
		name: 'Demo',
		surname: 'Organization Owner',
		password: 'demo',
	},
	'demo.unprivileged': {
		name: 'Demo',
		surname: 'Unprivileged',
		password: 'demo',
	},
	'test': {
		name: 'Test',
		surname: 'Test',
		password: 'test',
	},
};

async function performLogin(
	page: Page,
	screenName: LoginScreenName
): Promise<Cookie[]> {
	const {name, surname, password} = userData[screenName];

	await page.goto('/');

	await page.getByRole('button', {name: 'Sign In'}).click();

	await page.getByLabel('Email Address').fill(`${screenName}@liferay.com`);
	await page.getByLabel('Password').fill(password);
	await page.getByLabel('Remember Me').check();

	await page
		.getByLabel('Sign In- Loading')
		.getByRole('button', {name: 'Sign In'})
		.click();

	await expect(
		page.getByLabel(`${name} ${surname} User Profile`)
	).toBeVisible({
		timeout: 30 * 1000,
	});

	return await page.context().cookies();
}

export default performLogin;

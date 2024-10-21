/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Cookie, Page, expect} from '@playwright/test';

import {liferayConfig} from '../liferay.config';

export type LoginScreenName =
	| 'demo.company.admin'
	| 'demo.organization.owner'
	| 'demo.unprivileged'
	| 'test';

export const userData = {
	'demo.company.admin': {
		name: 'Demo',
		password: 'demo',
		surname: 'Company Admin',
	},
	'demo.organization.owner': {
		name: 'Demo',
		password: 'demo',
		surname: 'Organization Owner',
	},
	'demo.unprivileged': {
		name: 'Demo',
		password: 'demo',
		surname: 'Unprivileged',
	},
	'test': {
		name: 'Test',
		password: liferayConfig.environment.password,
		surname: 'Test',
	},
};

async function performLogin(
	page: Page,
	screenName: LoginScreenName | string,
	baseUrl = '/',
	domain = '@liferay.com',
	rememberMe = true
): Promise<Cookie[]> {
	const {name, password, surname} = userData[screenName];

	await page.goto(baseUrl);

	const signInButton = page.getByRole('button', {name: 'Sign In'});

	const searchInput = page
		.locator('.user-personal-bar')
		.getByPlaceholder('Search');

	await expect(searchInput).toBeVisible();

	await signInButton.click();

	const emailAddressInput = page.getByLabel('Email Address');

	await expect(emailAddressInput).toBeVisible();

	await emailAddressInput.fill(`${screenName}${domain}`);

	await page.getByLabel('Password').fill(password);
	await page.getByLabel('Remember Me').setChecked(rememberMe);

	if ((await signInButton.count()) === 1) {
		await signInButton.click();
	}
	else {
		await page
			.getByLabel('Sign In- Loading')
			.getByRole('button', {name: 'Sign In'})
			.click();
	}

	await expect(
		page.getByLabel(`${name} ${surname} User Profile`)
	).toBeVisible({
		timeout: 30 * 1000,
	});

	return await page.context().cookies();
}

export async function performLogout(page: Page) {
	await page.goto('/');

	await page.getByTitle('User Profile Menu').click();

	await page.getByRole('menuitem', {name: 'Sign Out'}).click();
}

export async function performUserSwitch(
	page: Page,
	screenName: LoginScreenName | string
) {
	await performLogout(page);

	await performLogin(page, screenName);
}

export default performLogin;

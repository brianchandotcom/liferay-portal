/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {LoginPage} from '../pages/LoginPage';
import {LogoutPage} from '../pages/LogoutPage';

export async function customerPerformLogin(
	page: Page,
	userEmailAddress: string
) {
	const loginPage = new LoginPage(page);

	await loginPage.goto();

	await loginPage.emailField.fill(userEmailAddress);

	await loginPage.passwordField.fill('test');

	await loginPage.rememberMeCheckBox.setChecked(true);

	await loginPage.signInButton.click();

	await page.waitForSelector('body', {state: 'visible'});
}

export async function customerPerformLogout(page: Page) {
	const logoutPage = new LogoutPage(page);

	await logoutPage.goto();
}

export async function customerPerformUserSwitch(
	page: Page,
	userEmailAddress: string
) {
	await customerPerformLogout(page);

	await customerPerformLogin(page, userEmailAddress);
}

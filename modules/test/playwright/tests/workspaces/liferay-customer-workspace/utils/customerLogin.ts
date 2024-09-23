/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { expect, Page } from "@playwright/test";

import { HomePage } from "../pages/HomePage";
import { LoginPage } from "../pages/LoginPage";

export async function customerPerformLogin(page: Page, userEmailAddress: string) {

    const homePage = new HomePage(page);
    const loginPage = new LoginPage(page);

    await homePage.goto();

    await loginPage.emailField.fill(userEmailAddress);

    await loginPage.passwordField.fill('test');

	await loginPage.rememberMeCheckBox.setChecked(true);

    await loginPage.signInButton.click();

    await expect(
        homePage.heading
    ).toBeVisible({
        timeout: 30 * 1000,
    });
	
}

export async function customerPerformLogout(page: Page) {

    const homePage = new HomePage(page);

    await homePage.accountMenu.click();

	await homePage.signOutButton.click();
	
}

export async function customerPerformUserSwitch(page: Page, userEmailAddress: string) {

    await customerPerformLogout(page);

	await customerPerformLogin(page, userEmailAddress);
	
}

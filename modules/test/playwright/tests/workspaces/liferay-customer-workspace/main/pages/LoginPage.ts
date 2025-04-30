/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class LoginPage {
	readonly emailField: Locator;
	readonly page: Page;
	readonly passwordField: Locator;
	readonly rememberMeCheckBox: Locator;
	readonly signInButton: Locator;
	readonly signOutButton: Locator;

	constructor(page: Page) {
		this.emailField = page.getByLabel('Email Address');
		this.page = page;
		this.passwordField = page.getByLabel('Password');
		this.rememberMeCheckBox = page.getByLabel('Remember Me');
		this.signInButton = page.getByRole('button', {name: 'Sign In'});
	}

	async goto() {
		await this.page.goto(`/c/portal/login`);
	}
}

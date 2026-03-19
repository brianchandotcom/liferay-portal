/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class UserLoginPage {
	readonly authenticationFailedAlert: Locator;
	readonly emailAddressInput: Locator;
	readonly forgotPasswordLink: Locator;
	readonly iAgreeButton: Locator;
	readonly page: Page;
	readonly passwordInput: Locator;
	readonly sendNewPasswordButton: Locator;
	readonly signInButton: Locator;
	readonly userProfileMenuButton: Locator;

	constructor(page: Page) {
		this.authenticationFailedAlert = page
			.getByRole('alert')
			.getByText('Authentication failed');
		this.emailAddressInput = page.getByLabel('Email Address');
		this.forgotPasswordLink = page
			.getByRole('link', {
				name: 'Forgot Password',
			})
			.or(page.getByRole('menuitem', {name: 'Forgot Password'}));
		this.iAgreeButton = page.getByRole('button', {name: 'I Agree'});
		this.page = page;
		this.passwordInput = page.getByLabel('Password');
		this.sendNewPasswordButton = page.getByRole('button', {
			name: 'Send New Password',
		});
		this.signInButton = page.getByRole('button', {name: 'Sign In'}).last();
		this.userProfileMenuButton = page.getByTitle('User Profile Menu');
	}

	async goto() {
		await this.page.goto('/');

		await this.page.getByRole('button', {name: 'Sign In'}).click();
	}
}

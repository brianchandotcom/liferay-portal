/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class UserRegistrationPage {
	readonly createAccountLink: Locator;
	readonly emailAddressInput: Locator;
	readonly firstNameInput: Locator;
	readonly lastNameInput: Locator;
	readonly page: Page;
	readonly passwordInput: Locator;
	readonly reenterPasswordInput: Locator;
	readonly saveButton: Locator;
	readonly screenNameInput: Locator;
	readonly signInButton: Locator;

	constructor(page: Page) {
		this.createAccountLink = page
			.getByRole('link', {name: 'Create Account'})
			.or(page.getByRole('menuitem', {name: 'Create Account'}));
		this.emailAddressInput = page
			.locator('form')
			.filter({hasText: 'Screen Name'})
			.getByLabel('Email Address');
		this.firstNameInput = page.getByLabel('First Name');
		this.lastNameInput = page.getByLabel('Last Name');
		this.page = page;
		this.passwordInput = page.getByLabel('Password').first();
		this.reenterPasswordInput = page.getByLabel('Reenter Password');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.screenNameInput = page.getByLabel('Screen Name');
		this.signInButton = page.getByRole('button', {name: 'Sign In'});
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class CreateAccountPage {
	readonly firstNameInput: Locator;
	readonly lastNameInput: Locator;
	readonly page: Page;
	readonly password1Input: Locator;
	readonly password2Input: Locator;
	readonly saveButton: Locator;
	readonly screenNameInput: Locator;

	constructor(page: Page) {
		this.page = page;
		this.firstNameInput = page.locator('input[name$="_firstName"]');
		this.lastNameInput = page.locator('input[name$="_lastName"]');
		this.password1Input = page.locator('input[name$="_password1"]');
		this.password2Input = page.locator('input[name$="_password2"]');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.screenNameInput = page.locator('input[name$="_screenName"]');
	}

	async register({
		password,
		screenName,
	}: {
		password: string;
		screenName: string;
	}) {
		await expect(this.screenNameInput).toBeVisible({timeout: 30000});

		await this.screenNameInput.fill(screenName);
		await this.firstNameInput.fill(screenName);
		await this.lastNameInput.fill(screenName);
		await this.password1Input.fill(password);
		await this.password2Input.fill(password);

		await this.saveButton.click();
	}
}

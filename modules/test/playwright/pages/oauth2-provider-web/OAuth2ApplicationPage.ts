/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {GlobalMenuPage} from '../product-navigation-applications-menu/GlobalMenuPage';

export class OAuth2ApplicationPage {
	readonly addButton: Locator;
	readonly ercInput: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly nameInput: Locator;
	readonly page: Page;
	readonly redirectURIsInput: Locator;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.addButton = page.getByRole('link', {
			name: 'Add OAuth 2 Application',
		});
		this.ercInput = page.getByRole('textbox', {
			name: 'OAuth 2 Application External',
		});
		this.globalMenuPage = new GlobalMenuPage(page);
		this.nameInput = page.getByRole('textbox', {
			name: 'Name Required Provide a name',
		});
		this.page = page;
		this.redirectURIsInput = page.getByRole('textbox', {
			name: 'Characters Maximum:',
		});
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}

	async createApplication(name: string, redirectURI: string) {
		await this.addButton.click();

		await expect(this.nameInput).toBeVisible();

		await this.nameInput.fill(name);
		await this.redirectURIsInput.fill(redirectURI);
		await this.saveButton.click();

		await expect(
			this.page.getByText('Your request completed successfully')
		).toBeVisible();
	}

	async deleteApplication(name: string) {
		await this.goTo();

		const row = this.page.locator('tr').filter({hasText: name});

		this.page.once('dialog', (dialog) => dialog.accept());

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('link', {name: 'Delete'}),
			trigger: row.locator('.dropdown-toggle'),
		});

		await expect(
			this.page.getByText('Your request completed successfully')
		).toBeVisible();
	}

	async goTo() {
		await this.globalMenuPage.goToControlPanel('OAuth 2 Administration');

		await expect(this.addButton).toBeVisible();
	}

	async goToApplication(name: string) {
		await this.page.getByRole('link', {exact: true, name}).click();

		await expect(this.nameInput).toBeVisible();
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {GlobalMenuPage} from '../product-navigation-applications-menu/GlobalMenuPage';

export class ViewAttributesPage {
	readonly addCustomFieldButton: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly customFieldTableRowLink: (
		customFieldName: string
	) => Promise<Locator>;
	readonly page: Page;
	readonly selectAllItemsCheckbox: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.addCustomFieldButton = page.getByRole('link', {
			name: 'Add Custom Field',
		});
		this.globalMenuPage = new GlobalMenuPage(page);
		this.customFieldTableRowLink = async (customFieldName: string) => {
			const customFieldTableRow = await page
				.getByRole('row')
				.filter({hasText: customFieldName});

			if (customFieldTableRow.isVisible()) {
				return customFieldTableRow;
			}

			throw new Error(
				`Cannot locate custom field with name ${customFieldName}`
			);
		};
		this.page = page;
		this.selectAllItemsCheckbox = page.getByRole('checkbox', {
			name: 'Select All Items on the Page',
		});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
	}

	async goto(resource: string, forceReload = false) {
		if (forceReload) {
			this.globalMenuPage.goToHome();
		}

		await this.globalMenuPage.goToControlPanel('Custom Fields');

		await this.page
			.getByRole('link', {exact: true, name: resource})
			.click();
	}

	async deleteCustomField(customFieldName: string, resource: string) {
		await this.goto(resource);

		await this.addCustomFieldButton.waitFor();

		this.page.once('dialog', (dialog) => {
			dialog.accept();
		});

		const row = await this.customFieldTableRowLink(customFieldName);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('link', {name: 'Delete'}),
			trigger: row.locator('.dropdown-toggle'),
		});

		await expect(this.successMessage).toBeVisible();

		await this.page.locator('.alert').getByLabel('Close').click();
	}

	async deleteCustomFields(resource: string) {
		await this.goto(resource);

		await this.addCustomFieldButton.waitFor();

		await this.selectAllItemsCheckbox.check();

		await this.page.getByRole('button', {name: 'Delete'}).click();

		await expect(this.successMessage).toBeVisible();

		await this.page.locator('.alert').getByLabel('Close').click();
	}
}

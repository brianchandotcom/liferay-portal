/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class ViewAttributesPage {
	readonly addCustomFieldButton: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly customFieldTableRowLink: (
		customFieldName: string
	) => Promise<Locator>;
	readonly page: Page;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.addCustomFieldButton = page.getByRole('link', {
			name: 'Add Custom Field',
		});
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
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
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
	}

	async goto(resource: string, forceReload = false) {
		await this.applicationsMenuPage.goToCustomFields(forceReload);

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

		await expect(await this.successMessage).toBeVisible();
		await this.page.getByLabel('Close').click();
	}
}

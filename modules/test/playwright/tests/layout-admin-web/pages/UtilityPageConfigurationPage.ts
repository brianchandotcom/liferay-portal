/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page} from '@playwright/test';

import {UtilityPagesPage} from '../../layout-admin-web/pages/UtilityPagesPage';

export class UtilityPageConfigurationPage {
	readonly page: Page;

	readonly htmlDescriptionPlaceholder: Locator;
	readonly htmlTitlePlaceholder: Locator;
	readonly saveButton: Locator;
	readonly utilityPagesPage: UtilityPagesPage;

	constructor(page: Page) {
		this.page = page;

		this.htmlDescriptionPlaceholder = page.getByPlaceholder('Description');
		this.htmlTitlePlaceholder = page.getByPlaceholder('Title');
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
		this.utilityPagesPage = new UtilityPagesPage(page);
	}

	async goto(title: string) {
		await this.utilityPagesPage.goto();
		await this.utilityPagesPage.clickOnAction('Configure', title);
	}

	async setUtilityPageConfiguration(
		htmlDescription: string,
		htmlTitle: string,
		title: string
	) {
		await this.goto(title);

		await this.htmlDescriptionPlaceholder.waitFor();

		await this.htmlDescriptionPlaceholder.fill(htmlDescription);
		await this.htmlTitlePlaceholder.fill(htmlTitle);

		await this.saveButton.click();

		await this.page
			.getByText('The page was updated successfully.')
			.waitFor();
	}
}

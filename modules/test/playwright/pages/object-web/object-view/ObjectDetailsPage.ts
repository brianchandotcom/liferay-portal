/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ViewObjectDefinitionsPage} from '../ViewObjectDefinitionsPage';

export class ObjectDetailsPage {
	readonly detailsTabItem: Locator;
	readonly saveButton: Locator;
	readonly viewObjectDefinitionsPage: ViewObjectDefinitionsPage;
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
		this.detailsTabItem = page
			.getByRole('listitem')
			.filter({hasText: 'Details'});
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.viewObjectDefinitionsPage = new ViewObjectDefinitionsPage(page);
	}

	async goto(objectDefinitionLabel: string) {
		await this.viewObjectDefinitionsPage.goto();

		await this.viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			objectDefinitionLabel
		);

		await this.detailsTabItem.click();

		// Ensure that the page has loaded

		await this.saveButton.waitFor();
	}

	async updateConfiguration({
		fieldLabel,
		value,
	}: {
		fieldLabel: string;
		value: boolean;
	}) {
		const field = this.page.getByLabel(fieldLabel, {exact: true});

		if (value) {
			await field.check();
		}
		else {
			await field.uncheck();
		}

		await this.saveButton.click();

		await this.page
			.getByText('The object was saved successfully.')
			.waitFor();
	}
}

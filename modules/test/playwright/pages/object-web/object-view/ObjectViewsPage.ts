/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ViewObjectDefinitionsPage} from '../ViewObjectDefinitionsPage';

export class ObjectViewPage {
	readonly addObjectViewButton: Locator;
	readonly objectViewNameModalInput: Locator;
	readonly objectViewNameSaveModalButton: Locator;
	readonly viewsTabItem: Locator;
	readonly viewObjectDefinitionsPage: ViewObjectDefinitionsPage;

	constructor(page: Page) {
		this.addObjectViewButton = page.getByTitle('Add Object View');
		this.objectViewNameModalInput = page.getByLabel('Name' + 'Mandatory');
		this.objectViewNameSaveModalButton = page.getByRole('button', {
			name: 'Save',
		});
		this.viewsTabItem = page
			.getByRole('listitem')
			.filter({hasText: 'Views'});
		this.viewObjectDefinitionsPage = new ViewObjectDefinitionsPage(page);
	}

	async createObjectView(objectViewName: string) {
		await this.addObjectViewButton.click();
		await this.objectViewNameModalInput.fill(objectViewName);
		await this.objectViewNameSaveModalButton.click();
	}

	async goto(objectDefinitionLabel: string) {
		await this.viewObjectDefinitionsPage.goto();

		await this.viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
			objectDefinitionLabel
		);

		await this.viewsTabItem.click();
	}
}

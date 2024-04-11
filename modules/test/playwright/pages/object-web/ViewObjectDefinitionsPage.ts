/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class ViewObjectDefinitionsPage {
	readonly addObjectFolderButton: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly createObjectDefinitionButton: Locator;
	readonly createObjectFolderButton: Locator;
	readonly defaultObjectFolderLink: Locator;
	readonly objectFolderActionsLink: Locator;
	readonly objectFolderDeleteFolderOption: Locator;
	readonly objectFolderEditLabelAndERCOption: Locator;
	readonly objectFolderLabel: Locator;
	readonly page: Page;
	readonly viewInModelBuilderButton: Locator;

	constructor(page: Page) {
		this.addObjectFolderButton = page.getByLabel('Add Object Folder');
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.createObjectDefinitionButton = page.getByTestId(
			'fdsCreationActionButton'
		);
		this.createObjectFolderButton = page.getByRole('button', {
			name: 'Create Folder',
		});
		this.defaultObjectFolderLink = page
			.locator('li')
			.filter({hasText: 'Default'});
		this.objectFolderActionsLink = page
			.locator('div.lfr__object-web-view-object-definitions-title-kebab')
			.getByLabel('Object Folder Actions');
		this.objectFolderDeleteFolderOption = page.getByRole('menuitem', {
			name: 'Delete Folder',
		});
		this.objectFolderEditLabelAndERCOption = page.getByRole('menuitem', {
			name: 'Edit Label and ERC',
		});
		this.objectFolderLabel = page.locator('input[name="label"]');
		this.page = page;
		this.viewInModelBuilderButton = page.getByLabel(
			'View in Model Builder'
		);
	}

	async clickDefaultObjectFolder() {
		await this.defaultObjectFolderLink.click();
	}

	async createObjectFolder(objectFolderLabel: string) {
		await this.addObjectFolderButton.click();
		await this.objectFolderLabel.click();
		await this.objectFolderLabel.fill(objectFolderLabel);

		const responsePromise = this.page.waitForResponse('**/object-folders');
		await this.createObjectFolderButton.click();
		const response = await responsePromise;

		return response.json();
	}

	async goto() {
		await this.applicationsMenuPage.goToObjects();
	}

	async openObjectFolderActions() {
		await this.objectFolderActionsLink.click();
	}

	async openObjectFolder(objectFolderLabel: string) {
		await this.page
			.locator('li')
			.filter({hasText: objectFolderLabel})
			.click();
	}

	async viewInModelBuilder() {
		this.viewInModelBuilderButton.click();
	}
}

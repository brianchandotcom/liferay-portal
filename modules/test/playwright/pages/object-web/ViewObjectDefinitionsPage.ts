/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class ViewObjectDefinitionsPage {
	readonly addObjectFolderButton: Locator;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly createObjectDefinitionButton: Locator;
	readonly createObjectFolderButton: Locator;
	readonly confirmObjectFolderNameInput: Locator;
	readonly defaultObjectFolderLink: Locator;
	readonly deleteObjectFolderButton: Locator;
	readonly frontendDataSetEntries: Locator;
	readonly objectFolderActionsLink: Locator;
	readonly objectFolderCardHeader: Locator;
	readonly objectFolderDeleteFolderOption: Locator;
	readonly objectFolderEditLabelAndERCOption: Locator;
	readonly objectFolders: Locator;
	readonly objectFolderLabel: Locator;
	readonly page: Page;
	readonly viewInModelBuilderButton: Locator;

	constructor(page: Page) {
		this.addObjectFolderButton = page.getByLabel('Add Object Folder');
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.confirmObjectFolderNameInput = page.locator(
			'input[placeholder="Confirm Folder Name"]'
		);
		this.createObjectDefinitionButton = page.getByTestId(
			'fdsCreationActionButton'
		);
		this.createObjectFolderButton = page.getByRole('button', {
			name: 'Create Folder',
		});
		this.defaultObjectFolderLink = page
			.locator('li')
			.filter({hasText: 'Default'});
		this.deleteObjectFolderButton = page.getByRole('button', {
			name: 'Delete',
		});
		this.frontendDataSetEntries = page.locator('div.table-list-title a');
		this.objectFolders = page
			.getByRole('list')
			.filter({hasText: 'Default'});
		this.objectFolderActionsLink = page
			.locator('div.lfr__object-web-view-object-definitions-title-kebab')
			.getByLabel('Object Folder Actions');
		this.objectFolderCardHeader = page.locator(
			'div.lfr-objects__card-header'
		);
		this.objectFolderDeleteFolderOption = page.getByRole('menuitem', {
			name: 'Delete Object Folder',
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

	async deleteObjectFolder(objectFolderName: string) {
		await this.objectFolderDeleteFolderOption.click();
		await this.confirmObjectFolderNameInput.click();
		await this.confirmObjectFolderNameInput.fill(objectFolderName);
		await this.deleteObjectFolderButton.click();
	}

	getObjectFolderCardHeaderLabel = (objectFolderLabel: string) => {
		return this.objectFolderCardHeader
			.locator('span')
			.filter({hasText: objectFolderLabel})
			.first();
	};

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.objects}`,
			{waitUntil: 'load'}
		);
	}

	async openObjectFolderActions() {
		await this.objectFolderActionsLink.click();
	}

	async openObjectFolder(objectFolderLabel: string) {
		await this.page
			.getByRole('listitem')
			.filter({hasText: objectFolderLabel})
			.click();
	}

	async viewInModelBuilder() {
		this.viewInModelBuilderButton.click();
	}
}

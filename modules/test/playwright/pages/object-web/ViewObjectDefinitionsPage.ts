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
	readonly defaultObjectFolder: Locator;
	readonly deleteObjectDefinitionOption: Locator;
	readonly deleteObjectFolderButton: Locator;
	readonly frontendDataSetEntries: Locator;
	readonly objectFolderActions: Locator;
	readonly objectFolderCardHeader: Locator;
	readonly objectFolderDeleteFolderOption: Locator;
	readonly objectFolderEditLabelAndERCOption: Locator;
	readonly objectFolders: Locator;
	readonly objectFolderLabelInput: Locator;
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
		this.defaultObjectFolder = page
			.getByRole('listitem')
			.filter({hasText: 'Default'});
		this.deleteObjectDefinitionOption = page.getByRole('menuitem', {
			name: 'Delete',
		});
		this.deleteObjectFolderButton = page.getByRole('button', {
			name: 'Delete',
		});
		this.frontendDataSetEntries = page.locator('div.table-list-title a');
		this.objectFolders = page
			.getByRole('list')
			.filter({hasText: 'Default'});
		this.objectFolderActions = page
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
		this.objectFolderLabelInput = page.locator('input[name="label"]');
		this.page = page;
		this.viewInModelBuilderButton = page.getByLabel(
			'View in Model Builder'
		);
	}

	async clickDefaultObjectFolder() {
		await this.defaultObjectFolder.click();
	}

	async clickEditObjectDefinitionLink(objectDefinitionName: string) {
		await this.page.getByRole('link', {name: objectDefinitionName}).click();
	}

	async clickDeleteObjectDefinition() {
		await this.deleteObjectDefinitionOption.click();
	}

	async createObjectFolder(objectFolderLabel: string) {
		await this.addObjectFolderButton.click();
		await this.objectFolderLabelInput.click();
		await this.objectFolderLabelInput.fill(objectFolderLabel);

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

	async editObjectDefinitionFDSLink(objectDefinitionLabel: string) {
		await this.frontendDataSetEntries.filter({
			hasText: objectDefinitionLabel
		}).click();
	}

	getObjectFolderCardHeaderERC = (objectFolderERC: string) => {
		return this.objectFolderCardHeader
			.getByRole('strong')
			.filter({hasText: objectFolderERC});
	};

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
		await this.objectFolderActions.click();
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

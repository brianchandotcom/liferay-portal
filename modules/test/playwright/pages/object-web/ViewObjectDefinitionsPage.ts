/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectFolder} from '@liferay/object-admin-rest-client-js';
import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';
import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class ViewObjectDefinitionsPage {
	readonly actionsButton: Locator;
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
		this.actionsButton = page.getByRole('button', {name: 'Actions'});
		this.addObjectFolderButton = page.getByLabel('Add Object Folder');
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.confirmObjectFolderNameInput = page.locator(
			'input[placeholder="Confirm Folder Name"]'
		);
		this.createObjectDefinitionButton =
			page.getByLabel('Create New Object');
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

	async changeObjectActivateStatus(objectDefinitionName: string) {
		await this.clickEditObjectDefinitionLink(objectDefinitionName);

		await this.page.getByRole('switch', {name: 'Activate Object'}).click();

		await this.page.getByRole('button', {name: 'Save'}).click();
	}

	async clickEditObjectDefinitionLink(objectDefinitionLabel: string) {
		await this.page
			.getByRole('link', {exact: true, name: objectDefinitionLabel})
			.click();
	}

	async createObjectFolder(objectFolderLabel: string): Promise<ObjectFolder> {
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

	async openObjectFolder(objectFolderLabel: string) {
		await this.page
			.getByRole('listitem')
			.filter({hasText: objectFolderLabel})
			.click();
	}
}

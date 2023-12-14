/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/applicationsMenu.page';

export class ObjectDefinitionsPage {
	constructor(page) {
		this.addObjectFolderButton = page.getByLabel('Add Object Folder');
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.createObjectFolderButton = page.getByRole('button', {
			name: 'Create Folder',
		});
		this.objectFolderActionsLink = page.getByLabel('folder-actions');
		this.objectFolderDeleteFolderOption = page.getByRole('menuitem', {
			name: 'Delete Folder',
		});
		this.objectFolderEditLabelAndERCOption = page.getByRole('menuitem', {
			name: 'Edit Label and ERC',
		});
		this.objectFolderLabel = page.locator('input[name="label"]');
		this.page = page;
		this.uncategorizedObjectFolderLink = page
			.locator('li')
			.filter({hasText: 'Uncategorized'});
		this.viewInModelBuilderButton = page.getByLabel(
			'View in Model Builder'
		);
	}

	async clickUncategorizedObjectFolder() {
		await this.uncategorizedObjectFolderLink.click();
	}

	async createObjectFolder(objectFolderLabel) {
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

	async openObjectFolder(objectFolderExternalReferenceCode) {
		await this.page
			.locator('li')
			.filter({hasText: objectFolderExternalReferenceCode})
			.click();
	}

	async viewInModelBuilder() {
		this.viewInModelBuilderButton.click();
	}
}

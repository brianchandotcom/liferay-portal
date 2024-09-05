/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {Locator, Page} from '@playwright/test';

export class ModelBuilderLeftSidebarPage {
	readonly createNewObjectDefinitionButton: Locator;
	readonly collapseOtherFoldersButton: Locator;
	readonly goToFolderButton: Locator;
	readonly sidebarItems: Locator;
	readonly otherObjectFolders: Locator;
	readonly selectedObjectFolder: Locator;

	constructor(page: Page) {
		this.collapseOtherFoldersButton = page.getByRole('button', {
			name: 'Other Folders',
		});
		this.createNewObjectDefinitionButton =
			page.getByText('Create New Object');
		this.goToFolderButton = page.getByRole('button', {
			exact: true,
			name: 'Go to Folder',
		});
		this.sidebarItems = page.locator('li.treeview-item div.autofit-row');
		this.otherObjectFolders = page
			.getByRole('region')
			.filter({has: page.getByTitle('Go to Folder')});
		this.selectedObjectFolder = page
			.getByRole('tabpanel')
			.getByRole('treeitem')
			.filter({hasNot: page.getByTitle('Go to Folder')})
			.first();
	}

	async clickSideBarItem(objectDefinitionLabel: string) {
		await this.sidebarItems
			.filter({hasText: objectDefinitionLabel})
			.click();
	}

	async clickObjectDefinitionActionsButtonInSidebar(
		objectDefinitionLabel: string
	) {
		await this.sidebarItems
			.filter({hasText: objectDefinitionLabel})
			.getByLabel('Actions')
			.click();
	}

	getOtherObjectFolderLocator(objectFolderLabel: string) {
		return this.otherObjectFolders
			.getByRole('treeitem')
			.filter({hasText: objectFolderLabel});
	}
}

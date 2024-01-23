/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../../../pages/product-navigation-applications-menu/ApplicationsMenuPage';

export class ClientExtensionsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly editorConfigContributorMenuItem: Locator;
	readonly itemDeleteButton: Locator;
	readonly itemEditButton: Locator;
	readonly newClientExtensionButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);

		this.editorConfigContributorMenuItem = page.getByRole('menuitem', {
			name: 'Add Editor Config Contributor',
		});

		this.itemDeleteButton = page.getByRole('menuitem', {
			name: 'Delete',
		});
		this.itemEditButton = page.getByRole('menuitem', {
			name: 'Edit',
		});

		this.newClientExtensionButton = page
			.getByRole('button')
			.and(page.getByTitle('New'));

		this.page = page;
	}

	async goto() {
		await this.applicationsMenuPage.goToClientExtensions();
	}

	async gotoNewEditorConfigContributorPage() {
		await this.goto();

		await this.newClientExtensionButton.click();
		await this.editorConfigContributorMenuItem.click();
	}

	async openItemActionsDropdown({text}: {text: string}) {
		await this.page
			.locator('.dnd-tr')
			.filter({has: this.page.getByText(text)})
			.getByRole('button', {
				name: 'Actions',
			})
			.click();
	}
}

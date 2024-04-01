/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../../../pages/product-navigation-applications-menu/ApplicationsMenuPage';

export class ClientExtensionsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly deleteMenuItem: Locator;
	readonly editMenuItem: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.deleteMenuItem = page.getByRole('menuitem', {
			name: 'Delete',
		});
		this.editMenuItem = page.getByRole('menuitem', {
			name: 'Edit',
		});
		this.page = page;
	}

	async deleteClientExtension(clientExtensionName: string) {
		await this.openItemActionsDropdown(clientExtensionName);

		this.page.on('dialog', (dialog) => dialog.accept());

		await this.deleteMenuItem.click();
	}

	async editClientExtension(clientExtensionName: string) {
		await this.openItemActionsDropdown(clientExtensionName);

		await this.editMenuItem.click();
	}

	async goto() {
		await this.applicationsMenuPage.goToClientExtensions();
	}

	async openItemActionsDropdown(clientExtensionName: string) {
		await this.page
			.locator('.dnd-tr')
			.filter({has: this.page.getByText(clientExtensionName)})
			.getByRole('button', {
				name: 'Actions',
			})
			.click();
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../../../pages/product-navigation-applications-menu/ApplicationsMenuPage';

export class ClientExtensionsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly itemActionsToggleButton: Locator;
	readonly itemDeleteButton: Locator;
	readonly itemEditButton: Locator;
	readonly newClientExtensionButton: Locator;
	readonly editorConfigContributorMenuItem: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.page = page;

		this.newClientExtensionButton = page
			.getByRole('button')
			.and(page.getByTitle('New'));

		this.editorConfigContributorMenuItem = page.getByRole('menuitem', {
			name: 'Add Editor Config Contributor',
		});

		this.itemActionsToggleButton = page.getByRole('button', {
			name: 'Actions',
		});

		this.itemDeleteButton = page.getByRole('menuitem', {
			name: 'Delete',
		});
		this.itemEditButton = page.getByRole('menuitem', {
			name: 'Edit',
		});
	}

	async goto() {
		await this.applicationsMenuPage.goToClientExtensions();
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../../product-navigation-applications-menu/ApplicationsMenuPage';
export class CommerceAdminCatalogsPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly catalogActionsButton: Locator;
	readonly page: Page;
	readonly permissionsMenuItem: Locator;
	readonly permissionsFrame: FrameLocator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.catalogActionsButton = page.getByRole('button', {
			exact: true,
			name: 'Actions',
		});
		this.page = page;
		this.permissionsFrame = page.frameLocator(
			'iframe[title="Permissions"]'
		);
		this.permissionsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Permissions',
		});
	}

	async goto() {
		await this.applicationsMenuPage.goToCommerceCatalogs();
	}
}

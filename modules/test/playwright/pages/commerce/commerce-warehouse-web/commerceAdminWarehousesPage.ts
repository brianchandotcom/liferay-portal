/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';

export class CommerceAdminWarehousesPage {
	readonly addButton: Locator;
	readonly deleteMenuItem: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly modalFieldName: Locator;
	readonly modalFrameLocator: FrameLocator;
	readonly modalSubmitButton: Locator;
	readonly page: Page;
	readonly rowActionsButton: (warehouseName: string) => Locator;
	readonly warehouseLink: (warehouseName: string) => Locator;

	constructor(page: Page) {
		this.addButton = page
			.getByTestId('managementToolbar')
			.locator('[data-testid="fdsCreationActionButton"]');
		this.deleteMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Delete',
		});
		this.globalMenuPage = new GlobalMenuPage(page);
		this.modalFrameLocator = page.frameLocator('.fds-modal-body iframe');
		this.modalFieldName =
			this.modalFrameLocator.getByLabel('Name Required');
		this.modalSubmitButton = this.modalFrameLocator.getByRole('button', {
			exact: true,
			name: 'Submit',
		});
		this.page = page;
		this.rowActionsButton = (warehouseName: string) =>
			page.getByRole('button', {name: `${warehouseName} Actions`});
		this.warehouseLink = (warehouseName: string) =>
			page.getByRole('link', {exact: true, name: warehouseName});
	}

	async goto() {
		await this.globalMenuPage.goToCommerce('Warehouses');
	}
}

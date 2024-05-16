/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {ProductMenuPage} from '../product-navigation-control-menu-web/ProductMenuPage';

export class PortletConfigurationPermissionsPage {
	readonly clearLink: Locator;
	readonly ownerRoleCell: Locator;
	readonly page: Page;
	readonly pageOptionsMenu: Locator;
	readonly permissionsFrame: FrameLocator;
	readonly permissionsMenuItem: Locator;
	readonly productMenuPage: ProductMenuPage;
	readonly resultsBanner: Locator;
	readonly saveButton: Locator;
	readonly searchBar: Locator;
	readonly siteMemberRoleCell: Locator;
	readonly successMessage: Locator;

	constructor(page: Page) {
		this.page = page;
		this.pageOptionsMenu = page.getByTitle('Open Page Options Menu');
		this.permissionsFrame = page.frameLocator(
			'iframe[title="Permissions"]'
		);
		this.permissionsMenuItem = page.getByRole('menuitem', {
			name: 'Permissions',
		});
		this.productMenuPage = new ProductMenuPage(page);

		this.clearLink = this.permissionsFrame.getByLabel('Clear');
		this.ownerRoleCell = this.permissionsFrame.getByRole('cell', {
			name: 'Owner',
		});
		this.resultsBanner =
			this.permissionsFrame.getByText('Results Found for');
		this.saveButton = this.permissionsFrame.getByRole('button', {
			name: 'Save',
		});
		this.searchBar = this.permissionsFrame.getByPlaceholder('Search for');
		this.siteMemberRoleCell = this.permissionsFrame.getByRole('cell', {
			name: 'Site Member',
		});
		this.successMessage = this.permissionsFrame.getByText(
			'Success:Your request completed successfully.'
		);
	}

	async goto() {
		await this.productMenuPage.openProductMenuIfClosed();
		await this.productMenuPage.goToPages();
		await this.pageOptionsMenu.first().click();
		await this.permissionsMenuItem.click();
	}

	async changePagination(startValue: number, endValue: number) {
		await this.permissionsFrame
			.getByText(startValue + ' Entries Per Page', {exact: true})
			.click();
		await this.permissionsFrame
			.getByRole('link', {name: endValue + ' Entries per Page'})
			.click();
		await expect(
			this.permissionsFrame.getByText('Showing 1 to ' + endValue)
		).toBeVisible();
	}
}

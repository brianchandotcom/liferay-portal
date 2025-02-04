/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';

export class RoleDefinePermissionsPage {
	readonly accessInControlPanel: Locator;
	readonly addToPage: Locator;
	readonly loading: Locator;
	readonly menuItem: (name: string) => Locator;
	readonly menuItemByTestId: (id: string) => Locator;
	readonly page: Page;
	readonly permissionCheckbox: (permissionName: string) => Locator;
	readonly portletResourceLabel: Locator;
	readonly resourceName: (resourceName: string) => Locator;
	readonly resourceRemoveLink: (resourceName: string) => Locator;
	readonly resourceSection: (title: string) => Locator;
	readonly saveButton: Locator;
	readonly searchInput: Locator;

	constructor(page: Page) {
		this.accessInControlPanel = page.getByText('Access in Control Panel');
		this.addToPage = page.getByText('Add to Page');
		this.loading = page.getByText('Loading');
		this.menuItem = (name: string) => {
			return page.getByRole('menuitem', {name});
		};
		this.menuItemByTestId = (id: string) => {
			return page.getByTestId(id).getByRole('menuitem');
		};
		this.page = page;
		this.permissionCheckbox = (permissionName: string) => {
			return page
				.getByLabel(permissionName)
				.and(page.getByRole('checkbox'));
		};
		this.portletResourceLabel = page.getByTestId('portletResourceLabel');
		this.resourceName = (resourceName) =>
			page.getByRole('cell', {name: resourceName});
		this.resourceRemoveLink = (resourceName) =>
			this.resourceName(resourceName)
				.locator('..')
				.getByRole('link', {name: 'Remove'});
		this.resourceSection = (title: string) => {
			return page
				.locator('.sheet-tertiary-title')
				.filter({hasText: title});
		};
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
		this.searchInput = page.getByPlaceholder('Search');
	}

	async changePermission(
		menuItemName: string,
		permissionName: string,
		check: boolean
	) {
		await this.searchInput.click();
		await this.searchInput.fill(menuItemName);

		await expect(this.menuItem(menuItemName)).toBeVisible();

		await this.menuItem(menuItemName).click();

		await this.page.waitForLoadState('domcontentloaded');

		if (check) {
			await this.permissionCheckbox(permissionName).check();
		}
		else {
			await this.permissionCheckbox(permissionName).uncheck();
		}

		await this.saveButton.click();

		await waitForAlert(
			this.page,
			'Success:The role permissions were updated.'
		);
	}
}

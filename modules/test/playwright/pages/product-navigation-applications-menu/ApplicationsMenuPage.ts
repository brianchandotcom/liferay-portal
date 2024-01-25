/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {HomePage} from '../portal-web/HomePage';

export class ApplicationsMenuPage {
	private readonly apiBuilderMenuItem: Locator;
	private readonly applicationsMenuTabButton: Locator;
	private readonly clientExtensionsLink: Locator;
	private readonly controlPanelButton: Locator;
	private readonly dataMigrationCenterMenuItem: Locator;
	private readonly dataSetManagerMenuItem: Locator;
	private readonly homePage: HomePage;
	private readonly instanceSettingsLink: Locator;
	private readonly objectsMenuItem: Locator;
	readonly page: Page;
	private readonly usersAndOrganizationsItem: Locator;

	constructor(page: Page) {
		this.apiBuilderMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'API Builder',
		});
		this.applicationsMenuTabButton = page.getByRole('tab', {
			name: 'Applications',
		});
		this.clientExtensionsLink = page.getByRole('menuitem', {
			name: 'Client Extensions',
		});
		this.controlPanelButton = page.getByRole('tab', {
			name: 'Control Panel',
		});
		this.homePage = new HomePage(page);
		this.instanceSettingsLink = page.getByRole('link', {
			name: 'Instance Settings',
		});
		this.dataMigrationCenterMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Data Migration Center',
		});
		this.dataSetManagerMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Data Sets',
		});
		this.objectsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Objects',
		});
		this.page = page;
		this.usersAndOrganizationsItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Users and Organizations',
		});
	}

	async goto() {
		await this.homePage.goto();
		await this.homePage.openApplicationMenu();

		await expect(this.applicationsMenuTabButton).toBeVisible();
	}

	async goToDataSetManager() {
		await this.goToControlPanel();
		await this.dataSetManagerMenuItem.click();
	}

	async goToApplicationsMenu() {
		await this.goto();
		await this.applicationsMenuTabButton.click();
	}

	async goToClientExtensions() {
		await this.goto();
		await this.clientExtensionsLink.click();
	}

	async goToDataMigrationCenter() {
		await this.goToApplicationsMenu();
		await this.dataMigrationCenterMenuItem.click();
	}

	async goToApiBuilder() {
		await this.goToControlPanel();
		await this.apiBuilderMenuItem.click();
	}

	async goToObjects() {
		await this.goToControlPanel();
		await this.objectsMenuItem.click();
	}

	async goToInstanceSettings() {
		await this.goToControlPanel();
		await this.instanceSettingsLink.click();
	}

	async goToControlPanel() {
		await this.goto();
		await this.controlPanelButton.click();
	}

	async goToUsersAndOrganizations() {
		await this.goto();
		await this.controlPanelButton.click();
		await this.usersAndOrganizationsItem.click();
	}
}

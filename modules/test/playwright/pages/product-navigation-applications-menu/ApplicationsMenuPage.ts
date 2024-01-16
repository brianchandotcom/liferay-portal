/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class ApplicationsMenuPage {
	readonly applicationMenuButton: Locator;
	readonly applicationsMenuTabButton: Locator;
	readonly clientExtensionsLink: Locator;
	readonly controlPanelButton: Locator;
	readonly dataMigrationCenterMenuItem: Locator;
	readonly instanceSettingsLink: Locator;
	readonly objectsLink: Locator;
	readonly objectsMenuItem: Locator;
	readonly page: Page;
	readonly signInButton: Locator;
	readonly usersAndOrganizationsItem: Locator;

	constructor(page: Page) {
		this.applicationMenuButton = page.getByLabel(
			'Open Applications MenuCtrl+'
		);
		this.applicationsMenuTabButton = page.getByRole('tab', {
			name: 'Applications',
		});
		this.clientExtensionsLink = page.getByRole('menuitem', {
			name: 'Client Extensions',
		});
		this.controlPanelButton = page.getByRole('tab', {
			name: 'Control Panel',
		});
		this.instanceSettingsLink = page.getByRole('link', {
			name: 'Instance Settings',
		});
		this.dataMigrationCenterMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Data Migration Center',
		});
		this.objectsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Objects',
		});
		this.page = page;
		this.signInButton = page.getByRole('button', {name: 'Sign In'});
		this.usersAndOrganizationsItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Users and Organizations',
		});
	}

	async goto() {
		await this.page.goto('/');
	}

	async goToApplicationsMenu() {
		await this.goto();
		await this.applicationMenuButton.click();
		await this.applicationsMenuTabButton.click();
	}

	async goToClientExtensions() {
		await this.goto();
		await this.applicationMenuButton.click();
		await this.clientExtensionsLink.click();
	}

	async goToDataMigrationCenter() {
		await this.goToApplicationsMenu();
		await this.dataMigrationCenterMenuItem.click();
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
		await this.applicationMenuButton.click();
		await this.controlPanelButton.click();
	}

	async goToUsersAndOrganizations() {
		await this.goto();
		await this.applicationMenuButton.click();
		await this.controlPanelButton.click();
		await this.usersAndOrganizationsItem.click();
	}
}

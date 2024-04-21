/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {HomePage} from '../portal-web/HomePage';

export class ApplicationsMenuPage {
	private readonly accountsItem: Locator;
	private readonly aiCreatorLink: Locator;
	private readonly announcementsItem: Locator;
	private readonly apiBuilderMenuItem: Locator;
	private readonly applicationsMenuTabButton: Locator;
	private readonly clientExtensionsLink: Locator;
	private readonly commerceChannelsMenuItem: Locator;
	private readonly commerceOrdersMenuItem: Locator;
	private readonly commercePanelButton: Locator;
	private readonly controlPanelButton: Locator;
	private readonly dataMigrationCenterMenuItem: Locator;
	private readonly dataSetManagerMenuItem: Locator;
	private readonly defaultPermissionsLink: Locator;
	private readonly gogoShellItem: Locator;
	private readonly homePage: HomePage;
	private readonly instanceSettingsMenuItem: Locator;
	private readonly jobSchedulerMenuItem: Locator;
	private readonly oAuth2Administration: Locator;
	private readonly objectsMenuItem: Locator;
	private readonly page: Page;
	private readonly paymentsMenuItem: Locator;
	private readonly processBuilderItem: Locator;
	private readonly productsMenuItem: Locator;
	private readonly queueMenuItem: Locator;
	private readonly serviceAccountsItem: Locator;
	private readonly sitesItem: Locator;
	private readonly systemSettingsItem: Locator;
	private readonly serverAdministrationItem: Locator;
	private readonly siteTemplatesButton: Locator;
	private readonly usersAndOrganizationsItem: Locator;

	constructor(page: Page) {
		this.accountsItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Accounts',
		});
		this.aiCreatorLink = page.getByRole('link', {
			exact: true,
			name: 'AI Creator',
		});
		this.announcementsItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Announcements and Alerts',
		});
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
		this.commerceChannelsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Channels',
		});
		this.commerceOrdersMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Orders',
		});
		this.commercePanelButton = page.getByRole('tab', {
			name: 'Commerce',
		});
		this.controlPanelButton = page.getByRole('tab', {
			name: 'Control Panel',
		});
		this.gogoShellItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Gogo Shell',
		});
		this.homePage = new HomePage(page);
		this.dataMigrationCenterMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Data Migration Center',
		});
		this.dataSetManagerMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Data Sets',
		});
		this.defaultPermissionsLink = page.getByRole('link', {
			exact: true,
			name: 'Default Permissions',
		});
		this.instanceSettingsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Instance Settings',
		});
		this.jobSchedulerMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Job Scheduler',
		});
		this.oAuth2Administration = page.getByRole('menuitem', {
			exact: true,
			name: 'OAuth 2 Administration',
		});
		this.objectsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Objects',
		});
		this.page = page;
		this.paymentsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Payments',
		});
		this.processBuilderItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Process Builder',
		});
		this.productsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Products',
		});
		this.queueMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Queue',
		});
		this.serviceAccountsItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Service Accounts',
		});
		this.serverAdministrationItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Server Administration',
		});
		this.sitesItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Sites',
		});
		this.siteTemplatesButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Site Templates',
		});
		this.systemSettingsItem = page.getByRole('menuitem', {
			exact: true,
			name: 'System Settings',
		});
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

	async goToAccounts() {
		await this.goto();
		await this.controlPanelButton.click();
		await this.accountsItem.click();
	}

	async goToAnnouncements() {
		await this.goToApplicationsMenu();
		await this.announcementsItem.click();
	}

	async goToDataSetManager() {
		await this.goToControlPanel();
		await this.dataSetManagerMenuItem.click();
	}

	async goToApplicationsMenu() {
		await this.goto();
		await this.applicationsMenuTabButton.click();
	}

	async goToAICreator() {
		await this.goToInstanceSettings();
		await this.aiCreatorLink.click();
	}

	async goToClientExtensions() {
		await this.goto();
		await this.clientExtensionsLink.click();
	}

	async goToDataMigrationCenter() {
		await this.goToApplicationsMenu();
		await this.dataMigrationCenterMenuItem.click();
	}

	async goToDefaultPermissions() {
		await this.goToInstanceSettings();
		await this.defaultPermissionsLink.click();
	}

	async goToAPIBuilder() {
		await this.goToControlPanel();
		await this.apiBuilderMenuItem.click();
	}

	async goToGogoShell() {
		await this.goToControlPanel();
		await this.gogoShellItem.click();
	}

	async goToObjects() {
		await this.goToControlPanel();
		await this.objectsMenuItem.click();
	}

	async goToServerAdministration() {
		await this.goToControlPanel();
		await this.serverAdministrationItem.click();
	}

	async goToSiteTemplates() {
		await this.goToControlPanel();
		await this.siteTemplatesButton.click();
	}

	async goToSites() {
		await this.goToControlPanel();
		await this.sitesItem.click();
	}

	async goToSystemSettings() {
		await this.goToControlPanel();
		await this.systemSettingsItem.click();
	}

	async goToInstanceSettings() {
		await this.goToControlPanel();
		await this.instanceSettingsMenuItem.click();
	}

	async goToJobScheduler() {
		await this.goToControlPanel();
		await this.jobSchedulerMenuItem.click();
	}

	async goToCommerceChannels() {
		await this.goToCommercePanel();
		await this.commerceChannelsMenuItem.click();
	}

	async goToCommercePanel() {
		await this.goto();
		await this.commercePanelButton.click();
	}

	async goToCommerceOrders() {
		await this.goToCommercePanel();
		await this.commerceOrdersMenuItem.click();
	}

	async goToPayments() {
		await this.goToCommercePanel();
		await this.paymentsMenuItem.click();
	}

	async goToProducts() {
		await this.goToCommercePanel();
		await this.productsMenuItem.click();
	}

	async goToQueue() {
		await this.goToControlPanel();
		await this.queueMenuItem.click();
	}

	async goToSite(name: string = 'Liferay DXP') {
		await this.goto();
		await this.page.getByRole('link', {exact: true, name}).click();
	}

	async goToControlPanel() {
		await this.goto();
		await this.controlPanelButton.click();
	}

	async goToOauth2Administration() {
		await this.goToControlPanel();
		await this.oAuth2Administration.click();
	}

	async goToProcessBuilder() {
		await this.goToApplicationsMenu();
		await this.processBuilderItem.click();
	}

	async goToServiceAccounts() {
		await this.goto();
		await this.controlPanelButton.click();
		await this.serviceAccountsItem.click();
	}

	async goToUsersAndOrganizations() {
		await this.goto();
		await this.controlPanelButton.click();
		await this.usersAndOrganizationsItem.click();
	}
}

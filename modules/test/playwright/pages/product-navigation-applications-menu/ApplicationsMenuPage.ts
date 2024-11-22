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
	private readonly blueprintsItem: Locator;
	private readonly clientExtensionsLink: Locator;
	private readonly commerceChannelsMenuItem: Locator;
	private readonly commerceDiscountsMenuItem: Locator;
	private readonly commerceOrdersMenuItem: Locator;
	private readonly commercePanelButton: Locator;
	private readonly commerceProductConfigurationListsMenuItem: Locator;
	private readonly commerceReturnsMenuItem: Locator;
	private readonly commerceShipmentsMenuItem: Locator;
	private readonly commerceSpecificationsMenuItem: Locator;
	private readonly componentsMenuItem: Locator;
	private readonly controlPanelButton: Locator;
	private readonly countriesManagementItem: Locator;
	private readonly customFieldsMenuItem: Locator;
	private readonly dataMigrationCenterMenuItem: Locator;
	private readonly dataSetManagerMenuItem: Locator;
	private readonly defaultPermissionsLink: Locator;
	private readonly gogoShellItem: Locator;
	private readonly homePage: HomePage;
	private readonly instanceSettingsMenuItem: Locator;
	private readonly jobSchedulerMenuItem: Locator;
	private readonly oAuth2Administration: Locator;
	private readonly objectsMenuItem: Locator;
	private readonly metricsItem: Locator;
	private readonly page: Page;
	private readonly paymentsMenuItem: Locator;
	private readonly picklistsMenuItem: Locator;
	private readonly processBuilderItem: Locator;
	private readonly productsMenuItem: Locator;
	private readonly queueMenuItem: Locator;
	private readonly rolesItem: Locator;
	private readonly samlAdminItem: Locator;
	private readonly searchItem: Locator;
	private readonly serviceAccountsItem: Locator;
	private readonly sitesItem: Locator;
	private readonly systemSettingsItem: Locator;
	private readonly serverAdministrationItem: Locator;
	private readonly siteTemplatesButton: Locator;
	private readonly userGroupsItem: Locator;
	private readonly usersAndOrganizationsItem: Locator;
	private readonly virtualInstancesItem: Locator;

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
		this.blueprintsItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Blueprints',
		});
		this.clientExtensionsLink = page.getByRole('menuitem', {
			name: 'Client Extensions',
		});
		this.commerceChannelsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Channels',
		});
		this.commerceDiscountsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Discounts',
		});
		this.commerceOrdersMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Orders',
		});
		this.commercePanelButton = page.getByRole('tab', {
			name: 'Commerce',
		});
		this.commerceProductConfigurationListsMenuItem = page.getByRole(
			'menuitem',
			{
				exact: true,
				name: 'Product Configurations',
			}
		);
		this.commerceReturnsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Returns',
		});
		this.commerceShipmentsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Shipments',
		});
		this.commerceSpecificationsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Specifications',
		});
		this.componentsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Components',
		});
		this.controlPanelButton = page.getByRole('tab', {
			name: 'Control Panel',
		});
		this.countriesManagementItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Countries Management',
		});
		this.customFieldsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Custom Fields',
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
		this.metricsItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Metrics',
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
		this.picklistsMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Picklists',
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
		this.rolesItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Roles',
		});
		this.samlAdminItem = page.getByRole('menuitem', {
			exact: true,
			name: 'SAML Admin',
		});
		this.searchItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Search',
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
		this.userGroupsItem = page.getByRole('menuitem', {
			exact: true,
			name: 'User Groups',
		});
		this.usersAndOrganizationsItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Users and Organizations',
		});
		this.virtualInstancesItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Virtual Instances',
		});
	}

	async goto(checkTabVisibility = true) {
		await this.homePage.goto();
		await this.homePage.openApplicationMenu();

		if (checkTabVisibility) {
			await expect(this.applicationsMenuTabButton).toBeVisible();
		}
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

	async goToDataSetManager(checkTabVisibility = true) {
		await this.goToControlPanel(checkTabVisibility);
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

	async goToComponents() {
		await this.goto();
		await this.controlPanelButton.click();
		await this.componentsMenuItem.click();
	}

	async goToCountriesManagement() {
		await this.goto();
		await this.controlPanelButton.click();
		await this.countriesManagementItem.click();
	}

	async goToCustomFields(forceReload = true) {
		if (forceReload) {
			await this.goto();
		}
		else {
			await this.homePage.openApplicationMenu();

			await expect(this.applicationsMenuTabButton).toBeVisible();
		}
		await this.controlPanelButton.click();
		await this.customFieldsMenuItem.click();
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

	async goToMetrics() {
		await this.goToApplicationsMenu();
		await this.metricsItem.click();
	}

	async goToObjects() {
		await this.goToControlPanel();
		await this.objectsMenuItem.click();
	}

	async goToPicklists() {
		await this.goToControlPanel();
		await this.picklistsMenuItem.click();
	}

	async goToSearch() {
		await this.goToControlPanel();
		await this.searchItem.click();
	}

	async goToServerAdministration() {
		await this.goToControlPanel();
		await this.serverAdministrationItem.click();
	}

	async goToSiteTemplates() {
		await this.goToControlPanel();
		await this.siteTemplatesButton.click();
	}

	async goToSites(forceReload = true) {
		if (forceReload) {
			await this.goto();
		}
		else {
			await this.homePage.openApplicationMenu();

			await expect(this.applicationsMenuTabButton).toBeVisible();
		}

		await this.controlPanelButton.click();
		await this.sitesItem.click();
	}

	async goToGlobalSite() {
		await this.goToSite('Global');
	}

	async goToSystemSettings() {
		await this.goToControlPanel();
		await this.systemSettingsItem.click();
	}

	async goToInstanceSettings(forceReload = true) {
		if (forceReload) {
			await this.goto();
		}
		else {
			await this.homePage.openApplicationMenu();

			await expect(this.applicationsMenuTabButton).toBeVisible();
		}

		await this.controlPanelButton.click();
		await this.instanceSettingsMenuItem.click();
	}

	async goToJobScheduler() {
		await this.goToControlPanel();
		await this.jobSchedulerMenuItem.click();
	}

	async goToCommerceChannels(checkTabVisibility = true) {
		await this.goToCommercePanel(checkTabVisibility);
		await this.commerceChannelsMenuItem.click();
	}

	async goToCommerceDiscounts() {
		await this.goToCommercePanel();
		await this.commerceDiscountsMenuItem.click();
	}

	async goToCommercePanel(checkTabVisibility = true) {
		await this.goto(checkTabVisibility);
		await this.commercePanelButton.click();
	}

	async goToCommerceOrders(checkTabVisibility = true) {
		await this.goToCommercePanel(checkTabVisibility);
		await this.commerceOrdersMenuItem.click();
	}

	async goToCommerceProductConfigurationLists(checkTabVisibility = true) {
		await this.goToCommercePanel(checkTabVisibility);
		await this.commerceProductConfigurationListsMenuItem.click();
	}

	async goToCommerceReturns(checkTabVisibility = true) {
		await this.goToCommercePanel(checkTabVisibility);
		await this.commerceReturnsMenuItem.click();
	}

	async goToCommerceShipments(checkTabVisibility = true) {
		await this.goToCommercePanel(checkTabVisibility);
		await this.commerceShipmentsMenuItem.click();
	}

	async goToCommerceSpecifications() {
		await this.goToCommercePanel();
		await this.commerceSpecificationsMenuItem.click();
	}

	async goToPayments(checkTabVisibility = true) {
		await this.goToCommercePanel(checkTabVisibility);
		await this.paymentsMenuItem.click();
	}

	async goToProducts(checkTabVisibility = true) {
		await this.goToCommercePanel(checkTabVisibility);
		await this.productsMenuItem.click();
	}

	async goToQueue() {
		await this.goToControlPanel();
		await this.queueMenuItem.click();
	}

	async goToSite(name: string = 'Liferay DXP', checkTabVisibility = true) {
		await this.goto(checkTabVisibility);
		await this.page.getByRole('link', {exact: true, name}).click();
	}

	async goToControlPanel(checkTabVisibility = true) {
		await this.goto(checkTabVisibility);
		await this.controlPanelButton.click();
	}

	async goToBlueprints() {
		await this.goToApplicationsMenu();
		await this.blueprintsItem.click();
	}

	async goToOauth2Administration() {
		await this.goToControlPanel();
		await this.oAuth2Administration.click();
	}

	async goToProcessBuilder() {
		await this.goToApplicationsMenu();
		await this.processBuilderItem.click();
	}

	async goToRoles() {
		await this.goto();
		await this.controlPanelButton.click();
		await this.rolesItem.click();
	}

	async goToSamlAdmin(forceReload = true) {
		if (forceReload) {
			await this.goto();
		}
		else {
			await this.homePage.openApplicationMenu();

			await expect(this.applicationsMenuTabButton).toBeVisible();
		}

		await this.controlPanelButton.click();
		await this.samlAdminItem.click();
	}

	async goToServiceAccounts() {
		await this.goto();
		await this.controlPanelButton.click();
		await this.serviceAccountsItem.click();
	}

	async goToUserGroups(forceReload = true) {
		if (forceReload) {
			await this.goto();
		}
		else {
			await this.homePage.openApplicationMenu();

			await expect(this.applicationsMenuTabButton).toBeVisible();
		}
		await this.controlPanelButton.click();
		await this.userGroupsItem.click();
	}

	async goToUsersAndOrganizations(forceReload = true) {
		if (forceReload) {
			await this.goto();
		}
		else {
			await this.homePage.openApplicationMenu();

			await expect(this.applicationsMenuTabButton).toBeVisible();
		}

		await this.controlPanelButton.click();
		await this.usersAndOrganizationsItem.click();
	}

	async goToVirtualInstances() {
		await this.goto();
		await this.controlPanelButton.click();
		await this.virtualInstancesItem.click();
	}
}

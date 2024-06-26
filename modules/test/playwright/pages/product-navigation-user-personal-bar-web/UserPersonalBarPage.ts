/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApplicationsMenuPage} from '../product-navigation-applications-menu/ApplicationsMenuPage';

export class UserPersonalBarPage {
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly editConfigurationSubmitButton: Locator;
	readonly notificationBadge: Locator;
	readonly page: Page;
	readonly processBuilderConfigurationTab: Locator;
	readonly productsMenuItem: Locator;
	readonly searchInput: Locator;
	readonly searchResultText: Locator;
	readonly searchSubmit: Locator;
	readonly showNotificationBadgeInPersonalMenuLabel: Locator;
	readonly submitForWorkflowButton: Locator;
	readonly usersSetting: Locator;
	readonly workflowDefinitionLinkCancelButton: Locator;
	readonly workflowDefinitionLinkEditButton: Locator;
	readonly workflowDefinitionLinkSaveButton: Locator;
	readonly workflowDefinitionLinkSelectButton: Locator;

	constructor(page: Page) {
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.editConfigurationSubmitButton = page.getByTestId(
			'submitConfiguration'
		);
		this.notificationBadge = page.getByLabel('New Notification');
		this.page = page;
		this.processBuilderConfigurationTab = page.getByRole('link', {
			name: 'Configuration',
		});
		this.productsMenuItem = page.getByRole('menuitem', {
			name: 'Products',
		});
		this.searchInput = page
			.locator(
				'#_com_liferay_portal_workflow_web_portlet_ControlPanelWorkflowPortlet_fm_search'
			)
			.getByPlaceholder(/^Search/);
		this.searchResultText = page.getByTestId('searchResultText');
		this.searchSubmit = page
			.locator(
				'#_com_liferay_portal_workflow_web_portlet_ControlPanelWorkflowPortlet_fm_search'
			)
			.getByLabel('Search for', {exact: true});
		this.showNotificationBadgeInPersonalMenuLabel = page
			.getByTestId('showNotificationBadgeInPersonalMenu')
			.getByLabel('Show Notification Badge in Personal Menu', {
				exact: true,
			});
		this.submitForWorkflowButton = page.getByRole('link', {
			name: 'Submit for Workflow',
		});
		this.usersSetting = page.getByRole('link', {
			name: 'Users',
		});
		this.workflowDefinitionLinkCancelButton = page
			.getByTestId('actionProduct')
			.getByRole('button', {name: 'Cancel'});
		this.workflowDefinitionLinkEditButton = page
			.getByTestId('actionProduct')
			.getByRole('button', {name: 'Edit'});
		this.workflowDefinitionLinkSaveButton = page
			.getByTestId('actionProduct')
			.getByRole('button', {name: 'Save'});
		this.workflowDefinitionLinkSelectButton =
			page.getByTestId('selectProduct');
	}

	async disableNotificationBadgeInPersonalMenu() {
		await this.applicationsMenuPage.goToInstanceSettings();
		await this.usersSetting.click();
		await this.showNotificationBadgeInPersonalMenuLabel.uncheck();
		await Promise.all([
			this.editConfigurationSubmitButton.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'p_p_id=com_liferay_configuration_admin_web_portlet_InstanceSettingsPortlet'
						)
			),
		]);
	}

	async disableSingleApproverWorkflowProduct() {
		await this.goToProcessBuilderConfigurationTab();
		await this.searchInput.fill('Product');
		await this.searchSubmit.click();
		await this.searchResultText.waitFor({
			state: 'attached',
		});
		await this.workflowDefinitionLinkEditButton.click();
		await this.workflowDefinitionLinkSelectButton.selectOption(
			'No Workflow'
		);
		await this.workflowDefinitionLinkSaveButton.click();
	}

	async enableNotificationBadgeInPersonalMenu() {
		await this.applicationsMenuPage.goToInstanceSettings();
		await this.usersSetting.click();
		await this.showNotificationBadgeInPersonalMenuLabel.check();
		await Promise.all([
			this.editConfigurationSubmitButton.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'p_p_id=com_liferay_configuration_admin_web_portlet_InstanceSettingsPortlet'
						)
			),
		]);
	}

	async enableSingleApproverWorkflowProduct() {
		await this.goToProcessBuilderConfigurationTab();
		await this.searchInput.fill('Product');
		await this.searchSubmit.click();
		await this.searchResultText.waitFor({
			state: 'attached',
		});
		await this.workflowDefinitionLinkEditButton.click();
		await this.workflowDefinitionLinkSelectButton.selectOption(
			'Single Approver'
		);
		await this.workflowDefinitionLinkSaveButton.click();
	}

	async goToProcessBuilderConfigurationTab() {
		await this.applicationsMenuPage.goToProcessBuilder();
		await Promise.all([
			this.processBuilderConfigurationTab.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'_com_liferay_portal_workflow_web_portlet_ControlPanelWorkflowPortlet_tab=configuration'
						)
			),
		]);
	}
}

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
		this.notificationBadge = page.getByTestId('notificationsCount');
		this.page = page;
		this.processBuilderConfigurationTab = page.getByRole('link', {
			name: 'Configuration',
		});
		this.productsMenuItem = page.getByRole('menuitem', {
			name: 'Products',
		});
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
		await this.editConfigurationSubmitButton.click();
	}

	async disableSingleApproverWorkflowProduct() {
		await this.goToProcessBuilderConfigurationTab();
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
		await this.editConfigurationSubmitButton.click();
	}

	async enableSingleApproverWorkflowProduct() {
		await this.goToProcessBuilderConfigurationTab();
		await this.workflowDefinitionLinkEditButton.click();
		await this.workflowDefinitionLinkSelectButton.selectOption(
			'Single Approver'
		);
		await this.workflowDefinitionLinkSaveButton.click();
	}

	async goto() {
		await this.page.goto('/');
	}

	async goToProcessBuilderConfigurationTab() {
		await this.applicationsMenuPage.goToProcessBuilder();
		await this.processBuilderConfigurationTab.click();
	}
}

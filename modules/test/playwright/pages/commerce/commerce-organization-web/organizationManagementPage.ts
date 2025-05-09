/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {getRandomInt} from '../../../utils/getRandomInt';

export class OrganizationManagementPage {
	readonly accountNode: (accountName: string) => Locator;
	readonly addAccountNode: Locator;
	readonly addAccountModalName: Locator;
	readonly addAccountModalNewType: Locator;
	readonly addAccountModalSearch: Locator;
	readonly addAccountModalSearchOption: (accountName: string) => Locator;
	readonly addAccountModalSave: Locator;
	readonly addNode: Locator;
	readonly addOrganizationModalName: Locator;
	readonly addOrganizationModalSave: Locator;
	readonly addOrganizationNode: Locator;
	readonly addUserModalRoles: Locator;
	readonly addUserModalSave: Locator;
	readonly addUserModalUsersEmails: Locator;
	readonly addUserNode: Locator;
	readonly chart: Locator;
	readonly collapseAllButton: Locator;
	readonly deleteItem: Locator;
	readonly discoveredAccountNode: (accountName: string) => Locator;
	readonly discoveredOrganizationNode: (organizationName: string) => Locator;
	readonly editItem: Locator;
	readonly infoText: (text: string) => Locator;
	readonly menuButton: (container: Locator) => Locator;
	readonly noRootOrganizationsMessage: Locator;
	readonly organizationNode: (organizationName: string) => Locator;
	readonly page: Page;
	readonly removeItem: Locator;
	readonly searchedEntry: (name: string) => Locator;
	readonly searchInput: Locator;
	readonly selectImage: Locator;
	readonly selectImageDoneButton: Locator;
	readonly sidebarAccountIDField: Locator;
	readonly sidebarAccountNameField: Locator;
	readonly sidebarChangeImageButton: Locator;
	readonly sidebarDescriptionField: Locator;
	readonly sidebarEmailAddressField: Locator;
	readonly sidebarExternalReferenceCodeField: Locator;
	readonly sidebarFirstNameField: Locator;
	readonly sidebarJobTitleField: Locator;
	readonly sidebarLanguageField: Locator;
	readonly sidebarLastNameField: Locator;
	readonly sidebarMiddleNameField: Locator;
	readonly sidebarMoreAction: Locator;
	readonly sidebarPrefixField: Locator;
	readonly sidebarSaveButton: Locator;
	readonly sidebarScreenNameField: Locator;
	readonly sidebarSuffixField: Locator;
	readonly sidebarTaxIDField: Locator;
	readonly sidebarTitle: Locator;
	readonly sidebarTypeField: Locator;
	readonly sidebarUserIDField: Locator;
	readonly sidebarValue: (label: string) => Locator;
	readonly userNode: (userName: string) => Locator;
	readonly viewItem: Locator;

	constructor(page: Page) {
		this.addAccountModalName = page.getByLabel('Name');
		this.addAccountModalNewType = page.getByLabel('Create New Account');
		this.addAccountModalSearch = page.locator('#searchAccountInput');
		this.addAccountModalSearchOption = (accountName) => {
			return page.getByRole('option', {exact: true, name: accountName});
		};
		this.addAccountModalSave = page.getByRole('button', {
			exact: true,
			name: 'Save',
		});
		this.addOrganizationModalName = page.getByLabel('Name');
		this.addOrganizationModalSave = page.getByRole('button', {
			exact: true,
			name: 'Save',
		});
		this.addUserModalUsersEmails = page.getByPlaceholder("Users' Emails");
		this.addUserModalSave = page.getByRole('button', {
			exact: true,
			name: 'Save',
		});
		this.addUserModalRoles = page.getByLabel('Roles');
		this.chart = page.locator('svg.svg-chart');
		this.collapseAllButton = page.getByRole('button', {
			exact: true,
			name: 'Collapse All',
		});
		this.deleteItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Delete',
		});
		this.editItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Edit',
		});
		this.infoText = (text) => {
			return page.getByText(text);
		};
		this.noRootOrganizationsMessage = page.getByText(
			'No root organizations were found'
		);
		this.menuButton = (container) => {
			return container.locator('.node-menu-wrapper');
		};
		this.page = page;
		this.removeItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Remove',
		});
		this.selectImage = page
			.frameLocator('iframe[title="Upload Image"]')
			.getByLabel('Select Image');
		this.selectImageDoneButton = page
			.frameLocator('iframe[title="Upload Image"]')
			.getByRole('button', {name: 'Done'});
		this.sidebarAccountIDField = page.getByLabel('Account ID');
		this.sidebarAccountNameField = page.getByLabel('Account Name');
		this.sidebarChangeImageButton = page.getByLabel('Change Image');
		this.sidebarDescriptionField = page.getByLabel('Description');
		this.sidebarEmailAddressField = page.getByLabel('Email Address');
		this.sidebarExternalReferenceCodeField = page.getByLabel(
			'External Reference Code'
		);
		this.sidebarFirstNameField = page.getByLabel('First Name');
		this.sidebarJobTitleField = page.getByLabel('Job Title');
		this.sidebarLanguageField = page.getByLabel('Language');
		this.sidebarLastNameField = page.getByLabel('Last Name');
		this.sidebarMiddleNameField = page.getByLabel('Middle Name');
		this.sidebarMoreAction = page.getByLabel('More Actions');
		this.sidebarPrefixField = page.getByLabel('Prefi');
		this.sidebarSaveButton = page.getByRole('button', {name: 'Save'});
		this.sidebarScreenNameField = page.getByLabel('Screen Name');
		this.sidebarSuffixField = page.getByLabel('Suffix');
		this.sidebarTaxIDField = page.getByLabel('Tax ID');
		this.sidebarTitle = page.locator('.sidebar-header .component-title');
		this.sidebarTypeField = page.getByLabel('Type');
		this.sidebarUserIDField = page.getByLabel('User ID');
		this.sidebarValue = (label) =>
			page
				.locator('.sidebar-dt')
				.filter({hasText: label})
				.locator('..')
				.locator('.sidebar-dd');
		this.viewItem = page.getByRole('menuitem', {
			exact: true,
			name: 'View',
		});

		this.accountNode = (accountName) => {
			return this.chart
				.locator('g.chart-item-account')
				.filter({hasText: accountName});
		};
		this.addAccountNode = this.chart
			.locator('g.actions-wrapper.menu-open')
			.locator('g.add-action-wrapper.account');
		this.addNode = this.chart.locator('g.chart-item-add');
		this.addOrganizationNode = this.chart
			.locator('g.actions-wrapper.menu-open')
			.locator('g.add-action-wrapper.organization');
		this.addUserNode = this.chart
			.locator('g.actions-wrapper.menu-open')
			.locator('g.add-action-wrapper.user');
		this.discoveredAccountNode = (accountName) => {
			return this.chart
				.locator('g.chart-item-account.discovered')
				.filter({hasText: accountName});
		};
		this.discoveredOrganizationNode = (organizationName) => {
			return this.chart
				.locator('g.chart-item-organization.discovered')
				.filter({hasText: organizationName});
		};
		this.organizationNode = (organizationName) => {
			return this.chart
				.locator('g.chart-item-organization')
				.filter({hasText: organizationName});
		};
		this.searchedEntry = (name) => {
			return page.getByRole('menuitem', {name: new RegExp(name)});
		};
		this.searchInput = page.getByPlaceholder('Type Here');
		this.userNode = (userName) => {
			return this.chart
				.locator('g.chart-item-user')
				.filter({hasText: userName});
		};
	}

	async addAccountToOrganization({
		accountName,
		isNew = false,
	}: {
		accountName: string;
		isNew: boolean;
	}) {
		await this.addNode.click();
		await this.addAccountNode.click();
		if (isNew) {
			await this.addAccountModalNewType.click();
			await this.addAccountModalName.click();
			await this.addAccountModalName.fill(accountName);
		}
		else {
			await this.addAccountModalSearch.click();
			await this.addAccountModalSearch.fill(accountName);
			await this.addAccountModalSearchOption(accountName).click();
		}
		await this.addAccountModalSave.click();
	}

	async addOrganizationToOrganization(
		{
			organizationName,
		}: {
			organizationName: string;
		} = {organizationName: `Organization ${getRandomInt()}`}
	) {
		await this.addNode.click();
		await this.addOrganizationNode.click();
		await this.addOrganizationModalName.click();
		await this.addOrganizationModalName.fill(organizationName);
		await this.addOrganizationModalSave.click();
	}

	async addUserToOrganization(
		{email, role}: {email?: string; role?: string} = {
			email: 'test@liferay.com',
			role: 'Organization Administrator',
		}
	) {
		await this.addNode.click();
		await this.addUserNode.click();
		await this.addUserModalUsersEmails.click();
		await this.addUserModalUsersEmails.fill(email);
		await this.addUserModalRoles.selectOption({label: role});
		await this.addUserModalSave.click();
	}
}

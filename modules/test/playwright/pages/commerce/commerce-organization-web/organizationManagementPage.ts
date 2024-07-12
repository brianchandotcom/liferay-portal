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
	readonly menuButton: (container: Locator) => Locator;
	readonly organizationNode: (organizationName: string) => Locator;
	readonly page: Page;
	readonly removeItem: Locator;
	readonly userNode: (userName: string) => Locator;

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
		this.menuButton = (container) => {
			return container.locator('.node-menu-wrapper');
		};
		this.page = page;
		this.removeItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Remove',
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
		this.organizationNode = (organizationName) => {
			return this.chart
				.locator('g.chart-item-organization')
				.filter({hasText: organizationName});
		};
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

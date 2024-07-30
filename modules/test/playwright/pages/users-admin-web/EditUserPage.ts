/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {searchTableRowByValue} from './UsersAndOrganizationsPage';

export class EditUserPage {
	readonly confirmButton: Locator;
	readonly customField: (fieldName: string) => Promise<Locator>;
	readonly emailAddressInput: Locator;
	readonly generateWebDAVPasswordButton: Locator;
	readonly membershipsAccountsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly membershipsAccountsTable: Locator;
	readonly membershipsLink: Locator;
	readonly membershipsUserGroupsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly membershipsUserGroupsTable: Locator;
	readonly organizationsLink: Locator;
	readonly page: Page;
	readonly passwordConfirmationFrame: FrameLocator;
	readonly passwordLink: Locator;
	readonly rolesLink: Locator;
	readonly saveButton: Locator;
	readonly screenNameInput: Locator;
	readonly selectOrganizationButton: Locator;
	readonly selectOrganizationRolesButton: Locator;
	readonly selectOrganizationRolesFrame: FrameLocator;
	readonly selectOrganizationRolesTable: Locator;
	readonly selectOrganizationRolesTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly selectOrganizationRolesSearchBar: Locator;
	readonly selectOrganizationRolesSearchBarButton: Locator;
	readonly selectOrganizationsTable: Locator;
	readonly selectOrganizationsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly selectSitesButton: Locator;
	readonly selectSiteLink: (siteName: string) => Promise<Locator>;
	readonly webDAVPasswordLabel: Locator;
	readonly yourPasswordInput: Locator;

	constructor(page: Page) {
		this.customField = async (fieldName: string) => {
			await page.getByText('Custom Fields').waitFor({timeout: 15 * 1000});

			const customField = await page.getByText(fieldName);

			if (customField.isVisible()) {
				return customField;
			}

			throw new Error(`Cannot locate Custom Field ${fieldName}`);
		};
		this.emailAddressInput = page.getByLabel('Email Address');
		this.generateWebDAVPasswordButton = page.getByTestId(
			'generateWebDAVPasswordButton'
		);
		this.membershipsAccountsTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean
		) => {
			return await searchTableRowByValue(
				this.membershipsAccountsTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.membershipsAccountsTable = page.locator(
			'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_accountEntriesSearchContainer'
		);
		this.membershipsUserGroupsTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean
		) => {
			return await searchTableRowByValue(
				this.membershipsUserGroupsTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.membershipsUserGroupsTable = page.locator(
			'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_userGroupsSearchContainer'
		);
		this.membershipsLink = page.getByRole('link', {
			exact: true,
			name: 'Memberships',
		});
		this.organizationsLink = page.getByRole('link', {
			exact: true,
			name: 'Organizations',
		});
		this.page = page;
		this.passwordConfirmationFrame = page.frameLocator(
			'iframe[title="Confirm Password"]'
		);
		this.passwordLink = page.getByRole('link', {
			exact: true,
			name: 'Password',
		});
		this.rolesLink = page.getByRole('link', {
			exact: true,
			name: 'Roles',
		});
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.screenNameInput = page.getByLabel('Screen Name');

		this.selectOrganizationButton = page.locator(
			'#_com_liferay_users_admin_web_portlet_MyOrganizationsPortlet_selectOrganizationLink'
		);
		this.selectOrganizationRolesButton = page.locator(
			'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_selectOrganizationRoleLink'
		);
		this.selectOrganizationRolesFrame = page.frameLocator(
			'iframe[title="Select Organization Role"]'
		);
		this.selectOrganizationRolesTable =
			this.selectOrganizationRolesFrame.locator(
				'#_com_liferay_roles_admin_web_portlet_RolesAdminPortlet_organizationsSearchContainer'
			);
		this.selectOrganizationRolesTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean
		) => {
			return await searchTableRowByValue(
				this.selectOrganizationRolesTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.selectOrganizationRolesSearchBar =
			this.selectOrganizationRolesFrame.getByPlaceholder('Search for');
		this.selectOrganizationRolesSearchBarButton =
			this.selectOrganizationRolesFrame.getByRole('button', {
				name: 'Search for',
			});
		this.selectOrganizationsTable = page
			.frameLocator(
				'#_com_liferay_users_admin_web_portlet_MyOrganizationsPortlet_selectOrganization_iframe_'
			)
			.locator(
				'#_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_entriesSearchContainer'
			);
		this.selectOrganizationsTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean
		) => {
			return await searchTableRowByValue(
				this.selectOrganizationsTable,
				colPosition,
				value,
				strictEqual
			);
		};
		this.selectSitesButton = page.getByRole('button', {
			exact: true,
			name: 'Select Sites',
		});
		this.selectSiteLink = async (siteName: string) => {
			return page
				.frameLocator('iframe[title="Select Site"]')
				.getByRole('link', {exact: true, name: siteName});
		};
		this.webDAVPasswordLabel = page.locator(
			'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_webDAVPassword'
		);
		this.confirmButton = this.passwordConfirmationFrame.getByRole(
			'button',
			{name: 'Confirm'}
		);
		this.yourPasswordInput =
			this.passwordConfirmationFrame.getByLabel('Your Password');
	}

	async selectUserMembershipSite(site: string) {
		await this.membershipsLink.click();
		await this.selectSitesButton.click();
		(await this.selectSiteLink(site)).click();
		await this.saveButton.waitFor({state: 'visible'});
		await this.saveButton.click();
	}
}

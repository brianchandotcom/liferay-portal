/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {searchTableRowByValue} from './UsersAndOrganizationsPage';

export class EditUserPage {
	readonly generateWebDAVPasswordButton: Locator;
	readonly membershipsAccountsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly membershipsAccountsTable: Locator;
	readonly membershipsLink: Locator;
	readonly organizationsLink: Locator;
	readonly page: Page;
	readonly passwordLink: Locator;
	readonly selectOrganizationButton: Locator;
	readonly selectOrganizationsTable: Locator;
	readonly selectOrganizationsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;
	readonly webDAVPasswordLabel: Locator;

	constructor(page: Page) {
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
		this.membershipsLink = page.getByRole('link', {
			exact: true,
			name: 'Memberships',
		});
		this.organizationsLink = page.getByRole('link', {
			exact: true,
			name: 'Organizations',
		});
		this.passwordLink = page.getByRole('link', {
			exact: true,
			name: 'Password',
		});
		this.page = page;
		this.webDAVPasswordLabel = page.locator(
			'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_webDAVPassword'
		);
		this.selectOrganizationButton = page.locator(
			'#_com_liferay_users_admin_web_portlet_MyOrganizationsPortlet_selectOrganizationLink'
		);
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
	}
}

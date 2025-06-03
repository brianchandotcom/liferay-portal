/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../utils/portletUrls';
import {DataTablePage} from '../../account-admin-web/DataTablePage';

export class SiteMembershipsPage {
	readonly assignRolesButton: Locator;
	readonly assignRolesDoneButton: Locator;
	readonly assignRolesIFrame: FrameLocator;
	readonly assignRolesTable: DataTablePage;
	readonly assignUserGroupIFrame: FrameLocator;
	readonly assignUserGroupIFrameTitle: Locator;
	readonly assignUserGroupTable: DataTablePage;
	readonly newUserGroupButton: Locator;
	readonly noPermissionMessage: Locator;
	readonly noUserGroupMessage: Locator;
	readonly page: Page;
	readonly userGroupsLink: Locator;
	readonly userGroupSelectButton: (userGroupName: string) => Promise<Locator>;
	readonly userGroupSelectDoneButton: Locator;
	readonly userGroupsTable: DataTablePage;
	readonly usersTable: DataTablePage;

	constructor(page: Page) {
		this.assignRolesButton = page
			.getByRole('button', {exact: true, name: 'Assign Roles'})
			.or(
				page.getByRole('menuitem', {exact: true, name: 'Assign Roles'})
			);
		this.assignRolesDoneButton = page.getByRole('button', {
			exact: true,
			name: 'Done',
		});
		this.assignRolesIFrame = page.frameLocator(
			'iframe[title="Assign Roles"]'
		);
		this.assignRolesTable = new DataTablePage(
			this.assignRolesIFrame,
			this.assignRolesIFrame.locator(
				'#_com_liferay_site_memberships_web_portlet_SiteMembershipsPortlet_userGroupGroupRoleRoleSearchContainer'
			)
		);
		this.assignUserGroupIFrame = page.frameLocator(
			'iframe[title="Assign User Groups to This Site"]'
		);
		this.assignUserGroupIFrameTitle = page.getByText(
			'Assign User Groups to This Site'
		);
		this.assignUserGroupTable = new DataTablePage(
			this.assignUserGroupIFrame,
			this.assignUserGroupIFrame.locator(
				'#_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_entriesSearchContainer'
			)
		);
		this.newUserGroupButton = page.getByRole('button', {name: 'Add'});
		this.noPermissionMessage = page
			.getByText(
				'You do not have the roles required to access this portlet.'
			)
			.first();
		this.noUserGroupMessage = page.getByText(
			'No user group was found that is a member of this site'
		);
		this.page = page;
		this.userGroupsLink = page.getByRole('link', {
			name: 'User Groups',
		});
		this.userGroupSelectButton = async (userGroupName: string) => {
			return this.assignUserGroupIFrame.getByLabel(
				`Select ${userGroupName}`
			);
		};
		this.userGroupSelectDoneButton = this.page.getByRole('button', {
			name: 'Done',
		});
		this.userGroupsTable = new DataTablePage(
			page,
			page.locator(
				'#_com_liferay_site_memberships_web_portlet_SiteMembershipsPortlet_userGroups'
			)
		);
		this.usersTable = new DataTablePage(
			page,
			page.locator(
				'#_com_liferay_site_memberships_web_portlet_SiteMembershipsPortlet_users'
			)
		);
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.siteMemberships}`
		);
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {UsersAndOrganizationsPage} from './UsersAndOrganizationsPage';

export class EditOrganizationPage {
	readonly createSiteToggle: Locator;
	readonly contactLink: Locator;
	readonly editOrgLaborIconMenu: Locator;
	readonly manageSiteLink: Locator;
	readonly openingHoursLink: Locator;
	readonly organizationEditMenuItem: Locator;
	readonly organizationSiteLink: Locator;
	readonly organizationSiteSaveButton: Locator;
	readonly orgLaborListTypeSelectedValue: Locator;
	readonly page: Page;
	readonly usersAndOrganizationsPage: UsersAndOrganizationsPage;

	constructor(page: Page) {
		this.createSiteToggle = page.getByLabel('Create Site');
		this.contactLink = page.getByRole('link', {name: 'Contact'});
		this.editOrgLaborIconMenu = page.getByTestId('editOrgLaborIconMenu');
		this.manageSiteLink = page.getByRole('link', {name: 'Manage Site'});
		this.openingHoursLink = page.getByRole('link', {name: 'Opening Hours'});
		this.organizationEditMenuItem = page.getByRole('menuitem', {
			name: 'Edit',
		});
		this.organizationSiteLink = page.getByRole('link', {
			name: 'Organization Site',
		});
		this.organizationSiteSaveButton = page.getByRole('button', {
			name: 'Save',
		});
		this.orgLaborListTypeSelectedValue = page
			.locator(
				'#_com_liferay_users_admin_web_portlet_UsersAdminPortlet_orgLaborListTypeId'
			)
			.locator('option[selected=""]');
		this.page = page;
		this.usersAndOrganizationsPage = new UsersAndOrganizationsPage(page);
	}

	async gotoOrganizationEditOpeningHoursTab(organizationName: string) {
		await (
			await this.usersAndOrganizationsPage.organizationActionsMenu(
				organizationName
			)
		).click();
		await this.organizationEditMenuItem.click();
		await this.contactLink.click();
		await this.openingHoursLink.click();
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.organizationEditMenuItem,
			trigger: this.editOrgLaborIconMenu,
		});
	}
}

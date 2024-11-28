/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ProductMenuPage} from '../../../pages/product-navigation-control-menu-web/ProductMenuPage';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';

export class MembershipsPage {
	readonly page: Page;
	readonly productMenuPage: ProductMenuPage;

	constructor(page: Page) {
		this.page = page;
		this.productMenuPage = new ProductMenuPage(page);
	}

	async assignAllRolesToUser(userName: String) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: 'Assign Roles',
			}),
			timeout: 500,
			trigger: this.page
				.locator(
					'[id="_com_liferay_site_memberships_web_portlet_SiteMembershipsPortlet_users_' +
						userName +
						'"]'
				)
				.getByLabel('More actions'),
		});

		await this.page.waitForTimeout(500);

		await this.page
			.frameLocator('iframe[title="Assign Roles"]')
			.getByLabel('Select All Items on the Page')
			.check();

		await this.page.getByRole('button', {name: 'Done'}).click();
	}

	async assignAllUsersSiteMembership() {
		await this.page.getByRole('button', {name: 'Add'}).click();

		await this.page.waitForTimeout(500);

		await this.page
			.frameLocator('iframe[title="Assign Users to This Site"]')
			.getByLabel('Select All Items on the Page')
			.check();

		await this.page.getByRole('button', {name: 'Done'}).click();
	}

	async assignSiteAdministratorRole() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('button', {name: 'Assign Roles'}),
			timeout: 500,
			trigger: this.page.getByLabel('Select All Items on the Page'),
		});

		await this.page
			.frameLocator('iframe[title="Assign Roles"]')
			.locator(
				'[id="_com_liferay_site_memberships_web_portlet_SiteMembershipsPortlet_roles_1"] svg'
			)
			.click();

		await this.page.getByRole('button', {name: 'Done'}).click();
	}

	async filterBySiteAdministratorRole() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Roles'}),
			timeout: 500,
			trigger: this.page.getByLabel('Filter'),
		});

		await this.page
			.frameLocator('iframe[title="Select Role"]')
			.getByText('Site Administrator')
			.click();
	}

	async goto() {
		await this.productMenuPage.openProductMenuIfClosed();
		await this.productMenuPage.goToMemberships();
	}

	async removeSiteAdministratorRole() {
		this.page.once('dialog', (dialog) => {
			dialog.accept();
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('button', {
				name: 'Remove Role: Site Administrator',
			}),
			timeout: 500,
			trigger: this.page.getByLabel('Select All Items on the Page'),
		});
	}

	async unassignAllRolesFromUser(userName: String) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: 'Unassign Roles',
			}),
			timeout: 500,
			trigger: this.page
				.locator(
					'[id="_com_liferay_site_memberships_web_portlet_SiteMembershipsPortlet_users_' +
						userName +
						'"]'
				)
				.getByLabel('More actions'),
		});

		await this.page.waitForTimeout(500);

		await this.page
			.frameLocator('iframe[title="Unassign Roles"]')
			.getByLabel('Select All Items on the Page')
			.check();

		await this.page.getByRole('button', {name: 'Done'}).click();
	}
}

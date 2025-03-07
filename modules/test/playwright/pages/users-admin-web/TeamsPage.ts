/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataTablePage} from '../account-admin-web/DataTablePage';
import {ProductMenuPage} from '../product-navigation-control-menu-web/ProductMenuPage';

export class TeamsPage {
	readonly editLink: Locator;
	readonly nameInput: Locator;
	readonly newTeamButton: Locator;
	readonly page: Page;
	readonly productMenuPage: ProductMenuPage;
	readonly saveButton: Locator;
	readonly teamsTable: DataTablePage;

	constructor(page: Page) {
		this.editLink = page.getByRole('link', {name: 'Edit'});
		this.nameInput = page.getByPlaceholder('Name');
		this.newTeamButton = page.getByRole('link', {name: 'Add Team'});
		this.page = page;
		this.productMenuPage = new ProductMenuPage(page);
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.teamsTable = new DataTablePage(
			page,
			page.locator(
				'#_com_liferay_site_teams_web_portlet_SiteTeamsPortlet_teamsSearchContainer'
			)
		);
	}

	async goTo(siteUrl?: string) {
		await this.productMenuPage.goToTeams(siteUrl);
	}
}

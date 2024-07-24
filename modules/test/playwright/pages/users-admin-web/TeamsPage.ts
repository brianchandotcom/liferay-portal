/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ProductMenuPage} from '../product-navigation-control-menu-web/ProductMenuPage';
import {searchTableRowByValue} from './UsersAndOrganizationsPage';

export class TeamsPage {
	readonly nameInput: Locator;
	readonly newTeamButton: Locator;
	readonly page: Page;
	readonly productMenuPage: ProductMenuPage;
	readonly saveButton: Locator;
	readonly teamsTable: Locator;
	readonly teamsTableRow: (
		colPosition: number,
		value: string,
		strictEqual?: boolean
	) => Promise<{column: Locator; row: Locator}>;

	constructor(page: Page) {
		this.nameInput = page.getByPlaceholder('Name');
		this.newTeamButton = page.getByRole('link', {name: 'Add Team'});
		this.page = page;
		this.productMenuPage = new ProductMenuPage(page);
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.teamsTable = page.locator(
			'#_com_liferay_site_teams_web_portlet_SiteTeamsPortlet_teamsSearchContainer'
		);
		this.teamsTableRow = async (
			colPosition: number,
			value: string,
			strictEqual: boolean
		) => {
			return await searchTableRowByValue(
				this.teamsTable,
				colPosition,
				value,
				strictEqual
			);
		};
	}

	async goTo(siteUrl?: string) {
		await this.productMenuPage.goToTeams(siteUrl);
	}
}

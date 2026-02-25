/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
import {DataTablePage} from '../account-admin-web/DataTablePage';
import {ProductMenuPage} from '../product-navigation-control-menu-web/ProductMenuPage';

export class TeamsPage {
	readonly backButton: Locator;
	readonly backTooltip: Locator;
	readonly deleteButton: Locator;
	readonly descriptionInput: Locator;
	readonly editButton: Locator;
	readonly editLink: Locator;
	readonly nameInput: Locator;
	readonly newTeamButton: Locator;
	readonly page: Page;
	readonly productMenuPage: ProductMenuPage;
	readonly saveButton: Locator;
	readonly teamsTable: DataTablePage;
	readonly userGroupTab: Locator;
	readonly usersTab: Locator;

	constructor(page: Page) {
		this.backButton = page.getByRole('link', {name: 'Go to Teams'});
		this.backTooltip = page.getByRole('tooltip').getByText('Go to Teams');
		this.deleteButton = page
			.getByRole('button', {name: 'Delete'})
			.or(page.getByRole('link', {name: 'Delete'}));
		this.descriptionInput = page.getByPlaceholder('Description');
		this.editButton = page.getByRole('link', {name: 'Edit'});
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
		this.userGroupTab = page.getByRole('link', {name: 'User Groups'});
		this.usersTab = page.getByRole('link', {name: 'Users'});
	}

	async goTo(siteUrl?: string) {
		await this.productMenuPage.goToTeams(siteUrl);
	}

	async newTeam({
		teamDescription = '',
		teamName,
	}: {
		teamDescription?: string;
		teamName: string;
	}) {
		await this.nameInput.fill(teamName);
		await this.descriptionInput.fill(teamDescription);
		await this.saveButton.click();

		await waitForAlert(this.page);
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../utils/portletUrls';
import {DataTablePage} from '../../account-admin-web/DataTablePage';

export class SitesAdminPage {
	readonly componentTitle: Locator;
	readonly deleteModalButton: Locator;
	readonly deleteSiteMenuItem: Locator;
	readonly infoPanelButton: Locator;
	readonly noSitesMessage: Locator;
	readonly page: Page;
	readonly searchSiteButton: Locator;
	readonly searchSiteInput: Locator;
	readonly sitesTable: DataTablePage;

	constructor(page: Page) {
		this.componentTitle = page.locator(
			'.info-panel-content .component-title'
		);
		this.deleteModalButton = page
			.locator('.modal-footer')
			.getByRole('button', {name: 'Delete'});
		this.deleteSiteMenuItem = page.getByRole('menuitem', {name: 'Delete'});
		this.infoPanelButton = page.getByTitle('Toggle Info Panel');
		this.noSitesMessage = page.getByText('No sites were found.');
		this.page = page;
		this.searchSiteButton = this.page.getByLabel('Search for', {
			exact: true,
		});
		this.searchSiteInput = this.page.getByPlaceholder('Search for');
		this.sitesTable = new DataTablePage(
			page,
			page.locator(
				'#_com_liferay_site_admin_web_portlet_SiteAdminPortlet_sitesSearchContainer'
			)
		);
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.sites}`
		);
	}

	async searchSite(keywords: string) {
		await this.searchSiteInput.click();
		await this.searchSiteInput.clear();
		await this.searchSiteInput.fill(keywords);

		await this.searchSiteButton.click();

		await this.page.getByText('Search Results').waitFor();
	}
}

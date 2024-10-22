/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';

export class ProductMenuPage {
	readonly closeProductMenuButton: Locator;
	readonly configurationButton: Locator;
	readonly contentAndDataButton: Locator;
	readonly exportButton: Locator;
	readonly formsButton: Locator;
	readonly importButton: Locator;
	readonly membershipsButton: Locator;
	readonly openProductMenuButton: Locator;
	readonly page: Page;
	readonly pagesButton: Locator;
	readonly peopleButton: Locator;
	readonly productMenuHeader: Locator;
	readonly publishingButton: Locator;
	readonly siteBuilderButton: Locator;
	readonly siteSettingsButton: Locator;
	readonly stagingMenuItem: Locator;
	readonly webContentButton: Locator;

	constructor(page: Page) {
		this.configurationButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Configuration',
		});
		this.contentAndDataButton = page.getByRole('menuitem', {
			name: 'Content & Data',
		});
		this.exportButton = page.getByRole('menuitem', {
			name: 'Export',
		});
		this.formsButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Forms',
		});
		this.importButton = page.getByRole('menuitem', {
			name: 'Import',
		});
		this.membershipsButton = page.getByRole('menuitem', {
			name: 'Memberships',
		});
		this.page = page;
		this.pagesButton = page.getByRole('menuitem', {name: 'Pages'});
		this.peopleButton = page.getByRole('menuitem', {name: 'People'});
		this.openProductMenuButton = page.getByLabel('Open Product Menu');
		this.closeProductMenuButton = page.getByLabel('Close Product Menu');
		this.productMenuHeader = page.locator(
			'[id="_com_liferay_product_navigation_product_menu_web_portlet_ProductMenuPortlet_site_administrationHeading"] div'
		);
		this.publishingButton = page.getByRole('menuitem', {
			name: 'Publishing',
		});
		this.siteBuilderButton = page.getByRole('menuitem', {
			name: 'Site Builder',
		});
		this.siteSettingsButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Site Settings',
		});
		this.stagingMenuItem = page.getByRole('menuitem', {
			name: 'Staging',
		});
		this.webContentButton = page.getByRole('menuitem', {
			name: 'Web Content',
		});
	}

	async checkIfAdecuateProductMenu(templateName: string) {
		await this.productMenuHeader
			.filter({hasText: templateName})
			.nth(2)
			.isVisible();
	}

	async clickSpecificPage(pageName: string) {
		await this.pagesButton.click();
		await this.page.getByLabel(pageName, {exact: true}).click();
	}

	async getSiteTemplateUrl(templateName: string) {
		return await this.page.getByText(templateName).getAttribute('href');
	}

	async goToForms() {
		await this.contentAndDataButton.click();
		await this.formsButton.click();
	}

	async goToMemberships() {
		await this.peopleButton.click();
		await this.membershipsButton.click();
	}

	async goToPages() {
		await this.siteBuilderButton.click();
		await this.pagesButton.click();
	}

	async goToPublishingExport() {
		await this.publishingButton.click();
		await this.exportButton.click();
	}

	async goToPublishingImport() {
		await this.publishingButton.click();
		await this.importButton.click();
	}

	async goToSiteSettings() {
		await this.configurationButton.click();
		await this.siteSettingsButton.click();
	}

	async goToTeams(siteUrl?: string) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.teams}`
		);
	}

	async goToWebContent() {
		await this.contentAndDataButton.click();
		await this.webContentButton.click();
	}

	async openProductMenuIfClosed() {
		if (!(await this.contentAndDataButton.isVisible())) {
			await this.openProductMenuButton.click();
			await this.contentAndDataButton.isVisible();
		}
	}
}

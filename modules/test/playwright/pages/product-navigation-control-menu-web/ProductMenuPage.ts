/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page} from '@playwright/test';

export class ProductMenuPage {
	readonly configurationButton: Locator;
	readonly contentAndDataButton: Locator;
	readonly formsButton: Locator;
	readonly page: Page;
	readonly pagesButton: Locator;
	readonly productMenuButton: Locator;
	readonly productMenuHeader: Locator;
	readonly siteBuilderButton: Locator;
	readonly siteSettingsButton: Locator;
	readonly webContentButton: Locator;

	constructor(page: Page) {
		this.page = page;
		this.configurationButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Configuration',
		});
		this.contentAndDataButton = page.getByRole('menuitem', {
			name: 'Content & Data',
		});
		this.formsButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Forms',
		});
		this.pagesButton = page.getByRole('menuitem', {name: 'Pages'});
		this.productMenuButton = page.getByLabel('Open Product Menu');
		this.productMenuHeader = page.locator(
			'[id="_com_liferay_product_navigation_product_menu_web_portlet_ProductMenuPortlet_site_administrationHeading"] div'
		);
		this.siteBuilderButton = page.getByRole('menuitem', {
			name: 'Site Builder',
		});
		this.siteSettingsButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Site Settings',
		});
		this.webContentButton = page.getByRole('menuitem', {
			name: 'Web Content',
		});
	}

	async goToForms() {
		await this.contentAndDataButton.click();
		await this.formsButton.click();
	}

	async goToSiteSettings() {
		await this.configurationButton.click();
		await this.siteSettingsButton.click();
	}

	async goToWebContent() {
		await this.contentAndDataButton.click();
		await this.webContentButton.click();
	}

	async goToPages() {
		await this.siteBuilderButton.click();
		await this.pagesButton.click();
	}

	async clickSpecificPage(pageName: string) {
		await this.pagesButton.click();
		await this.page.getByLabel(pageName, {exact: true}).click();
	}

	async getSiteTemplateUrl(templateName: string) {
		return await this.page.getByText(templateName).getAttribute('href');
	}

	async checkIfAdecuateProductMenu(templateName: string) {
		await this.productMenuHeader
			.filter({hasText: templateName})
			.nth(2)
			.isVisible();
	}

	async openProductMenuIfClosed() {
		if (!(await this.contentAndDataButton.isVisible())) {
			await this.productMenuButton.click();
			await this.contentAndDataButton.isVisible();
		}
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class WidgetPagePage {
	readonly addApplicationButton: Locator;
	readonly controlMenuAddButton: Locator;
	readonly controlMenuAddPanelContentTab: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;

		this.addApplicationButton = page
			.locator('ul')
			.filter({hasText: 'Open Applications MenuCtrl+Alt+A'})
			.getByLabel('Add');
		this.controlMenuAddButton = page
			.locator('.control-menu-nav-item')
			.getByRole('button', {
				exact: true,
				name: 'Add',
			});
		this.controlMenuAddPanelContentTab = page.getByText('Content', {
			exact: true,
		});
	}

	async addPortlet(portletName: string) {
		await this.clickControlMenuAddButton();
		await this.page
			.getByRole('textbox', {name: 'Search Form'})
			.fill(portletName);
		await this.page.getByLabel('Widgets').getByText(portletName).click();
		await this.page.getByRole('button', {name: 'Add Content'}).click();
	}

	async addContent(contentName: string) {
		await this.clickControlMenuAddButton();
		await this.goToControlMenuAddPanelContentTab();
		await this.page
			.locator('.sidebar-body__add-panel__tab-item')
			.filter({hasText: contentName})
			.getByRole('button', {name: 'Add Content'})
			.click();
	}

	async clickToAddApplication() {
		await this.addApplicationButton.click();
	}

	async clickControlMenuAddButton() {
		await this.controlMenuAddButton.click();
	}

	async goToControlMenuAddPanelContentTab() {
		await this.page.getByText('Content', {exact: true}).click();
	}

	async goToSitePage(site: Site, layoutFriendlyURL: string) {
		await this.page.goto(`/web${site.friendlyUrlPath}${layoutFriendlyURL}`);
	}
}

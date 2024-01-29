/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceLayoutsPage {
	readonly addPageButton: Locator;
	readonly addPageModalSubmitButton: Locator;
	readonly addPageNameInput: Locator;
	readonly closeProductMenuButton: Locator;
	readonly createPageMenuItem: Locator;
	readonly deleteLayoutModal: Locator;
	readonly deletePageButton: Locator;
	readonly openProductMenuButton: Locator;
	readonly page: Page;
	readonly pagesMenuItem: Locator;
	readonly siteBuilderMenuItem: Locator;
	readonly widgetPageTemplateButton: Locator;

	constructor(page: Page) {
		this.addPageButton = page.getByTestId('creationMenuNewButton');
		this.addPageModalSubmitButton = page
			.frameLocator('#addLayoutDialog_iframe_')
			.getByTestId('addLayoutFooter')
			.getByRole('button', {exact: true, name: 'Add'});
		this.addPageNameInput = page
			.frameLocator('#addLayoutDialog_iframe_')
			.getByTestId('addPageNameInput');
		this.closeProductMenuButton = page.getByRole('tab', {
			exact: true,
			name: 'Close Product Menu',
		});
		this.createPageMenuItem = page
			.getByTestId('dropdownMenu')
			.getByRole('menuitem', {
				exact: true,
				name: 'Page',
			});
		this.deleteLayoutModal = page.locator('#deleteLayoutModalDeleteButton');
		this.deletePageButton = page
			.getByTestId('actionDropdownItem')
			.getByRole('button', {
				exact: true,
				name: 'Delete',
			});
		this.openProductMenuButton = page.getByRole('tab', {
			exact: true,
			name: 'Open Product Menu',
		});
		this.page = page;
		this.pagesMenuItem = page.getByTestId('app').filter({hasText: 'Pages'});
		this.siteBuilderMenuItem = page
			.getByTestId('appGroup')
			.filter({hasText: 'Site Builder'});
		this.widgetPageTemplateButton = page
			.getByTestId('cardPageItemDirectory')
			.getByRole('button', {
				exact: true,
				name: 'Widget Page',
			});
	}

	async createWidgetPage(pageName: string) {
		await this.addPageButton.click();
		await this.createPageMenuItem.click();
		await this.widgetPageTemplateButton.click();
		await this.addPageNameInput.waitFor({
			state: 'attached',
		});
		await this.addPageNameInput.click();
		await this.addPageNameInput.fill(pageName);
		await Promise.all([
			this.addPageModalSubmitButton.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet'
						)
			),
		]);
	}

	async goto() {
		await this.page.goto('/');
	}

	async goToPages() {
		await this.goto();

		if (await this.closeProductMenuButton.isVisible()) {
			await this.siteBuilderMenuItem.click();
		}
		else if (await this.openProductMenuButton.isVisible()) {
			await this.openProductMenuButton.click();
			await this.siteBuilderMenuItem.click();
		}

		await Promise.all([
			this.pagesMenuItem.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet'
						)
			),
		]);
	}
}

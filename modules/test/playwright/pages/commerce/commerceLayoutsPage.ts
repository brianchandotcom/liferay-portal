/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

export class CommerceLayoutsPage {
	readonly addPageButton: Locator;
	readonly addPageModalSubmitButton: Locator;
	readonly addPageNameInput: Locator;
	readonly availableThemesFrame: FrameLocator;
	readonly changeCurrentThemeButton: Locator;
	readonly closeProductMenuButton: Locator;
	readonly configurationMenuItem: Locator;
	readonly createPageMenuItem: Locator;
	readonly deleteLayoutModal: Locator;
	readonly deletePageButton: Locator;
	readonly openProductMenuButton: Locator;
	readonly optionsButton: Locator;
	readonly page: Page;
	readonly pagesMenuItem: Locator;
	readonly saveButton: Locator;
	readonly siteBuilderMenuItem: Locator;
	readonly siteHomePageLink: Locator;
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
		this.availableThemesFrame = page.frameLocator(
			'iframe[title="Available Themes"]'
		);
		this.changeCurrentThemeButton = page.getByRole('button', {
			exact: true,
			name: 'Change Current Theme',
		});
		this.closeProductMenuButton = page.getByRole('tab', {
			exact: true,
			name: 'Close Product Menu',
		});
		this.configurationMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Configuration',
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
		this.optionsButton = page.getByLabel('Options', {exact: true});
		this.page = page;
		this.pagesMenuItem = page.getByTestId('app').filter({hasText: 'Pages'});
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
		this.siteBuilderMenuItem = page
			.getByTestId('appGroup')
			.filter({hasText: 'Site Builder'});
		this.siteHomePageLink = page.getByRole('link', {
			exact: true,
			name: 'Home',
		});
		this.widgetPageTemplateButton = page
			.getByTestId('cardPageItemDirectory')
			.getByRole('button', {
				exact: true,
				name: 'Widget Page',
			});
	}

	async changeCurrentTheme(themeName: string) {
		await this.optionsButton.click();
		await this.configurationMenuItem.click();
		await this.changeCurrentThemeButton.click();
		await this.availableThemesFrame
			.getByRole('button', {exact: true, name: themeName})
			.click();
		await this.saveButton.click();
	}

	async createWidgetPage(pageName: string) {
		await this.addPageButton.first().click();
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

	async goToPages(navigation: boolean = true) {
		if (navigation) {
			await this.goto();
		}

		if (
			(await this.closeProductMenuButton.isVisible()) &&
			(await this.pagesMenuItem.isHidden())
		) {
			await this.siteBuilderMenuItem.click();
		}
		else if (await this.openProductMenuButton.isVisible()) {
			await this.openProductMenuButton.click();

			if (await this.pagesMenuItem.isHidden()) {
				await this.siteBuilderMenuItem.click();
			}
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

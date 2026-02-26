/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {openProductMenu} from '../../utils/productMenu';

type Categories = 'Applications' | 'CMS' | 'Commerce' | 'Control Panel';

export class GlobalMenuPage {
	readonly categoriesList: Locator;
	readonly globalMenuButton: Locator;
	readonly page: Page;
	readonly sitesList: Locator;

	constructor(page: Page) {
		this.categoriesList = page
			.getByRole('menu')
			.and(page.locator('.categories-list'));
		this.globalMenuButton = page
			.getByRole('button', {name: 'Applications Menu'})
			.or(page.getByTestId('globalMenu'));
		this.page = page;
		this.sitesList = page
			.getByRole('menu')
			.and(page.locator('.global-menu .sites-list'));
	}

	async goTo(categoryName: Categories) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: categoryName}),
			trigger: this.globalMenuButton,
		});

		const menu = this.page.getByLabel(`${categoryName} Menu`, {
			exact: true,
		});

		await expect(menu).toBeAttached();

		if (!(await menu.isVisible())) {
			openProductMenu(this.page);
		}
	}

	async goToApplications() {
		await this.goTo('Applications');
	}

	async goToCMS() {
		await this.goTo('CMS');
	}

	async goToCommerce() {
		await this.goTo('Commerce');
	}

	async goToControlPanel() {
		await this.goTo('Control Panel');
	}

	async openGlobalMenu() {
		await clickAndExpectToBeVisible({
			target: this.categoriesList,
			trigger: this.globalMenuButton,
		});
	}
}

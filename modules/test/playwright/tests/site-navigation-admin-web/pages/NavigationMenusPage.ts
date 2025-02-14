/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {waitForAlert} from '../../../utils/waitForAlert';

export class NavigationMenusPage {
	readonly page: Page;

	readonly addItemButton: Locator;
	readonly blogsModal: FrameLocator;
	readonly categoriesModal: FrameLocator;
	readonly newButton: Locator;
	readonly pagesModal: FrameLocator;
	readonly selectButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.addItemButton = page.getByLabel('Add Menu Item');
		this.blogsModal = page.frameLocator(
			'iframe[title="Select Blogs Entry"]'
		);
		this.categoriesModal = page.frameLocator(
			'iframe[title="Select Categories"]'
		);
		this.newButton = page.getByRole('button', {name: 'Add'});
		this.pagesModal = page.frameLocator('iframe[title="Select Pages"]');
		this.selectButton = page.getByRole('button', {name: 'Select'});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.navigationMenus}`
		);
	}

	async createNavigationMenu(name: string) {
		await this.newButton.click();

		const input = this.page.getByPlaceholder('Name');

		await input.waitFor();

		await input.fill(name);

		await this.page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(this.page);
	}

	async openAddPageModal() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: 'Page',
			}),
			trigger: this.addItemButton,
		});

		await this.pagesModal.getByPlaceholder('Search').waitFor();
	}

	async openAddCategoryModal() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: 'Category',
			}),
			trigger: this.addItemButton,
		});

		await this.categoriesModal.getByPlaceholder('Search').waitFor();
	}
}

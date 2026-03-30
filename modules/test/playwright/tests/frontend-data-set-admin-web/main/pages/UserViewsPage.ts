/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../../../pages/product-navigation-applications-menu/GlobalMenuPage';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';

export class UserViewsPage {
	readonly adminOptionsDropdown: Locator;
	readonly emptyStateTitle: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly manageUserViewsMenuItem: Locator;
	readonly page: Page;
	readonly pageContainer: Locator;
	readonly table: {
		bodyRows: Locator;
		container: Locator;
		headRow: Locator;
	};

	constructor(page: Page) {
		this.adminOptionsDropdown = page.locator(
			'button[data-qa-id="fdsAdminOptionsMenu"]'
		);
		this.emptyStateTitle = page.getByText('No Results Found');
		this.globalMenuPage = new GlobalMenuPage(page);
		this.manageUserViewsMenuItem = page.getByRole('menuitem', {
			name: 'Manage User Views',
		});
		this.page = page;
		this.pageContainer = page.locator('.data-sets.manage-user-views');

		const tableContainer = page.locator('.fds table');

		this.table = {
			bodyRows: tableContainer.locator('tbody tr'),
			container: tableContainer,
			headRow: tableContainer.locator('thead tr'),
		};
	}

	async goto() {
		await this.globalMenuPage.goToControlPanel('Data Sets');

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.manageUserViewsMenuItem,
			trigger: this.adminOptionsDropdown,
		});

		await this.pageContainer.waitFor({state: 'visible'});
	}
}

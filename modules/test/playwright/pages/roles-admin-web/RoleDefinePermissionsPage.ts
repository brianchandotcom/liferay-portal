/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class RoleDefinePermissionsPage {
	readonly accessInControlPanel: Locator;
	readonly addToPage: Locator;
	readonly loading: Locator;
	readonly menuItem: (name: string) => Locator;
	readonly menuItemByTestId: (id: string) => Locator;
	readonly page: Page;
	readonly portletResourceLabel: Locator;
	readonly resourceSection: (title: string) => Locator;
	readonly searchInput: Locator;

	constructor(page: Page) {
		this.accessInControlPanel = page.getByText('Access in Control Panel');
		this.addToPage = page.getByText('Add to Page');
		this.loading = page.getByText('Loading');
		this.menuItem = (name: string) => {
			return page.getByRole('menuitem', {name});
		};
		this.menuItemByTestId = (id: string) => {
			return page.getByTestId(id).getByRole('menuitem');
		};
		this.page = page;
		this.portletResourceLabel = page.getByTestId('portletResourceLabel');
		this.resourceSection = (title: string) => {
			return page
				.locator('.sheet-tertiary-title')
				.filter({hasText: title});
		};
		this.searchInput = page.getByPlaceholder('Search');
	}
}

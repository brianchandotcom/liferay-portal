/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CUSTOMER_SITE_FRIENLY_URL_PATH} from '../utils/constants';

export class HomePage {
	readonly accountMenu: Locator;
	readonly heading: Locator;
	readonly page: Page;
	readonly projectCard: Locator;
	readonly searchBar: Locator;
	readonly signOutButton: Locator;

	constructor(page: Page) {
		this.accountMenu = page.locator('#account-menu-id img');
		this.heading = page.getByRole('link', {
			name: 'Customer Portal',
		});
		this.page = page;
		this.projectCard = page.locator('.card-body');
		this.searchBar = page.getByPlaceholder('Find a project');
		this.signOutButton = page.locator('a').filter({hasText: 'Sign Out'});
	}

	async goto() {
		await this.page.goto(`${CUSTOMER_SITE_FRIENLY_URL_PATH}/home`);
	}
}

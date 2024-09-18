/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CUSTOMER_SITE_FRIENLY_URL_PATH} from '../utils/constants';

export class HomePage {
	readonly heading: Locator;
	readonly page: Page;
	readonly projectCard: Locator;
	readonly searchBar: Locator;

	constructor(page: Page) {
		this.heading = page.getByRole('link', {
			name: 'Customer Portal',
		});
		this.page = page;
		this.projectCard = page.locator('.card-body').first();
		this.searchBar = page.getByPlaceholder('Find a project');
	}

	async goto() {
		await this.page.goto(`${CUSTOMER_SITE_FRIENLY_URL_PATH}/home`);
	}
}

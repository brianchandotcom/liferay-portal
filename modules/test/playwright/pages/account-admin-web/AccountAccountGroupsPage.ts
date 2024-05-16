/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page} from '@playwright/test';

export class AccountAccountGroupsPage {
	readonly page: Page;
	readonly searchButton: Locator;
	readonly searchInput: Locator;

	constructor(page: Page) {
		this.page = page;
		this.searchButton = this.page.getByLabel('Search for', {exact: true});
		this.searchInput = this.page.getByPlaceholder('Search for');
	}
}

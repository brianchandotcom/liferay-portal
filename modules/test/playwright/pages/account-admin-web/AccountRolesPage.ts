/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class AccountRolesPage {
	readonly addNewRoleButton: Locator;
	readonly editRoleButton: Locator;
	readonly page: Page;
	readonly searchInput: Locator;

	constructor(page: Page) {
		this.addNewRoleButton = page.getByTestId('creationMenuNewButton');
		this.editRoleButton = page.locator('svg.lexicon-icon-ellipsis-v');
		this.page = page;
		this.searchInput = page.getByPlaceholder('Search for');
	}

	async roleName(name: string): Promise<Locator> {
		return this.page.getByText(name, {exact: true});
	}
}

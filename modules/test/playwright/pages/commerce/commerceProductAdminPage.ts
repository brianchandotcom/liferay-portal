/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceProductAdminPage {
	readonly creationMenuNewButton: Locator;
	readonly generateSkusMenuItem: Locator;
	readonly managementToolbarSearchInput: Locator;
	readonly page: Page;
	readonly productSkusLink: Locator;

	constructor(page: Page) {
		this.creationMenuNewButton = page.getByLabel('New', {exact: true});
		this.generateSkusMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Generate All SKU Combinations',
		});
		this.managementToolbarSearchInput = page
			.getByTestId('management-toolbar')
			.getByPlaceholder('Search', {exact: true});
		this.productSkusLink = page.getByRole('link', {
			exact: true,
			name: 'SKUs',
		});
	}

	async generateSkus() {
		await this.productSkusLink.click();

		if (await this.creationMenuNewButton.isHidden()) {
			await this.productSkusLink.click();
		}

		await this.creationMenuNewButton.click();
		await this.generateSkusMenuItem.click();
	}
}

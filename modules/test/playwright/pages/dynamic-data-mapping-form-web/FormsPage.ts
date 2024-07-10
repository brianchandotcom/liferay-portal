/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ProductMenuPage} from '../../pages/product-navigation-control-menu-web/ProductMenuPage';

export class FormsPage {
	readonly emptyResultNewFormButton: Locator;
	readonly formsHeader: Locator;
	readonly managementToolbarNewButton: Locator;
	readonly managementToolbarSearchForButton: Locator;
	readonly page: Page;
	readonly productMenuPage: ProductMenuPage;

	constructor(page: Page) {
		this.emptyResultNewFormButton = page.getByText('New Form', {
			exact: true,
		});
		this.formsHeader = page.getByRole('heading', {
			exact: true,
			name: 'Forms',
		});
		this.managementToolbarNewButton = page.getByText('New', {exact: true});
		this.managementToolbarSearchForButton = page.getByRole('button', {
			name: 'Search for',
		});
		this.page = page;

		this.productMenuPage = new ProductMenuPage(page);
	}

	async clickManagementToolbarNewButton() {
		await this.managementToolbarSearchForButton.isEnabled();
		await this.managementToolbarNewButton.click();
	}

	async clickEmptyResultNewFormButton() {
		await this.emptyResultNewFormButton.click();
	}

	async goTo() {
		await this.productMenuPage.openProductMenuIfClosed();
		await this.productMenuPage.goToForms();
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ProductMenuPage} from '../../pages/product-navigation-control-menu-web/ProductMenuPage';

export class FormsPage {
	readonly emptyResultNewFormButton: Locator;
	readonly formsHeader: Locator;
	readonly managementToolbarDeleteButton: Locator;
	readonly managementToolbarNewButton: Locator;
	readonly managementToolbarSearchForButton: Locator;
	readonly managementToolbarSelectAllItems: Locator;
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
		this.managementToolbarDeleteButton = page.getByRole('button', {
			name: 'Delete',
		});
		this.managementToolbarNewButton = page.getByText('New', {exact: true});
		this.managementToolbarSearchForButton = page.getByRole('button', {
			name: 'Search for',
		});
		this.managementToolbarSelectAllItems = page.getByLabel(
			'Select All Items on the Page'
		);
		this.page = page;

		this.productMenuPage = new ProductMenuPage(page);
	}

	async clickEmptyResultNewFormButton() {
		await this.emptyResultNewFormButton.click();
	}

	async clickFormTitle(formTitle: string) {
		await this.page.getByText(formTitle).click();
	}

	async clickManagementToolbarNewButton() {
		await this.managementToolbarSearchForButton.isEnabled();
		await this.managementToolbarNewButton.click();
	}

	async goTo() {
		await this.productMenuPage.openProductMenuIfClosed();
		await this.productMenuPage.goToForms();
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CommerceLayoutsPage} from '../commerceLayoutsPage';

export class PendingOrdersPage {
	readonly errorMessageCloseButton: Locator;
	readonly layoutsPage: CommerceLayoutsPage;
	readonly orderItemActionsButton: Locator;
	readonly orderItemActionsButtonEdit: Locator;
	readonly page: Page;
	readonly pageLabel: Locator;
	readonly pageTitle: Locator;
	readonly panelList: Locator;
	readonly skuLink: (sku: string) => Locator;
	readonly viewButton: Locator;

	constructor(page: Page) {
		this.errorMessageCloseButton = page.getByRole('button', {
			name: 'close',
		});
		this.layoutsPage = new CommerceLayoutsPage(page);
		this.orderItemActionsButton = page.getByRole('button', {
			name: 'Actions',
		});
		this.orderItemActionsButtonEdit = page.getByRole('menuitem', {
			name: 'Edit',
		});
		this.page = page;
		this.pageLabel = page
			.getByTestId('layoutHref')
			.getByLabel('Pending Orders Page');
		this.pageTitle = page
			.getByTestId('headerTitle')
			.filter({hasText: 'Pending Orders Page'});
		this.panelList = page
			.getByTestId('specificationFacetPanel')
			.getByRole('button');
		this.skuLink = (sku) => page.getByRole('link', {name: sku});
		this.viewButton = page.getByLabel('View');
	}

	async addPendingOrdersWidget() {
		await this.layoutsPage.addWidgetToPage('Open Carts');
	}

	async goto() {
		await this.layoutsPage.goto();
	}
}

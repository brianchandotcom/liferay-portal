/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CustomerDashboardPage} from './customerDashboardPage';

export class CustomerDashboardAppDetailsPage extends CustomerDashboardPage {
	readonly catalogTitle: (catalogName: string) => Locator;
	readonly detailTab: Locator;
	readonly downloadTab: Locator;
	readonly page: Page;
	readonly productTitle: (productName: string) => Locator;
	readonly summaryTab;

	constructor(page: Page) {
		super(page);

		this.catalogTitle = (catalogName: string) =>
			page.getByText(catalogName);
		this.detailTab = page.getByRole('link', {name: 'Details'});
		this.downloadTab = page.getByRole('link', {name: 'Download'});
		this.page = page;
		this.productTitle = (productName: string) =>
			page.getByRole('heading', {
				name: productName,
			});
		this.summaryTab = page.getByRole('heading', {name: 'Summary'});
	}
}

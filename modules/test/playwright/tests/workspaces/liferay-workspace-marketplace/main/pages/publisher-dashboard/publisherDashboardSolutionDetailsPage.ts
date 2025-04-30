/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PublisherDashboardPage} from './publisherDashboardPage';

export class PublisherDashboardSolutionDetailsPage extends PublisherDashboardPage {
	readonly detailTab: Locator;
	readonly productTitle: (productName: string) => Locator;
	readonly page: Page;

	constructor(page: Page) {
		super(page);
		this.detailTab = page.locator('a').filter({hasText: 'Details'});
		this.productTitle = (productName: string) =>
			page.getByRole('heading', {
				name: productName,
			});

		this.page = page;
	}
}

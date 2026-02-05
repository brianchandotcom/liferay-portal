/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class ProductAnalyticsBannerPage {
	readonly acceptAllButton: Locator;
	readonly bannerLocator: Locator;
	readonly customizeButton: Locator;
	readonly declineAllButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.bannerLocator = page.locator('#productAnalyticsBanner');

		this.acceptAllButton = this.bannerLocator.getByRole('button', {
			exact: true,
			name: 'Accept All',
		});
		this.customizeButton = this.bannerLocator.getByRole('button', {
			exact: true,
			name: 'Customize',
		});
		this.declineAllButton = this.bannerLocator.getByRole('button', {
			exact: true,
			name: 'Decline All',
		});
		this.page = page;
	}
}

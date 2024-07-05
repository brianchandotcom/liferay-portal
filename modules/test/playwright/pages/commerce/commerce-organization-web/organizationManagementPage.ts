/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class OrganizationManagementPage {
	readonly accountNode: (accountName: string) => Locator;
	readonly chart: Locator;
	readonly menuButton: (container: Locator) => Locator;
	readonly organizationNode: (organizationName: string) => Locator;
	readonly page: Page;
	readonly removeItem: Locator;

	constructor(page: Page) {
		this.chart = page.locator('svg.svg-chart');
		this.menuButton = (container) => {
			return container.locator('.node-menu-wrapper');
		};
		this.page = page;
		this.removeItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Remove',
		});

		this.accountNode = (accountName) => {
			return this.chart
				.locator('g.chart-item-account')
				.filter({hasText: accountName});
		};
		this.organizationNode = (organizationName) => {
			return this.chart
				.locator('g.chart-item-organization')
				.filter({hasText: organizationName});
		};
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceAdminProductDetailsPage {
	readonly page: Page;
	readonly productDiagramTab: Locator;
	readonly productOptionsTab: Locator;
	readonly productRelationsTab: Locator;

	constructor(page: Page) {
		this.page = page;
		this.productDiagramTab = page.getByRole('link', {
			name: 'Diagram',
		});
		this.productOptionsTab = page.getByRole('link', {
			name: 'Options',
		});
		this.productRelationsTab = page.getByRole('link', {
			name: 'Product Relations',
		});
	}

	async goToProductDiagram() {
		await this.productDiagramTab.click();
	}

	async goToProductOptions() {
		await this.productOptionsTab.click();
	}

	async goToProductRelations() {
		await this.productRelationsTab.click();
	}
}

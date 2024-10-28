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
	readonly productSkusTab: Locator;
	readonly productVisibilityTab: Locator;

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
		this.productSkusTab = page.getByRole('link', {
			name: 'Skus',
		});
		this.productVisibilityTab = page.getByRole('link', {
			name: 'Visibility',
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

	async goToProductSkus() {
		await this.productSkusTab.click();
	}

	async goToProductVisibility() {
		await this.productVisibilityTab.click();
	}
}

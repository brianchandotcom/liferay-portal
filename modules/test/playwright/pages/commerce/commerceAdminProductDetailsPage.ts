/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceAdminProductDetailsPage {
	readonly page: Page;
	readonly productDiagramTab: Locator;
	readonly productRelationsTab: Locator;
	readonly visibilityTab: Locator;

	constructor(page: Page) {
		this.page = page;
		this.productDiagramTab = page.getByRole('link', {
			name: 'Diagram',
		});
		this.productRelationsTab = page.getByRole('link', {
			name: 'Product Relations',
		});
		this.visibilityTab = page.getByRole('link', {
			name: 'Visibility',
		});
	}

	async goToProductDiagram() {
		await this.productDiagramTab.click();
	}

	async goToProductRelations() {
		await this.productRelationsTab.click();
	}

	async goToVisibility() {
		await this.visibilityTab.click();
	}
}

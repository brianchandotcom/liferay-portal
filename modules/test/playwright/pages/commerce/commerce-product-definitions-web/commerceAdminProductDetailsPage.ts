/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceAdminProductDetailsPage {
	readonly page: Page;
	readonly productDetailsInput: (inputName: string) => Promise<Locator>;
	readonly productDiagramLink: Locator;
	readonly productMediaLink: Locator;
	readonly productOptionsLink: Locator;
	readonly productRelationsLink: Locator;
	readonly productSkusLink: Locator;
	readonly productVisibilityLink: Locator;
	readonly publishLink: Locator;

	constructor(page: Page) {
		this.page = page;
		this.productDetailsInput = async (inputName: string) =>
			page.getByLabel(inputName);
		this.productDiagramLink = page.getByRole('link', {
			name: 'Diagram',
		});
		this.productMediaLink = page.getByRole('link', {
			name: 'Media',
		});
		this.productOptionsLink = page.getByRole('link', {
			name: 'Options',
		});
		this.productRelationsLink = page.getByRole('link', {
			name: 'Product Relations',
		});
		this.productSkusLink = page.getByRole('link', {
			name: 'Skus',
		});
		this.productVisibilityLink = page.getByRole('link', {
			name: 'Visibility',
		});
		this.publishLink = page.getByRole('link', {name: 'Publish'});
	}

	async goToProductDiagram() {
		await this.productDiagramLink.click();
	}

	async goToProductOptions() {
		await this.productOptionsLink.click();
	}

	async goToProductRelations() {
		await this.productRelationsLink.click();
	}

	async goToProductSkus() {
		await this.productSkusLink.click();
	}

	async goToProductVisibility() {
		await this.productVisibilityLink.click();
	}
}

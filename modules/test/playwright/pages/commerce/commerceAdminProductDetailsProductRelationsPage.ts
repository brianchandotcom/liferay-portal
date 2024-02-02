/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CommerceAdminProductDetailsPage} from './commerceAdminProductDetailsPage';
import {CommerceDNDTablePage} from './commerceDNDTablePage';

export class CommerceAdminProductDetailsProductRelationsPage extends CommerceDNDTablePage {
	readonly addProductRelationHeading: (
		productName: string
	) => Promise<Locator>;
	readonly commerceAdminProductDetailsPage: CommerceAdminProductDetailsPage;
	readonly creationMenuNewButton: Locator;
	readonly deleteBulkButton: Locator;
	readonly page: Page;
	readonly productRelationsLink: Locator;
	readonly selectItemsInput: Locator;
	readonly addSpareProductMenuButton: Locator;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_product_definitions_web_internal_portlet_CPDefinitionsPortlet_fm .dnd-table'
		);

		this.addProductRelationHeading = async (productName: string) => {
			return page.getByRole('heading', {
				name: 'Add New Product to ' + productName,
			});
		};
		this.commerceAdminProductDetailsPage =
			new CommerceAdminProductDetailsPage(page);
		this.creationMenuNewButton = page
			.getByTestId('management-toolbar')
			.locator('[data-testid="fdsCreationActionButton"]');
		this.deleteBulkButton = page
			.locator('nav')
			.locator('.bulk-actions')
			.getByRole('button');
		this.page = page;
		this.productRelationsLink = page.getByRole('link', {
			exact: true,
			name: 'Product Relations',
		});
		this.selectItemsInput = page.locator('input[title="Select Items"]');
		this.addSpareProductMenuButton = page.getByRole('menuitem', {
			exact: true,
			name: 'Add Spare Product',
		});
	}

	async addSpareProductRelation() {
		await this.goto();
		await this.creationMenuNewButton.click();
		await this.addSpareProductMenuButton.click();
	}

	async goto() {
		await Promise.all([
			this.commerceAdminProductDetailsPage.goToProductRelations(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'screenNavigationCategoryKey=product-relations'
						)
			),
		]);
	}
}

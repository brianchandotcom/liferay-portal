/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {GlobalMenuPage} from '../../product-navigation-applications-menu/GlobalMenuPage';
import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class TaxCategoriesPage extends CommerceDNDTablePage {
	readonly deleteMenuItem: Locator;
	readonly globalMenuPage: GlobalMenuPage;
	readonly newButton: Locator;
	readonly page: Page;
	readonly taxCategoriesTableRowActions: (
		screenName: string
	) => Promise<Locator>;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_product_tax_category_web_internal_portlet_CPTaxCategoryPortlet_cpTaxCategoriesSearchContainer'
		);
		this.globalMenuPage = new GlobalMenuPage(page);
		this.deleteMenuItem = page.getByRole('menuitem', {
			name: 'Delete',
		});
		this.newButton = page.getByRole('link', {name: 'Add Tax Category'});
		this.page = page;
		this.taxCategoriesTableRowActions = async (name: string) => {
			const usersTableRow = await this.tableRow(1, name);

			if (usersTableRow && usersTableRow.column) {
				return usersTableRow.row.getByRole('button');
			}

			throw new Error(`Cannot locate tax category row with name ${name}`);
		};
	}

	async goto(siteName?: string) {
		if (siteName) {
			await this.page.goto(
				`/group/${siteName}/~/control_panel/manage?p_p_id=com_liferay_commerce_product_tax_category_web_internal_portlet_CPTaxCategoryPortlet`
			);
		}
		else {
			await this.globalMenuPage.goToCommerce('Tax Categories');
		}
	}
}

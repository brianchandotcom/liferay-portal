/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CommerceIframeDNDTablePage} from '../../commerce/commerceIframeDNDTablePage';

export class UserAssociatedDataEditMessageBoardThreadPage extends CommerceIframeDNDTablePage {
	readonly basicDocumentMenuItem: Locator;
	readonly blogEntryMenuItem: Locator;
	readonly doneButton: Locator;
	readonly page: Page;
	readonly publishButton: Locator;
	readonly relatedAssetLink: (assetTitle: string) => Locator;
	readonly relatedAssetsButton: Locator;
	readonly selectButton: Locator;
	readonly tableRowCheckBox: (rowValue: string) => Promise<Locator>;

	constructor(page: Page) {
		super(
			page,
			'iframe[title="Select Blogs Entry"], iframe[title="Select Basic Document"]',
			'#_com_liferay_item_selector_web_portlet_ItemSelectorPortlet_entries'
		);
		this.basicDocumentMenuItem = page.getByRole('menuitem', {
			name: 'Basic Document',
		});
		this.blogEntryMenuItem = page.getByRole('menuitem', {
			name: 'Blogs Entry',
		});
		this.doneButton = page.getByRole('button', {name: 'Done'});
		this.page = page;
		this.publishButton = page.getByRole('button', {name: 'Publish'});
		this.relatedAssetLink = (assetTitle: string) =>
			page.getByText(assetTitle);
		this.relatedAssetsButton = page.getByRole('button', {
			name: 'Related Assets',
		});
		this.selectButton = page.getByLabel('Select Items');
		this.tableRowCheckBox = async (rowValue: string) => {
			const tableRow = await this.tableRow(1, rowValue, true);

			if (tableRow && tableRow.row) {
				return tableRow.row.getByRole('checkbox');
			}

			throw new Error(`Cannot locate row with rowValue: ${rowValue}`);
		};
	}
}

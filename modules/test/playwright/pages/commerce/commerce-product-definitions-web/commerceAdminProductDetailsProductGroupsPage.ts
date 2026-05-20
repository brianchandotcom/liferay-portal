/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminProductDetailsProductGroupsPage extends CommerceDNDTablePage {
	readonly createNewSuggestion: (groupName: string) => Locator;
	readonly findOrCreateInput: Locator;
	readonly page: Page;
	readonly productGroupNameLink: (groupName: string) => Locator;
	readonly productGroupRow: (groupName: string) => Locator;
	readonly removeMenuItem: Locator;
	readonly rowActionsButton: (groupName: string) => Locator;
	readonly selectExistingSuggestion: (groupName: string) => Locator;
	readonly sidePanelCustomFieldInput: (fieldName: string) => Locator;
	readonly sidePanelFrame: FrameLocator;
	readonly sidePanelSaveButton: Locator;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_product_definitions_web_internal_portlet_CPDefinitionsPortlet_fm .fds table'
		);
		this.createNewSuggestion = (groupName: string) =>
			page
				.getByRole('listitem')
				.filter({hasText: `"${groupName}"`})
				.getByRole('button', {exact: true, name: 'Create New'});
		this.findOrCreateInput = page.getByRole('textbox', {
			name: 'Find or create a product group',
		});
		this.page = page;
		this.productGroupRow = (groupName: string) =>
			this.table.getByRole('row').filter({hasText: groupName});
		this.productGroupNameLink = (groupName: string) =>
			this.productGroupRow(groupName).getByRole('link', {
				exact: true,
				name: groupName,
			});
		this.removeMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'Remove',
		});
		this.rowActionsButton = (groupName: string) =>
			page.getByRole('button', {
				exact: true,
				name: `${groupName} Actions`,
			});
		this.selectExistingSuggestion = (groupName: string) =>
			page
				.getByRole('row')
				.filter({hasText: groupName})
				.getByRole('button', {exact: true, name: 'Select'});
		this.sidePanelFrame = page
			.locator('.fds-side-panel')
			.getByRole('tabpanel')
			.locator('iframe')
			.contentFrame();
		this.sidePanelCustomFieldInput = (fieldName: string) =>
			this.sidePanelFrame.locator(
				`input[name*="ExpandoAttribute--${fieldName}--"]`
			);
		this.sidePanelSaveButton = this.sidePanelFrame.getByRole('button', {
			exact: true,
			name: 'Save',
		});
	}
}

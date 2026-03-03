/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class DataSetPage {
	readonly activeViewSelector: Locator;
	readonly assetLink: (assetName: string) => Locator;
	readonly page: Page;
	readonly table: {
		bodyRows: Locator;
		container: Locator;
		headRow: Locator;
	};
	readonly selectAllLink: Locator;

	constructor(page: Page) {
		this.activeViewSelector = page.getByLabel(/View Selected/);
		this.assetLink = (assetName) => {
			return page.getByRole('link', {
				exact: true,
				name: assetName,
			});
		};

		const tableContainer = page.locator('.fds table');
		this.table = {
			bodyRows: tableContainer.locator('tbody tr'),
			container: tableContainer,
			headRow: tableContainer.locator('thead tr'),
		};

		this.page = page;
		this.selectAllLink = page.getByRole('button', {
			exact: true,
			name: 'Select All',
		});
	}

	getRow(filter: string) {
		return this.table.bodyRows.filter({hasText: filter});
	}

	async execBulkItemAction({action}) {
		await this.page
			.getByTestId('visualization-mode-table')
			.getByLabel('Actions')
			.click();

		const dropdownMenuItemDelete = this.page.getByRole('menuitem', {
			name: action,
		});

		await expect(dropdownMenuItemDelete).toBeVisible();

		await dropdownMenuItemDelete.click();
	}

	async execItemAction({
		action,
		filter,
		timeout,
	}: {
		action: string;
		filter: string;
		timeout?: number;
	}) {
		const item = this.getRow(filter);
		const button = item.getByRole('button', {
			name: `${filter} Actions`,
		});
		const dropdownId = await button.getAttribute('aria-controls');
		await button.click({timeout});

		const dropdownMenu = this.page
			.locator(`#${dropdownId}`)
			.filter({has: this.page.getByRole('menu')});
		await dropdownMenu.waitFor({timeout});

		const dropdownMenuActionItem = dropdownMenu
			.getByRole('menuitem', {
				name: action,
			})
			.first();

		await dropdownMenuActionItem.waitFor({timeout});
		await dropdownMenuActionItem.click({timeout});
	}

	async changeVisualizationMode(
		visualizationMode: 'Cards' | 'Table' | 'Gallery'
	) {
		await this.activeViewSelector.waitFor({
			state: 'visible',
		});
		await this.activeViewSelector.click();

		await this.page
			.getByRole('listbox')
			.getByRole('option', {name: visualizationMode})
			.click();
	}
}

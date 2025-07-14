/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApiHelpers} from '../../../../helpers/ApiHelpers';
import {EFDSVisualizationMode, waitForFDS} from '../../../../utils/waitFor';

export class ItemSelectorSamplePage {
	private readonly apiHelpers: ApiHelpers;
	readonly fragmentWidgetSearchInput: Locator;
	readonly page: Page;
	readonly publishPageButton: Locator;
	readonly selectFileButton: Locator;
	readonly selectFileModalHeader: Locator;
	readonly table: {
		bodyRows: Locator;
		container: Locator;
	};
	readonly visualizationModeSelector: Locator;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
		this.fragmentWidgetSearchInput = page.getByLabel(
			'Search Fragments and Widgets'
		);
		this.page = page;
		this.publishPageButton = page.getByRole('button', {
			name: 'Publish',
		});
		this.selectFileButton = page.getByRole('button', {
			exact: true,
			name: 'Select File',
		});
		this.selectFileModalHeader = page.getByRole('heading', {
			exact: true,
			name: 'Select File',
		});

		const tableContainer = page.locator('.fds table');

		this.table = {
			bodyRows: tableContainer.locator('tbody tr'),
			container: tableContainer,
		};

		this.visualizationModeSelector = page.getByLabel('Show View Options');
	}

	async addItemSelectorSample({layout}: {layout: Layout}) {
		await this.editPage({layout});

		await this.searchFragmentOrWidget('JS Item Selector Sample');

		const itemSelectorMenuItem = this.page.getByRole('menuitem', {
			exact: true,
			name: 'JS Item Selector Sample Add JS Item Selector Sample Mark JS Item Selector Sample as Favorite',
		});

		await itemSelectorMenuItem.dragTo(
			this.page.getByText('Drag and drop fragments or widgets here.')
		);
	}

	async changeVisualizationMode({
		page,
		visualizationMode,
	}: {
		page: Page;
		visualizationMode: EFDSVisualizationMode;
	}) {
		await this.visualizationModeSelector.waitFor({
			state: 'visible',
		});

		await this.visualizationModeSelector.click();

		await this.page
			.getByRole('listbox')
			.getByRole('option', {name: visualizationMode})
			.click();

		await waitForFDS({page, visualizationMode});
	}

	async configureItemSelector({layout}: {layout: Layout}) {
		await this.addItemSelectorSample({layout});

		await this.publishPage();

		await this.goToPage({layout});
	}

	async editPage({layout}: {layout: Layout}) {
		await this.page.goto(`/web/guest${layout.friendlyURL}?p_l_mode=edit`);
	}

	async goToPage({layout}: {layout: Layout}) {
		await this.page.goto(`/web/guest${layout.friendlyURL}`);
	}

	async publishPage() {
		await this.publishPageButton.click();

		await this.publishPageButton.isEnabled();
	}

	async searchFragmentOrWidget(itemName: string) {
		await this.fragmentWidgetSearchInput.fill(itemName);
	}

	async selectByRowAndRole({
		filter,
		role = 'radio',
		row = 0,
	}: {
		filter?: string;
		role?: any;
		row?: number;
	} = {}) {
		if (filter) {
			await this.table.bodyRows
				.nth(row)
				.locator('td')
				.getByRole(role)
				.filter({hasText: filter})
				.click();
		}
		else {
			await this.table.bodyRows
				.nth(row)
				.locator('td')
				.getByRole(role)
				.first()
				.click();
		}
	}
}

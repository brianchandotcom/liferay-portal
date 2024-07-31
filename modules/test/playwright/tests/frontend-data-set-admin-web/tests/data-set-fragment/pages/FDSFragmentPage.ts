/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ApiHelpers} from '../../../../../helpers/ApiHelpers';
import {DEFAULT_LABEL} from '../../../utils/constants';
import {VisualizationMode} from '../../../utils/types';

export class FDSFragmentPage {
	readonly apiHelpers: ApiHelpers;
	readonly creationMenuButton: Locator;
	readonly editPageButton: Locator;
	readonly emptyStateTitle: Locator;
	readonly fdsActiveViewSelector: Locator;
	readonly fdsAddFilterButton: Locator;
	readonly fdsCardsWrapper: Locator;
	readonly fdsFilterButton: Locator;
	fdsFilterItem: Locator;
	readonly fdsFilterResumeButton: Locator;
	readonly fdsListWrapper: Locator;
	readonly fdsPaginationResults: Locator;
	readonly fdsPaginationWrapper: Locator;
	readonly fdsResetFilterButton: Locator;
	readonly fdsTableWrapper: Locator;
	readonly fragmentWidgetSearchInput: Locator;
	readonly loadingIndicator: Locator;
	readonly page: Page;
	readonly publishPageButton: Locator;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
		this.creationMenuButton = page.getByRole('button', {name: 'New'});
		this.emptyStateTitle = page.getByText('No Results Found');
		this.fdsActiveViewSelector = page.getByLabel('Show View Options');
		this.fdsAddFilterButton = page.getByRole('button', {
			exact: true,
			name: 'Add Filter',
		});
		this.fdsCardsWrapper = page.locator('.cards-container');
		this.fdsFilterButton = page.getByRole('button', {
			exact: true,
			name: 'Filter',
		});
		this.fdsFilterResumeButton = page.locator('.filter-resume');
		this.fdsListWrapper = page.locator('.list-sheet');
		this.fdsPaginationWrapper = page.locator(
			'.data-set-pagination-wrapper'
		);
		this.fdsPaginationResults = page.locator('.pagination-results');
		this.fdsResetFilterButton = page.getByRole('button', {
			exact: true,
			name: 'Reset Filters',
		});
		this.fdsTableWrapper = page.locator('.dnd-table');
		this.fragmentWidgetSearchInput = page.getByLabel(
			'Search Fragments and Widgets'
		);
		this.loadingIndicator = page.locator('.fds .loading-animation');
		this.page = page;
		this.publishPageButton = page.getByRole('button', {
			name: 'Publish',
		});
	}

	async goto() {
		await this.page.goto('/');
	}

	async selectFilter(filterLabel) {
		await this.fdsFilterButton.waitFor({state: 'visible'});
		const filterDropdownId = await this.fdsFilterButton.evaluate((node) =>
			node.getAttribute('aria-controls')
		);
		await this.fdsFilterButton.click();
		await this.page
			.locator(`#${filterDropdownId}`)
			.waitFor({state: 'visible'});
		this.fdsFilterItem = this.page.locator(`#${filterDropdownId}`);
		this.fdsFilterItem
			.getByRole('menuitem', {
				name: filterLabel,
			})
			.click();
	}

	async changeVisualizationMode(visualizationMode: VisualizationMode) {
		await this.fdsActiveViewSelector.waitFor({
			state: 'visible',
		});
		await this.fdsActiveViewSelector.click();

		await this.page
			.getByRole('listbox')
			.getByRole('option', {name: visualizationMode})
			.click();
	}

	async configureDataSetFragment({
		dataSetLabel = DEFAULT_LABEL.DATA_SET,
		layout,
	}: {
		dataSetLabel?: string;
		layout: Layout;
	}) {
		await this.setupPageAndFragment(layout);

		await this.page
			.frameLocator('iframe[title="Select"]')
			.locator('.fds-admin-item-selector')
			.waitFor({state: 'visible'});

		await this.page
			.frameLocator('iframe[title="Select"]')
			.locator('li')
			.filter({hasText: dataSetLabel})
			.first()
			.click();

		await this.page
			.frameLocator('iframe[title="Select"]')
			.getByRole('button', {name: 'Save'})
			.click();

		const selectedDataSetView = await this.page.getByLabel('Configuration Panel').getByLabel('Data Set View', {exact: true} ).inputValue();
		
		await expect(selectedDataSetView).toEqual(dataSetLabel);

		await this.publishPage();

		await this.goToPage({layout});

		await this.page
			.locator('.data-set-content-wrapper')
			.waitFor({state: 'visible'});
	}

	async configureEmptyDataSetFragment({layout}: {layout: Layout}) {
		await this.setupPageAndFragment(layout);

		await this.page
			.frameLocator('iframe[title="Select"]')
			.locator('.c-empty-state-title')
			.waitFor({state: 'visible'});
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

	async setupPageAndFragment(layout: Layout) {
		await this.editPage({layout});

		await this.searchFragmentOrWidget('Data Set');

		const dataSetMenuItem = this.page.getByRole('menuitem', {
			exact: true,
			name: 'Data Set Add Data Set Mark Data Set as Favorite',
		});

		await dataSetMenuItem.dragTo(
			this.page.getByText('Place fragments or widgets here')
		);

		const fragmentSelectionArea = this.page.getByText(
			'Select a data set view'
		);

		await expect(fragmentSelectionArea).toBeVisible();

		await fragmentSelectionArea.click();

		await this.page
			.getByRole('button', {name: 'Select Data Set View'})
			.click();

		await this.page.getByRole('dialog').isVisible();

		await this.page.getByRole('heading', {name: 'Select'}).isVisible();
	}
}

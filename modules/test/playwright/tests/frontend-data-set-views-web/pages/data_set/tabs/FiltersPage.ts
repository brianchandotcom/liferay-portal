/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ISelectionFilter} from '../../../utils/types';
import {DataSetPage} from '../DataSetPage';

export class FiltersPage {
	private readonly dataSetPage: DataSetPage;

	private readonly filterTable: Locator;

	readonly newDateRangeFilterModal: {
		datePicker: Locator;
		filterBySelect: Locator;
		fromInput: Locator;
		fromDatePickerTrigger: Locator;
		toInput: Locator;
		toDatePickerTrigger: Locator;
	};
	private readonly newFilterButton: Locator;
	private readonly newFilterModal: {
		cancelButton: Locator;
		saveButton: Locator;
	};
	private readonly newSelectionFilterModal: {
		filterBySelect: Locator;
		filterModeRarioButtons: Locator;
		nameInput: Locator;
		newFilterModalheading: Locator;
		picklistDropdown: Locator;
		preselectedValuesMultiSelect: Locator;
		selectionRadioButtons: Locator;
		sourceDropdown: Locator;
	};
	readonly page: Page;

	constructor(page: Page) {
		this.dataSetPage = new DataSetPage(page);
		this.filterTable = page.getByRole('table');
		this.newFilterButton = page
			.getByRole('button', {name: 'New Filter'})
			.and(page.getByTitle('New Filter'));
		this.newDateRangeFilterModal = {
			datePicker: page.getByRole('dialog', { name: 'Choose date'}),
			filterBySelect: page.getByLabel('Filter By'),
			fromDatePickerTrigger: page.locator('div').filter({ hasText: /^From$/ }).getByRole('button'),
			fromInput: page.locator('div').filter({ hasText: /^From$/ }).getByPlaceholder('YYYY-MM-DD'),
			toDatePickerTrigger: page.locator('div').filter({ hasText: /^From$/ }).getByRole('button'),
			toInput: page.locator('div').filter({ hasText: /^To$/ }).getByPlaceholder('YYYY-MM-DD'),
		};
		this.newFilterModal = {
			cancelButton: page.getByRole('button', {name: 'Cancel'}),
			saveButton: page.getByRole('button', {name: 'Save'}),
		};
		this.newSelectionFilterModal = {
			filterBySelect: page.getByLabel('Filter ByRequired'),
			filterModeRarioButtons: page.getByText('Filter ModeIncludeExclude'),
			nameInput: page.getByPlaceholder('Add a name'),
			newFilterModalheading: page.getByRole('heading', {
				name: 'New Selection Filter',
			}),
			picklistDropdown: page.getByLabel('Picklist'),
			preselectedValuesMultiSelect: page.getByPlaceholder(
				'Select a default value for your filter.'
			),
			selectionRadioButtons: page.getByText('SelectionMultipleSingle'),
			sourceDropdown: page.getByLabel('Choose an Option'),
		};
		this.page = page;
	}

	async goto({dataSetLabel}: {dataSetLabel: string}) {
		await this.dataSetPage.goto({
			dataSetLabel,
		});

		await this.dataSetPage.selectTab('Filters');
	}

	async assertFiltersTableRowCount(rowCount: number) {
		await expect(
			this.filterTable.locator('tbody').locator('tr')
		).toHaveCount(rowCount);
	}

	async cancelAddFilterModal() {
		await this.newFilterModal.cancelButton.click();
	}

	async createSelectionFilter({
		filterBy,
		filterMode,
		name,
		picklist,
		preselectedValues,
		selectionType,
		source,
	}: ISelectionFilter) {
		await this.openNewFilterModal({
			dropdownItemLabel: 'Selection',
		});

		await this.newSelectionFilterModal.nameInput.click();
		await this.newSelectionFilterModal.nameInput.fill(name);
		await this.newSelectionFilterModal.filterBySelect.click();
		await this.page.getByRole('option', {name: filterBy}).click();
		await this.newSelectionFilterModal.sourceDropdown.selectOption(source);
		await this.newSelectionFilterModal.picklistDropdown.selectOption(
			picklist
		);
		await this.newSelectionFilterModal.preselectedValuesMultiSelect.click();
		await this.page
			.getByRole('option', {name: preselectedValues[0]})
			.click();
		await this.page.getByText(selectionType).click();
		await this.page.locator('label').filter({hasText: filterMode}).click();
		await this.newFilterModal.saveButton.click();
	}

	async openNewFilterModal({dropdownItemLabel}: {dropdownItemLabel: string}) {
		await expect(this.newFilterButton).toBeVisible();

		await this.newFilterButton.click();

		const menuItem = this.page.getByRole('menuitem', {
			name: dropdownItemLabel,
		});

		await expect(menuItem).toBeVisible();

		await menuItem.click();

		await expect(this.newFilterModal.saveButton).toBeVisible();
	}
}

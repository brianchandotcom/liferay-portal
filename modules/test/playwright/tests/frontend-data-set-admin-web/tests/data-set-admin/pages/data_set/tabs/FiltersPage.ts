/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {
	IClientExtensionFilter,
	IDateRangeFilter,
	ISelectionFilterApiHeadless,
	ISelectionFilterPicklist,
} from '../../../../../utils/types';
import {DataSetPage} from '../DataSetPage';

interface NewFilterModal {
	cancelButton: Locator;
	closeButton: Locator;
	filterByDropdown: Locator;
	filterBySelect: Locator;
	formFeedback: Locator;
	modalBody: Locator;
	nameInput: Locator;
	saveButton: Locator;
}

interface NewClientExtensionFilterModal extends NewFilterModal {
	noClientExtensionsAvailableAlert: Locator;
}

interface NewSelectionFilterModal extends NewFilterModal {
	filterModeRadioButtons: Locator;
	itemKey: Locator;
	itemLabel: Locator;
	picklistDropdown: Locator;
	preselectedValuesMultiSelect: Locator;
	restApplicationField: Locator;
	restApplicationOptions: Locator;
	restEndpointField: Locator;
	restEndpointOptions: Locator;
	restSchemaField: Locator;
	restSchemaOptions: Locator;
	selectionRadioButtons: Locator;
	sourceTypeDropdown: Locator;
}

interface NewDateRangeFilterModal extends NewFilterModal {
	datePicker: Locator;
	fromDatePickerTrigger: Locator;
	fromInput: Locator;
	toDatePickerTrigger: Locator;
	toInput: Locator;
}

export class FiltersPage {
	private readonly dataSetPage: DataSetPage;

	private readonly filterTable: Locator;

	readonly newClientExtensionFilterModal: NewClientExtensionFilterModal;
	readonly newDateRangeFilterModal: NewDateRangeFilterModal;
	readonly newFilterButton: Locator;
	private readonly newFilterModal: NewFilterModal;
	private readonly newSelectionFilterModal: NewSelectionFilterModal;
	readonly page: Page;

	constructor(page: Page) {
		this.dataSetPage = new DataSetPage(page);
		this.filterTable = page.getByRole('table');
		this.newFilterButton = page
			.getByRole('button', {name: 'New Filter'})
			.and(page.getByTitle('New Filter'));
		this.newFilterModal = {
			cancelButton: page.getByRole('button', {name: 'Cancel'}),
			closeButton: page.getByRole('button', {
				exact: true,
				name: 'Close',
			}),
			filterByDropdown: page.locator('.fds-field-name-dropdown-menu'),
			filterBySelect: page.getByLabel('Filter By'),
			formFeedback: page.locator('.form-feedback-item'),
			modalBody: page.locator('.modal-body'),
			nameInput: page.getByPlaceholder('Add a name'),
			saveButton: page.getByRole('button', {name: 'Save'}),
		};
		this.newClientExtensionFilterModal = {
			...this.newFilterModal,
			noClientExtensionsAvailableAlert: page.getByLabel('New Client Extension Filter')
				.locator('div')
				.filter({ hasText: 'InfoNo frontend data set filter client extensions are available. Add a client ex' })
				.first(),
		};
		this.newDateRangeFilterModal = {
			...this.newFilterModal,
			datePicker: page.getByRole('dialog', {name: 'Choose date'}),
			fromDatePickerTrigger: page
				.locator('div')
				.filter({hasText: /^From$/})
				.getByRole('button'),
			fromInput: page
				.locator('div')
				.filter({hasText: /^From$/})
				.getByPlaceholder('YYYY-MM-DD'),
			toDatePickerTrigger: page
				.locator('div')
				.filter({hasText: /^From$/})
				.getByRole('button'),
			toInput: page
				.locator('div')
				.filter({hasText: /^To$/})
				.getByPlaceholder('YYYY-MM-DD'),
		};
		this.newSelectionFilterModal = {
			...this.newFilterModal,
			filterModeRadioButtons: page.getByText('Filter ModeIncludeExclude'),
			itemKey: page.locator('.fds-filter-item-key'),
			itemLabel: page.locator('.fds-filter-item-label'),
			picklistDropdown: page.locator('label').filter({hasText: 'PicklistRequired'}),
			preselectedValuesMultiSelect: page.getByPlaceholder(
				'Select a default value for your filter.'
			),
			restApplicationField: page.getByLabel('REST ApplicationRequired'),
			restApplicationOptions: page.locator(
				'.fds-filter-rest-application-menu'
			),
			restEndpointField: page.getByLabel('REST EndpointRequired'),
			restEndpointOptions: page.locator('.fds-filter-rest-endpoint-menu'),
			restSchemaField: page.getByLabel('REST SchemaRequired'),
			restSchemaOptions: page.locator('.fds-filter-rest-schema-menu'),
			selectionRadioButtons: page.getByText('SelectionMultipleSingle'),
			sourceTypeDropdown: page.locator('label').filter({hasText: 'SourceRequired'}),
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

	async assertValidationError(text: string) {
		const visualFeedback = this.newFilterModal.formFeedback
			.filter({has: this.page.locator('.form-feedback-indicator')})
			.first();

		await expect(visualFeedback).toBeVisible();

		await expect(visualFeedback).toContainText(text);
	}

	async cancelAddFilterModal() {
		await this.newFilterModal.cancelButton.click();
	}

	async createDateRangeFilter({filterBy, from, name, to}: IDateRangeFilter) {
		await this.openNewFilterModal({
			dropdownItemLabel: 'Date Range',
		});

		await this.newDateRangeFilterModal.nameInput.click();
		await this.newDateRangeFilterModal.nameInput.fill(name);

		await this.newDateRangeFilterModal.filterBySelect.click();

		const dateFilterOption = this.page.getByRole('option', {
			name: filterBy,
		});

		await dateFilterOption.click();

		if (from) {
			await this.newDateRangeFilterModal.fromInput.fill(from);
		}

		if (to) {
			await this.newDateRangeFilterModal.toInput.fill(to);
		}

		await this.saveAddFilterModal();
	}

	async createSelectionFilterApiHeadless({
		filterBy,
		filterMode,
		itemKey,
		itemLabel,
		name,
		preselectedValues,
		restApplication,
		restEndpoint,
		restSchema,
		selectionType,
		sourceType,
	}: ISelectionFilterApiHeadless) {
		await this.openNewFilterModal({
			dropdownItemLabel: 'Selection',
		});

		await this.newSelectionFilterModal.nameInput.click();
		await this.newSelectionFilterModal.nameInput.fill(name);

		await this.newSelectionFilterModal.filterBySelect.click();
		await this.page.getByRole('option', {name: filterBy}).click();

		await this.newSelectionFilterModal.sourceTypeDropdown.selectOption(
			sourceType
		);

		await this.newSelectionFilterModal.restApplicationField.click();
		await this.newSelectionFilterModal.restApplicationOptions.waitFor();
		await this.newSelectionFilterModal.restApplicationOptions
			.getByRole('option', {name: restApplication})
			.click();

		await this.newSelectionFilterModal.restSchemaField.waitFor();
		await this.newSelectionFilterModal.restSchemaField.click();

		await this.newSelectionFilterModal.restSchemaOptions
			.getByRole('option', {exact: true, name: restSchema})
			.click();
		await this.newSelectionFilterModal.restSchemaField.click();

		await this.newSelectionFilterModal.restEndpointField.waitFor();
		await this.newSelectionFilterModal.restEndpointField.click();

		await this.newSelectionFilterModal.restEndpointOptions
			.getByRole('option', {exact: true, name: restEndpoint})
			.click();
		await this.newSelectionFilterModal.restEndpointField.click();

		await this.newSelectionFilterModal.itemKey.click();
		await this.page
			.getByRole('option', {exact: true, name: itemKey})
			.click();
		await this.newSelectionFilterModal.itemKey.click();

		await this.newSelectionFilterModal.itemLabel.click();
		await this.page
			.getByRole('option', {exact: true, name: itemLabel})
			.click();
		await this.newSelectionFilterModal.itemLabel.click();

		await this.newSelectionFilterModal.preselectedValuesMultiSelect.click();
		await this.page
			.getByRole('option', {name: preselectedValues[0]})
			.click();

		await this.page.getByText(selectionType).click();
		await this.page.locator('label').filter({hasText: filterMode}).click();
		await this.saveAddFilterModal();
	}

	async createSelectionFilterPicklist({
		filterBy,
		filterMode,
		name,
		preselectedValues,
		selectionType,
		source,
		sourceType,
	}: ISelectionFilterPicklist) {
		await this.openNewFilterModal({
			dropdownItemLabel: 'Selection',
		});

		await this.newSelectionFilterModal.nameInput.click();
		await this.newSelectionFilterModal.nameInput.fill(name);
		await this.newSelectionFilterModal.filterBySelect.click();
		await this.page.getByRole('option', {name: filterBy}).click();
		await this.newSelectionFilterModal.sourceTypeDropdown.click();
		await this.newSelectionFilterModal.sourceTypeDropdown.selectOption(
			sourceType
		);
		await this.newSelectionFilterModal.picklistDropdown.click();
		await this.newSelectionFilterModal.picklistDropdown.selectOption(
			source
		);
		await this.newSelectionFilterModal.preselectedValuesMultiSelect.click();

		if (preselectedValues.length) {
			await this.page
			.getByRole('option', {name: preselectedValues[0]})
			.click();
			await this.page.locator('label').filter({hasText: filterMode}).click();
		}
		await this.page.getByText(selectionType).click();

		await this.saveAddFilterModal();
	}

	getRowByText(text: string) {
		return this.filterTable
			.locator('tbody')
			.locator('tr')
			.filter({
				has: this.page.getByText(text, {exact: true}).first(),
			});
	}

	async openNewFilterModal({
		dropdownItemLabel,
		expectSaveHidden = false,
	}: {
		dropdownItemLabel: string;
		expectSaveHidden?: boolean;
	}) {
		await expect(this.newFilterButton).toBeVisible();

		await this.newFilterButton.click();

		const menuItem = this.page.getByRole('menuitem', {
			name: dropdownItemLabel,
		});

		await expect(menuItem).toBeVisible();

		await menuItem.click();

		if (expectSaveHidden) {
			await expect(this.newFilterModal.saveButton).toBeHidden();
		}
		else {
			await expect(this.newFilterModal.saveButton).toBeVisible();
		}
	}

	async saveAddFilterModal() {
		await this.newFilterModal.saveButton.click();
		await this.newFilterModal.saveButton.waitFor({state: 'hidden'});
	}
}

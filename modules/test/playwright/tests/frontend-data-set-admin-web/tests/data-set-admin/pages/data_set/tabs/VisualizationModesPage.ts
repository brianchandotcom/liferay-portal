/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {FieldSelectModalPage} from '../../components/FieldSelectModalPage';
import {DataSetPage} from '../DataSetPage';

export class VisualizationModesPage {
	readonly addCustomFieldInput: Locator;
	private readonly addFieldsButton: Locator;
	readonly cardsVisualizationModeContainer: Locator;
	private readonly container: Locator;
	readonly dataSetPage: DataSetPage;
	readonly fieldSelectModalPage: FieldSelectModalPage;
	readonly listVisualizationModeContainer: Locator;
	readonly page: Page;
	readonly tableVisualizationModeContainer: Locator;

	constructor(page: Page) {
		this.addCustomFieldInput = page.getByPlaceholder('Type Field Here.');
		this.addFieldsButton = page
			.getByRole('tabpanel')
			.getByRole('list')
			.getByLabel('New');
		this.cardsVisualizationModeContainer = page.locator(
			'.cards-visualization-mode'
		);
		this.container = page.locator('.visualization-modes');
		this.dataSetPage = new DataSetPage(page);
		this.fieldSelectModalPage = new FieldSelectModalPage(page);
		this.listVisualizationModeContainer = page.locator(
			'.list-visualization-mode'
		);
		this.page = page;
		this.tableVisualizationModeContainer = page.locator(
			'.table-visualization-mode'
		);
	}

	async assertTableFieldRowCount(rowCount: number) {
		await expect(
			this.tableVisualizationModeContainer.locator('tbody').locator('tr')
		).toHaveCount(rowCount);
	}

	async cancelAddFieldsModal() {
		await this.fieldSelectModalPage.cancelAddFieldsModal();
	}

	async getAssignedFieldLocator({
		container,
		sectionLabel,
	}: {
		container: Locator;
		sectionLabel: string;
	}) {
		return container
			.locator('tr')
			.filter({has: this.page.getByText(sectionLabel)})
			.locator('td.field-name');
	}

	getRowByText(text: string) {
		return this.page.locator('tr').filter({
			has: this.page.getByText(text, {exact: true}).first(),
		});
	}

	getFieldCheckboxByLabel(label: string) {
		return this.fieldSelectModalPage.getFieldCheckboxByLabel(label);
	}

	async goto({dataSetLabel}: {dataSetLabel: string}) {
		await this.dataSetPage.goto({
			dataSetLabel,
		});

		await this.dataSetPage.selectTab('Visualization Modes');
	}

	async openAddDataSourceFieldsModal() {
		await this.addFieldsButton.click();

		const assignDataSourceFieldsButton = await this.page.getByRole(
			'menuitem',
			{name: 'Assign from Data Source'}
		);

		await assignDataSourceFieldsButton.waitFor();
		await assignDataSourceFieldsButton.click();

		await this.fieldSelectModalPage.addFieldsDialog.fields
			.first()
			.waitFor();
	}

	async openAddCustomFieldModal() {
		await this.addFieldsButton.click();

		const assignCustomFieldButton = await this.page.getByRole('menuitem', {
			name: 'Assign Field Manually',
		});

		await assignCustomFieldButton.waitFor();
		await assignCustomFieldButton.click();
	}

	async openAssignDataSourceFieldsModal({
		container,
		sectionLabel,
	}: {
		container: Locator;
		sectionLabel: string;
	}) {
		await container
			.locator('tr')
			.filter({has: this.page.getByText(sectionLabel)})
			.getByTitle('Assign Field')
			.click();

		const assignDataSourceFieldsButton = await this.page.getByRole(
			'menuitem',
			{name: 'Assign from Data Source'}
		);

		await assignDataSourceFieldsButton.waitFor();
		await assignDataSourceFieldsButton.click();
	}

	async openAssignCustomFieldModal({
		container,
		sectionLabel,
	}: {
		container: Locator;
		sectionLabel: string;
	}) {
		await container
			.locator('tr')
			.filter({has: this.page.getByText(sectionLabel)})
			.getByTitle('Assign Field')
			.click();

		const assignCustomFieldButton = await this.page.getByRole('menuitem', {
			name: 'Assign Field Manually',
		});

		await assignCustomFieldButton.waitFor();
		await assignCustomFieldButton.click();
	}

	async openChangeDataSourceFieldsModal({
		container,
		sectionLabel,
	}: {
		container: Locator;
		sectionLabel: string;
	}) {
		await container
			.locator('tr')
			.filter({has: this.page.getByText(sectionLabel)})
			.getByTitle(`View ${sectionLabel} Options`)
			.click();

		const changeAssignmentButton = await this.page.getByRole('menuitem', {
			name: 'Change Field From Data Source',
		});

		await changeAssignmentButton.waitFor();
		await changeAssignmentButton.click();

		await this.fieldSelectModalPage.fieldSelectModalContainer
			.getByPlaceholder('Search')
			.waitFor();
	}

	async openChangeCustomFieldModal({
		container,
		sectionLabel,
	}: {
		container: Locator;
		sectionLabel: string;
	}) {
		await container
			.locator('tr')
			.filter({has: this.page.getByText(sectionLabel)})
			.getByTitle(`View ${sectionLabel} Options`)
			.click();

		const changeAssignmentButton = await this.page.getByRole('menuitem', {
			name: 'Change Field Manually',
		});

		await changeAssignmentButton.waitFor();
		await changeAssignmentButton.click();

		await this.page.getByRole('heading', {name: 'Add Field Manually'});
	}

	async searchAndSelectField(path: string) {
		await this.fieldSelectModalPage.searchAndSelectField(path);
	}

	async selectField({
		dataId,
		fieldName,
	}: {
		dataId?: string;
		fieldName: string;
	}) {
		await this.fieldSelectModalPage.checkField({
			dataId,
			expected: true,
			fieldName,
		});
	}

	async selectTab(tabLabel: string) {
		const tab = this.container.getByRole('tab', {
			exact: true,
			name: tabLabel,
		});

		await tab.click();
	}

	async unSelectField({
		dataId,
		fieldName,
	}: {
		dataId?: string;
		fieldName: string;
	}) {
		await this.fieldSelectModalPage.checkField({
			dataId,
			expected: false,
			fieldName,
		});
	}

	async unSelectSelectedFields() {
		await this.fieldSelectModalPage.unSelectSelectedFields();
	}
}

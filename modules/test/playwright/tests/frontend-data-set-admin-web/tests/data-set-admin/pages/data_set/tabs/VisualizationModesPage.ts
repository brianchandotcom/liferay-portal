/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {FieldSelectModalComponent} from '../../../components/FieldSelectModalComponent';
import {DataSetPage} from '../DataSetPage';

export class VisualizationModesPage {
	private readonly addFieldsButton: Locator;
	readonly cardsVisualizationModeContainer: Locator;
	private readonly container: Locator;
	readonly dataSetPage: DataSetPage;
	readonly fieldSelectModalComponent: FieldSelectModalComponent;
	readonly listVisualizationModeContainer: Locator;
	readonly page: Page;
	readonly tableVisualizationModeContainer: Locator;

	constructor(page: Page) {
		this.addFieldsButton = page.getByLabel('Add Fields');
		this.cardsVisualizationModeContainer = page.locator(
			'.cards-visualization-mode'
		);
		this.container = page.locator('.visualization-modes');
		this.dataSetPage = new DataSetPage(page);
		this.fieldSelectModalComponent = new FieldSelectModalComponent(page);
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
		await this.fieldSelectModalComponent.cancelAddFieldsModal();
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
		return this.fieldSelectModalComponent.getFieldCheckboxByLabel(label);
	}

	async goto({dataSetLabel}: {dataSetLabel: string}) {
		await this.dataSetPage.goto({
			dataSetLabel,
		});

		await this.dataSetPage.selectTab('Visualization Modes');
	}

	async openAddFieldsModal() {
		await this.addFieldsButton.click();

		await this.fieldSelectModalComponent.addFieldsDialog.fields
			.first()
			.waitFor();
	}

	async openAssignFieldModal({
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
	}

	async openChangeFieldModal({
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

		const changeAssignmentButton = this.page.getByRole('menuitem', {
			name: 'Change Assignment',
		});

		await changeAssignmentButton.waitFor();
		await changeAssignmentButton.click();

		await this.fieldSelectModalComponent.fieldSelectModalContainer
			.getByPlaceholder('Search')
			.waitFor();
	}

	async searchAndSelectField(path: string) {
		await this.fieldSelectModalComponent.searchAndSelectField(path);
	}

	async selectField({
		dataId,
		fieldName,
	}: {
		dataId?: string;
		fieldName: string;
	}) {
		await this.fieldSelectModalComponent.checkField({
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
		await this.fieldSelectModalComponent.checkField({
			dataId,
			expected: false,
			fieldName,
		});
	}

	async unSelectSelectedFields() {
		await this.fieldSelectModalComponent.unSelectSelectedFields();
	}
}

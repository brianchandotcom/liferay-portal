/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ViewPage} from '../ViewPage';

export class VisualizationModesPage {
	private readonly addFieldsButton: Locator;
	private readonly addFieldsDialog: {
		cancelButton: Locator;
		fields: Locator;
		fieldsTreeview: Locator;
		saveButton: Locator;
	};
	readonly cardsVisualizationModeContainer: Locator;
	private readonly container: Locator;
	readonly fieldSelectModalContainer: Locator;
	readonly listVisualizationModeContainer: Locator;
	readonly page: Page;
	private readonly viewPage: ViewPage;

	constructor(page: Page) {
		this.addFieldsButton = page.getByLabel('Add Fields');
		this.addFieldsDialog = {
			cancelButton: page.getByRole('button', {name: 'Cancel'}),
			fields: page.locator('.treeview-item'),
			fieldsTreeview: page.locator('.treeview'),
			saveButton: page.getByRole('button', {name: 'Save'}),
		};
		this.cardsVisualizationModeContainer = page.locator(
			'.cards-visualization-mode'
		);
		this.container = page.locator('.visualization-modes');
		this.listVisualizationModeContainer = page.locator(
			'.list-visualization-mode'
		);
		this.fieldSelectModalContainer = page.locator('.field-select-modal');
		this.page = page;
		this.viewPage = new ViewPage(page);
	}

	async addChildField(path: string[], field: string) {
		this.openParentField(path);

		await this.page
			.locator(`[data-id$="${path.join('.')}.${field}"]`)
			.getByText(field, {exact: true})
			.check();
	}

	async addRootField(field: string) {
		await this.addFieldsDialog.fields
			.getByText(field, {exact: true})
			.click();
	}

	async cancelAddFieldsModal() {
		await this.addFieldsDialog.cancelButton.click();
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

	async goto({
		dataSetLabel,
		viewLabel,
	}: {
		dataSetLabel: string;
		viewLabel: string;
	}) {
		await this.viewPage.goto({
			dataSetLabel,
			viewLabel,
		});

		await this.viewPage.selectTab('Visualization Modes');
	}

	async openAddFieldsModal() {
		await this.addFieldsButton.click();

		await this.addFieldsDialog.fields.first().waitFor();
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

		await expect(
			this.fieldSelectModalContainer
				.locator('.custom-control-input')
				.first()
		).toBeVisible();

		await expect(
			this.fieldSelectModalContainer
				.locator('.custom-control-label')
				.first()
		).toBeInViewport();
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

		await this.fieldSelectModalContainer
			.getByPlaceholder('Search')
			.waitFor();
	}

	async openParentField(path: string[]) {
		let fullPath = '';

		path.forEach(async (item) => {
			fullPath += item;
			const expandButton = this.page.locator(
				`button[aria-controls='${fullPath}.*']`
			);
			fullPath += '.';

			if (!(await expandButton.getAttribute('aria-expanded'))) {
				await expandButton.click();
			}
		});
	}

	async selectTab(tabLabel: string) {
		const tab = this.container.getByRole('tab', {
			exact: true,
			name: tabLabel,
		});

		await tab.click();
	}
}

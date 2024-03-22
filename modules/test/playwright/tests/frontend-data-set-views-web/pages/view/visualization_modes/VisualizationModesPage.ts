/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ViewPage} from '../ViewPage';

export class VisualizationModesPage {
	readonly cardsVisualizationModeContainer: Locator;
	private readonly container: Locator;
	readonly fieldSelectModalContainer: Locator;
	readonly listVisualizationModeContainer: Locator;
	readonly page: Page;
	private readonly toastContainer: Locator;
	private readonly viewPage: ViewPage;

	constructor(page: Page) {
		this.cardsVisualizationModeContainer = page.locator(
			'.cards-visualization-mode'
		);
		this.container = page.locator('.visualization-modes');
		this.listVisualizationModeContainer = page.locator(
			'.list-visualization-mode'
		);
		this.fieldSelectModalContainer = page.locator('.field-select-modal');
		this.page = page;
		this.toastContainer = page.locator('.alert-container');
		this.viewPage = new ViewPage(page);
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

	async selectTab(tabLabel: string) {
		const tab = this.container.getByRole('tab', {
			exact: true,
			name: tabLabel,
		});

		await tab.click();
	}

	async saveFieldSelection() {

		// Modal for field selection must be open.

		await this.page
			.getByRole('button', {
				exact: true,
				name: 'Save',
			})
			.click();

		await expect(this.fieldSelectModalContainer).not.toBeInViewport();

		await expect(this.toastContainer).toBeInViewport();

		await this.page.getByText('Success').waitFor();

		await this.toastContainer
			.getByRole('button', {
				name: 'Close',
			})
			.click();

		await expect(this.toastContainer).toBeHidden();
	}
}

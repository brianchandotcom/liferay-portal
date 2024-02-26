/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ViewPage} from '../../ViewPage';
import {VisualizationModesPage} from '../VisualizationModesPage';

export class ListVisualizationModePage {
	readonly fieldSelectModalContainer: Locator;
	readonly page: Page;
	private readonly pageContainer: Locator;
	private readonly viewPage: ViewPage;
	private readonly visualizationModesPage: VisualizationModesPage;

	constructor(page: Page) {
		this.fieldSelectModalContainer = page.locator(
			'.list-visualization-mode-field-select-modal'
		);
		this.page = page;
		this.pageContainer = page.locator('.list-visualization-mode');
		this.viewPage = new ViewPage(page);
		this.visualizationModesPage = new VisualizationModesPage(page);
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

		await this.visualizationModesPage.selectTab('List');

		await this.pageContainer.waitFor();
	}

	async getAssignedFieldLocator({
		listSectionLabel,
	}: {
		listSectionLabel: string;
	}) {
		return this.pageContainer
			.locator('tr')
			.filter({has: this.page.getByText(listSectionLabel)})
			.locator('td.field-name');
	}

	async openAssignFieldModal({listSectionLabel}: {listSectionLabel: string}) {
		await this.pageContainer
			.locator('tr')
			.filter({has: this.page.getByText(listSectionLabel)})
			.getByTitle('Assign Field')
			.click();

		await this.fieldSelectModalContainer
			.getByPlaceholder('Search')
			.waitFor();
	}

	async saveFieldSelection() {

		// Modal for field selection must be open.

		await this.page
			.getByRole('button', {
				exact: true,
				name: 'Save',
			})
			.click();
	}
}

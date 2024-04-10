/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ViewPage} from './view/ViewPage';

export class SortingPage {
	readonly addSortingButton: Locator;
	readonly sortingTable: Locator;
	readonly addSortingDialog: {
		cancelButton: Locator;
		defaultCheckbox: Locator;
		labelInput: Locator;
		saveButton: Locator;
		sortByInput: Locator;
	};
	readonly page: Page;
	readonly viewPage: ViewPage;

	constructor(page: Page) {
		this.addSortingButton = page.getByLabel('New Sort');
		this.sortingTable = page.locator('.table-responsive');
		this.addSortingDialog = {
			cancelButton: page.getByRole('button', {name: 'Cancel'}),
			defaultCheckbox: page.getByLabel('Use as Default Sorting'),
			labelInput: page.getByLabel('Label'),
			saveButton: page.getByRole('button', {name: 'Save'}),
			sortByInput: page.getByLabel('Sort By'),
		};
		this.page = page;
		this.viewPage = new ViewPage(page);
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

		await this.viewPage.selectTab('Sorting');
	}

	async openAddSortingModal() {
		await this.addSortingButton.click();
	}

	async saveAddSortingModal() {
		await this.addSortingDialog.saveButton.click();
	}
}

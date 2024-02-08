/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataSetsPage} from './DataSetsPage';

const DEFAULT_DATA_SET_VIEW_NAME = 'Data Set View Sample';

export class ViewsPage {
	readonly dataSetsPage: DataSetsPage;
	readonly dataSetsViewTable: Locator;
	readonly newDataSetViewButton: Locator;
	readonly newDataSetViewEmptyButton: Locator;
	readonly newDataSetViewModal: {
		descriptionInput: Locator;
		nameInput: Locator;
		saveButton: Locator;
	};
	readonly page: Page;

	constructor(page: Page) {
		this.dataSetsPage = new DataSetsPage(page);
		this.dataSetsViewTable = page.locator('.data-set-content-wrapper');
		this.newDataSetViewButton = page.getByLabel('New Data Set View');
		this.newDataSetViewEmptyButton = page.getByText('New Data Set View');
		this.newDataSetViewModal = {
			descriptionInput: page.getByLabel('Description'),
			nameInput: page.getByLabel('NameRequired'),
			saveButton: page.getByRole('button', {name: 'Save'}),
		};
		this.page = page;
	}

	async goto(dataSetName?: string) {
		await this.dataSetsPage.goto();
		await this.dataSetsPage.gotoSampleDataSet(dataSetName);
	}

	async createSampleDataSetView({
		description,
		name = DEFAULT_DATA_SET_VIEW_NAME,
	}: {
		description?: string;
		name?: string;
	} = {}) {
		await this.newDataSetViewButton.click();

		await this.newDataSetViewModal.nameInput.fill(name);

		if (description) {
			await this.newDataSetViewModal.descriptionInput.fill(description);
		}

		await this.newDataSetViewModal.saveButton.click();
	}

	async gotoSampleDataSetView(name = DEFAULT_DATA_SET_VIEW_NAME) {
		await this.dataSetsViewTable
			.getByRole('link', {
				exact: true,
				name,
			})
			.first()
			.click();

		await this.page
			.getByRole('heading', {
				name,
			})
			.waitFor();
	}
}

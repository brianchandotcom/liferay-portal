/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DataSetsPage} from './DataSetsPage';

export class ViewsPage {
	readonly dataSetsPage: DataSetsPage;
	readonly dataSetsViewTable: Locator;
	readonly newDataSetViewButton: Locator;
	readonly newDataSetViewEmptyButton: Locator;
	readonly newDataSetViewModal: {
		nameInput: Locator;
		saveButton: Locator;
	};
	readonly page: Page;

	constructor(page: Page) {
		this.dataSetsPage = new DataSetsPage(page);
		this.dataSetsViewTable = page.getByText(
			'ViewsData Set View SampleActions'
		);
		this.newDataSetViewButton = page.getByLabel('New Data Set View');
		this.newDataSetViewEmptyButton = page.getByText('New Data Set View');
		this.newDataSetViewModal = {
			nameInput: page.getByLabel('NameRequired'),
			saveButton: page.getByRole('button', {name: 'Save'}),
		};
		this.page = page;
	}

	async goto() {
		await this.dataSetsPage.goto();
		await this.dataSetsPage.gotoSampleDataSet();
	}

	async createSampleDataSetViewUI() {
		await this.newDataSetViewButton.click();

		await this.newDataSetViewModal.nameInput.fill('Data Set View Sample');

		await this.newDataSetViewModal.saveButton.click();
	}

	async gotoSampleDataSetView() {
		await this.dataSetsViewTable
			.getByRole('link', {name: 'Data Set View Sample'})
			.first()
			.click();
	}
}

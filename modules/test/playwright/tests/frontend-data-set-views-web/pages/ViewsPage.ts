/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {DEFAULT_LABEL} from '../utils/constants';
import {DataSetsPage} from './DataSetsPage';

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
	private readonly pageContainer: Locator;

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
		this.pageContainer = page.locator('.fds-views');
	}

	async createDataSetView({
		description,
		name = DEFAULT_LABEL.VIEW,
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

	async goto(dataSetLabel: string = DEFAULT_LABEL.DATA_SET) {
		await this.dataSetsPage.goto();

		await this.dataSetsPage.openDataSet(dataSetLabel);

		await expect(this.pageContainer).toBeInViewport();
	}

	async openDataSetView(viewLabel: string = DEFAULT_LABEL.VIEW) {
		await this.dataSetsViewTable
			.getByRole('link', {
				exact: true,
				name: viewLabel,
			})
			.first()
			.click();

		await this.page
			.getByRole('button', {
				name: 'Details',
			})
			.waitFor();
	}
}

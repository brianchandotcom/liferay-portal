/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ApiHelpers} from '../../../helpers/ApiHelpers';
import {ApplicationsMenuPage} from '../../../pages/product-navigation-applications-menu/ApplicationsMenuPage';

export class DataSetsPage {
	readonly apiHelpers: ApiHelpers;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly basePath: string;
	readonly dataSetsTable: Locator;
	readonly newDataSetButton: Locator;
	readonly newDataSetModal: {
		readonly heading: Locator;
		readonly nameInput: Locator;
		readonly restApplicationField: Locator;
		readonly restApplicationOptions: Locator;
		readonly restEndpointField: Locator;
		readonly restEndpointOptions: Locator;
		readonly restSchemaField: Locator;
		readonly restSchemaOptions: Locator;
		readonly saveButton: Locator;
	};
	readonly page: Page;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.basePath = 'data-set-manager/entries';
		this.dataSetsTable = page.locator('.data-set > div:nth-child(2)');
		this.newDataSetButton = page.getByLabel('New Data Set').first();
		this.newDataSetModal = {
			heading: page.getByRole('heading', {name: 'New Data Set'}),
			nameInput: page.getByLabel('NameRequired'),
			restApplicationField: page.getByLabel('REST ApplicationRequired'),
			restApplicationOptions: page
				.locator('.fds-entries-dropdown-menu')
				.first(),
			restEndpointField: page.getByLabel('REST EndpointRequired'),
			restEndpointOptions: page
				.locator('.fds-entries-dropdown-menu')
				.locator('nth=2'),
			restSchemaField: page.getByLabel('REST SchemaRequired'),
			restSchemaOptions: page
				.locator('.fds-entries-dropdown-menu')
				.locator('nth=1'),
			saveButton: page.getByRole('button', {name: 'Save'}),
		};
		this.page = page;
	}

	async createDataSet({
		name = 'Data Set Sample',
		restApplication = '/data-set-manager/fields',
		restEndpoint = '/',
		restSchema = 'FDSField',
	}: {
		name?: string;
		restApplication?: string;
		restEndpoint?: string;
		restSchema?: string;
	} = {}) {
		await this.newDataSetButton.click();
		await this.newDataSetModal.nameInput.waitFor();

		await this.newDataSetModal.nameInput.fill(name);
		await this.newDataSetModal.restApplicationField.click();
		await this.newDataSetModal.restApplicationOptions
			.getByRole('option', {name: restApplication})
			.click();

		await this.newDataSetModal.restSchemaField.waitFor();
		await this.newDataSetModal.restSchemaField.click();
		await this.newDataSetModal.restSchemaOptions
			.getByRole('option', {name: restSchema})
			.click();
		await this.newDataSetModal.restSchemaField.click();

		await this.newDataSetModal.restEndpointField.waitFor();
		await this.newDataSetModal.restEndpointField.click();
		await this.newDataSetModal.restEndpointOptions
			.getByRole('option', {name: restEndpoint})
			.click();
		await this.newDataSetModal.restEndpointField.click();

		await this.newDataSetModal.saveButton.click();
	}

	async goto() {
		await this.applicationsMenuPage.goToDataSetManager();

		await this.page
			.getByRole('heading', {
				name: 'Data Sets',
			})
			.waitFor();
	}

	async gotoDataSet(name = 'Data Set Sample') {
		await this.page
			.locator('.data-set-content-wrapper')
			.getByRole('link', {name})
			.first()
			.click();

		await this.page
			.getByRole('heading', {
				name,
			})
			.waitFor();
	}

	async deleteDataSet(name = 'Data Set Sample') {
		await this.goto();

		const datasetTestRow = await this.page
			.locator('.data-set-content-wrapper .dnd-tbody .dnd-tr')
			.filter({hasText: name});

		await datasetTestRow
			.first()
			.getByRole('button', {name: 'Actions'})
			.click();

		await this.page.getByRole('menuitem', {name: 'Delete'}).click();

		const deleteModal = await this.page.getByRole('dialog');

		await deleteModal.getByRole('button', {name: 'Delete'}).click();
	}
}

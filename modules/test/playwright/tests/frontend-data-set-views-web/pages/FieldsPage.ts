/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ViewsPage} from './ViewsPage';

export class FieldsPage {
	readonly addFieldsButton: Locator;
	readonly fieldsTable: Locator;
	readonly addFieldsDialog: {
		cancelButton: Locator;
		fields: Locator;
		fieldsTreeview: Locator;
		saveButton: Locator;
	};
	readonly page: Page;
	readonly viewsPage: ViewsPage;

	constructor(page: Page) {
		this.addFieldsButton = page.getByLabel('Add Fields');
		this.fieldsTable = page.locator('.table-responsive');
		this.addFieldsDialog = {
			cancelButton: page.getByRole('button', {name: 'Cancel'}),
			fields: page.locator('.treeview-item'),
			fieldsTreeview: page.locator('.treeview'),
			saveButton: page.getByRole('button', {name: 'Save'}),
		};
		this.page = page;
		this.viewsPage = new ViewsPage(page);
	}

	async goto({
		dataSetName,
		dataSetViewName,
	}: {
		dataSetName?: string;
		dataSetViewName?: string;
	} = {}) {
		await this.viewsPage.goto(dataSetName);
		await this.viewsPage.gotoDataSetView(dataSetViewName);

		await this.page
			.getByRole('button', {exact: true, name: 'Fields'})
			.click();
	}

	async openAddFieldsModal() {
		await this.addFieldsButton.click();

		await this.addFieldsDialog.fields.first().waitFor();
	}

	async saveAddFieldsModal() {
		await this.addFieldsDialog.saveButton.click();
	}

	async addRootField(field: string) {
		await this.addFieldsDialog.fields
			.getByText(field, {exact: true})
			.click();
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

	async addChildField(path: string[], field: string) {
		this.openParentField(path);

		await this.page
			.locator(`[data-id$="${path.join('.')}.${field}"]`)
			.getByText(field, {exact: true})
			.check();
	}
}

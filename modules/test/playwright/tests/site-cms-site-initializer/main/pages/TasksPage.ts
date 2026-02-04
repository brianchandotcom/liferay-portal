/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../../utils/portletUrls';
import {DataSetPage} from './DataSetPage';

interface ExecItemActionArgs {
	action: 'Assign Task' | 'Delete' | 'Update Due Date' | 'Update State';
	filter: string;
}

export class TasksPage {
	readonly dataSetFragmentPage: DataSetPage;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly updateDueDateDialog: Locator;
	readonly updateStateDialog: Locator;
	readonly updateStateSelector: Locator;

	constructor(page: Page) {
		this.page = page;

		this.dataSetFragmentPage = new DataSetPage(page);
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.updateDueDateDialog = page.getByRole('dialog', {
			name: 'Update Due Date',
		});
		this.updateStateDialog = page.getByRole('dialog', {
			name: 'Update State',
		});
		this.updateStateSelector = this.updateStateDialog.getByRole('combobox');
	}

	getItem(filter: string) {
		return this.dataSetFragmentPage.getRow(filter);
	}

	async execBulkItemAction(action: string) {
		await this.dataSetFragmentPage.execBulkItemAction({action});
	}

	async execItemAction({action, filter}: ExecItemActionArgs) {
		await this.dataSetFragmentPage.execItemAction({
			action,
			filter,
		});
	}

	async goto() {
		await this.page.goto(PORTLET_URLS.cmpTasks);
	}
}

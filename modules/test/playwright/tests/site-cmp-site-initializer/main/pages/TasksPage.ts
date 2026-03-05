/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../../utils/portletUrls';
import {DataSetPage} from '../../../site-cms-site-initializer/main/pages/DataSetPage';

interface ExecItemActionArgs {
	action: 'Assign Task' | 'Delete' | 'Update Due Date' | 'Update State';
	filter: string;
}

export class TasksPage {
	readonly addTaskKanbanButton: Locator;
	readonly assetTagNameField: Locator;
	readonly assignTaskToDialog: Locator;
	readonly dataSetFragmentPage: DataSetPage;
	readonly deleteButton: Locator;
	readonly dropdownKanbanViewButton: Locator;
	readonly dropdownTableViewButton: Locator;
	readonly kanbanViewButton: Locator;
	readonly page: Page;
	readonly projectTitleButton: Locator;
	readonly saveButton: Locator;
	readonly tableViewButton: Locator;
	readonly titleInput: Locator;
	readonly updateDueDateDialog: Locator;
	readonly updateStateDialog: Locator;
	readonly updateStateSelector: Locator;

	constructor(page: Page) {
		this.page = page;

		this.addTaskKanbanButton = page
			.getByRole('button', {name: 'Add Task'})
			.first();
		this.assetTagNameField = page
			.locator('span')
			.filter({hasText: 'L_CMP_TASK_'})
			.first();

		this.assignTaskToDialog = page.getByRole('dialog', {
			name: 'Assign Tasks to',
		});
		this.dataSetFragmentPage = new DataSetPage(page);
		this.deleteButton = page.getByRole('button', {name: 'Delete'});
		this.dropdownKanbanViewButton = page.getByRole('option', {
			name: 'Kanban',
		});
		this.dropdownTableViewButton = page.getByRole('option', {
			name: 'Table',
		});
		this.kanbanViewButton = page.getByRole('combobox', {
			name: 'Kanban View Selected',
		});
		this.projectTitleButton = page.locator(
			'#r_cmpProjectToCMPTasks_c_cmpProjectId'
		);
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.tableViewButton = page.getByRole('combobox', {
			name: 'Table View Selected',
		});
		this.titleInput = page.locator('#title');
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

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {WorkflowTasksPage} from './WorkflowTasksPage';

export class WorkflowTaskDetailsPage {
	readonly approveMenuItem: Locator;
	readonly doneButton: Locator;
	readonly page: Page;
	readonly reviewActionMenu: Locator;
	readonly reviewComment: Locator;
	readonly workflowTasksPage: WorkflowTasksPage;

	constructor(page: Page) {
		this.approveMenuItem = page.getByRole('menuitem', {name: 'approve'});
		this.doneButton = page.getByRole('button', {name: 'Done'});
		this.reviewActionMenu = page.locator(
			'[id="_com_liferay_portal_workflow_task_web_portlet_MyWorkflowTaskPortlet_kldx___menu"]'
		);
		this.reviewComment = page.getByRole('textbox', {name: 'Comment'});
		this.page = page;
		this.workflowTasksPage = new WorkflowTasksPage(page);
	}

	async clickDoneButton() {
		await this.doneButton.click();

		await waitForSuccessAlert(this.page);
	}

	async goTo(assetTitle: string) {
		await this.workflowTasksPage.goto();
		const assetLink = this.page.getByRole('link', {name: assetTitle});
		await assetLink.click({force: true});
	}
}

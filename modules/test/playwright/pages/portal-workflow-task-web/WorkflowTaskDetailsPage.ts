/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {WorkflowTasksPage} from './WorkflowTasksPage';

export class WorkflowTaskDetailsPage {
	readonly activitiesButton: Locator;
	readonly approveMenuItem: Locator;
	readonly assignToDialogIFRAME: FrameLocator;
	readonly assignToMenuItem: Locator;
	readonly assignToSingleSelect: Locator;
	readonly commentBox: Locator;
	readonly commentSectionButton: Locator;
	readonly detailsMessage: Locator;
	readonly doneAssigneeButton: Locator;
	readonly doneButton: Locator;
	readonly page: Page;
	readonly previewMessageBoards: Locator;
	readonly rejectMenuItem: Locator;
	readonly reply: Locator;
	readonly reviewActionMenu: Locator;
	readonly reviewComment: Locator;
	readonly subscribeButton: Locator;
	readonly viewButton: Locator;
	readonly viewUsagesButton: Locator;
	readonly workflowTasksPage: WorkflowTasksPage;

	constructor(page: Page) {
		this.activitiesButton = page.getByRole('button', {name: 'Activities'});
		this.approveMenuItem = page.getByRole('menuitem', {name: 'approve'});
		this.assignToDialogIFRAME = page.frameLocator(
			'iframe[name="_com_liferay_portal_workflow_task_web_portlet_MyWorkflowTaskPortlet_assignToDialog_iframe_"]'
		);
		this.assignToMenuItem = page.getByRole('link', {name: 'Assign to...'});
		this.assignToSingleSelect =
			this.assignToDialogIFRAME.getByLabel('Assign to');
		this.commentBox = page.frameLocator('iframe').getByRole('textbox');
		this.commentSectionButton = page.getByRole('button', {
			name: 'Comments',
		});
		this.detailsMessage = page.getByLabel(
			'Ask a user to work on the item.'
		);
		this.doneAssigneeButton = this.assignToDialogIFRAME.getByRole(
			'button',
			{name: 'Done'}
		);
		this.doneButton = page.getByRole('button', {name: 'Done'});
		this.page = page;
		this.previewMessageBoards = page.getByRole('button', {
			name: 'Preview of Message Boards',
		});
		this.rejectMenuItem = page.getByRole('menuitem', {name: 'reject'});
		this.reply = page.getByRole('button', {name: 'Reply'});
		this.reviewActionMenu = page.locator(
			'[id="_com_liferay_portal_workflow_task_web_portlet_MyWorkflowTaskPortlet_kldx___menu"]'
		);
		this.reviewComment = page.getByRole('textbox', {name: 'Comment'});
		this.subscribeButton = page.getByLabel('Subscribe to Comments');
		this.viewButton = page.getByRole('link', {exact: true, name: 'View'});
		this.viewUsagesButton = page.getByRole('link', {name: 'View Usages'});
		this.workflowTasksPage = new WorkflowTasksPage(page);
	}

	async clickDoneAssigneeButton() {
		await this.doneAssigneeButton.click();

		await waitForSuccessAlert(this.page);
	}

	async clickDoneButton() {
		await this.doneButton.click();

		await waitForSuccessAlert(this.page);
	}

	async goTo(assetTitle: string) {
		await this.workflowTasksPage.goto();

		await this.selectAsset(assetTitle);
	}

	async selectAsset(assetTitle: string) {
		const assetLink = this.page.getByRole('link', {name: assetTitle});
		await assetLink.click({force: true});
	}

	async selectAssignee(assignee: string) {
		await this.assignToSingleSelect.selectOption(assignee);
	}

	async writeTaskComment(threadTitle: string, comment: string) {
		await this.selectAsset(threadTitle);

		await this.commentSectionButton.first().waitFor();

		await this.commentSectionButton.first().click();

		await this.subscribeButton.click();

		await this.commentSectionButton.first().waitFor();

		await this.commentSectionButton.first().click();

		await this.commentBox.fill(comment);

		await this.reply.click();
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../fixtures/globalMenuPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {workflowPagesTest} from '../../../fixtures/workflowPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {cmsPagesTest} from '../../site-cms-site-initializer/main/fixtures/cmsPagesTest';
import {cmpPagesTest} from './fixtures/cmpPagesTest';

const test = mergeTests(
	cmpPagesTest,
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-58677': {enabled: true},
	}),
	globalMenuPagesTest,
	loginTest(),
	workflowPagesTest
);

const cmpProject = 'cmp/projects';
const cmpTask = 'cmp/tasks';
let project;
const tasks = [];
let taskNames: string[] = [];
let taskTags: string[] = [];

const generateTaskTag = () =>
	'L_CMP_TASK_' + Math.floor(Math.random() * 100000000);

test.beforeEach(async ({apiHelpers}) => {
	taskNames = [getRandomString(), getRandomString(), getRandomString()];
	taskTags = [];

	project = await apiHelpers.objectEntry.postObjectEntry(
		{
			title: getRandomString(),
		},
		cmpProject
	);

	for (const taskName of taskNames) {
		const taskTag = generateTaskTag();

		taskTags.push(taskTag);

		const task = await apiHelpers.objectEntry.postObjectEntry(
			{
				keywords: [taskTag],
				r_cmpProjectToCMPTasks_c_cmpProjectId: project.id,
				title: taskName,
			},
			cmpTask,
			project.scopeKey
		);
		tasks.push(task);
	}
});

test.afterEach(async ({apiHelpers}) => {
	if (project) {
		await apiHelpers.objectEntry.deleteObjectEntry(
			cmpProject,
			String(project.id)
		);
	}

	const bulkActionTasks = 'cms/bulk-action-tasks';

	const bulkTasks =
		await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
			bulkActionTasks
		);

	for (const bulkTask of bulkTasks.items) {
		await apiHelpers.objectEntry.deleteObjectEntry(
			bulkActionTasks,
			bulkTask.id
		);
	}
});

test('Bulk delete tasks', {tag: ['@LPD-75299']}, async ({page, tasksPage}) => {
	await test.step('Select 2 task and delete them using the Bulk Action', async () => {
		await tasksPage.goto();

		await tasksPage.projectTasksTab.click();

		await tasksPage
			.getItem(taskNames[0])
			.locator('input[title="Select Item"]')
			.check();
		await tasksPage
			.getItem(taskNames[1])
			.locator('input[title="Select Item"]')
			.check();

		await tasksPage.execBulkItemAction('Delete');

		await tasksPage.dialogDeleteButton.click();

		await waitForAlert(page, 'Info:Delete action started for 2 tasks.', {
			autoClose: true,
			type: 'info',
		});

		await expect(async () => {
			await tasksPage.goto();

			await expect(page.getByLabel(taskNames[0])).toBeHidden();
			await expect(page.getByLabel(taskNames[1])).toBeHidden();
			await expect(page.getByLabel(taskNames[2])).toBeVisible();
		}).toPass({timeout: 10000});
	});
});

test(
	'Bulk update the assignee of an task',
	{tag: ['@LPD-75299']},
	async ({page, tasksPage}) => {
		await test.step('Select 2 task and update its assignee using the Bulk Action', async () => {
			await tasksPage.goto();

			await tasksPage.projectTasksTab.click();

			await tasksPage
				.getItem(taskNames[0])
				.locator('input[title="Select Item"]')
				.check();
			await tasksPage
				.getItem(taskNames[1])
				.locator('input[title="Select Item"]')
				.check();

			await tasksPage.execBulkItemAction('Assign to...');

			await expect(tasksPage.assignTaskToDialog).toBeVisible();

			await page
				.getByPlaceholder('Unassigned')
				.fill('Asset Library Content Reviewer');

			await page
				.getByRole('option', {
					name: 'Asset Library Content Reviewer',
				})
				.click();

			await tasksPage.saveButton.click();

			await expect(async () => {
				await tasksPage.goto();

				await expect(
					page.getByRole('row', {
						name: 'Asset Library Content Reviewer',
					})
				).toHaveCount(2, {timeout: 1000});
			}).toPass({timeout: 10000});
		});
	}
);

test(
	'Bulk update the due date of an task',
	{tag: ['@LPD-75299']},
	async ({page, tasksPage}) => {
		await test.step('Select 2 task and update its due date using the Bulk Action', async () => {
			await tasksPage.goto();

			await tasksPage.projectTasksTab.click();

			await tasksPage
				.getItem(taskNames[0])
				.locator('input[title="Select Item"]')
				.check();
			await tasksPage
				.getItem(taskNames[1])
				.locator('input[title="Select Item"]')
				.check();

			await tasksPage.execBulkItemAction('Update Due Date');

			await expect(tasksPage.updateDueDateDialog).toBeVisible();

			const locale = await page.evaluate(() => {
				return Liferay.ThemeDisplay.getBCP47LanguageId();
			});

			const tomorrow = new Date();

			tomorrow.setDate(tomorrow.getDate() + 1);

			const dateString = tomorrow.toLocaleDateString(locale, {
				day: '2-digit',
				month: '2-digit',
				year: 'numeric',
			});

			await page.getByPlaceholder('MM/DD/YYYY').fill(dateString);

			await tasksPage.saveButton.click();

			await expect(async () => {
				await tasksPage.goto();

				const expectedDate = tomorrow.toLocaleDateString(locale, {
					day: 'numeric',
					month: 'short',
					year: 'numeric',
				});

				await expect(
					page.getByRole('row', {name: expectedDate})
				).toHaveCount(2);
			}).toPass({timeout: 10000});
		});
	}
);

test(
	'Bulk update the state of an task',
	{tag: ['@LPD-75299']},
	async ({page, tasksPage}) => {
		await test.step('Select 2 task and update its state using the Bulk Action', async () => {
			await tasksPage.goto();

			await tasksPage.projectTasksTab.click();

			await tasksPage
				.getItem(taskNames[0])
				.locator('input[title="Select Item"]')
				.check();
			await tasksPage
				.getItem(taskNames[1])
				.locator('input[title="Select Item"]')
				.check();

			await tasksPage.execBulkItemAction('Update State');

			await expect(tasksPage.updateStateDialog).toBeVisible();

			await tasksPage.updateStateSelector.click();

			await page.getByRole('option', {name: 'Blocked'}).click();

			await tasksPage.saveButton.click();

			await expect(async () => {
				await tasksPage.goto();

				await expect(
					page.getByRole('row', {name: 'Blocked'})
				).toHaveCount(2);
			}).toPass({timeout: 10000});
		});
	}
);

test(
	'Ensure that the "All Tasks" tab disables highlighted bulk actions when project and workflow tasks are selected together',
	{tag: ['@LPD-88846']},
	async ({apiHelpers, assignWorkflowToAssetType, page, tasksPage}) => {
		await assignWorkflowToAssetType('Single Approver', 'Blog');

		const blogTitle = getRandomString();

		await apiHelpers.objectEntry.postObjectEntry(
			{
				keywords: [taskTags[0]],
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: blogTitle,
			},
			'cms/blogs',
			'Default'
		);

		await tasksPage.goto();

		await tasksPage.allTasksTab.click();

		await tasksPage.getItem(taskNames[0]).getByLabel('Select Item').check();

		await tasksPage.getItem(blogTitle).getByLabel('Select Item').check();

		await expect(
			page.getByRole('button', {name: 'Update Due Date'})
		).toBeDisabled();
		await expect(
			page.getByRole('button', {name: 'Assign to...'})
		).toBeDisabled();
		await expect(
			page.getByRole('button', {name: 'Update State'})
		).toBeDisabled();
		await expect(page.getByRole('button', {name: 'Delete'})).toBeDisabled();
	}
);

test(
	'Kanban View Task creation generates a tag',
	{tag: ['@LPD-80545']},
	async ({apiHelpers, page, tasksPage}) => {
		const cmpProjectApplicationName = 'cmp/projects';
		const cmpTaskApplicationName = 'cmp/tasks';

		const project = await apiHelpers.objectEntry.postObjectEntry(
			{
				title: getRandomString(),
			},
			cmpProjectApplicationName
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{
				r_cmpProjectToCMPTasks_c_cmpProjectId: project.id,
				title: getRandomString(),
			},
			cmpTaskApplicationName,
			project.scopeKey
		);

		const taskTitle = getRandomString();

		await test.step('Go to tasks page and switch to kanban view', async () => {
			await tasksPage.goto();

			await tasksPage.projectTasksTab.click();

			await tasksPage.tableViewButton.click();

			await tasksPage.dropdownKanbanViewButton.click();
		});

		await test.step('Add a new task', async () => {
			await tasksPage.addTaskKanbanButton.click();

			await tasksPage.titleInput.fill(taskTitle);

			await tasksPage.projectTitleButton.click();

			await page.getByRole('option', {name: project.title}).click();

			await tasksPage.saveButton.click();
		});

		await test.step('Go to tasks page and select the created task', async () => {
			await tasksPage.kanbanViewButton.click();

			await tasksPage.dropdownTableViewButton.click();

			await page.getByRole('link', {name: taskTitle}).click();
		});

		await test.step("Check if the created task's AssetTagName follows the pattern", async () => {
			await expect(tasksPage.assetTagNameField).toContainText(
				'L_CMP_TASK_'
			);
		});
	}
);

test(
	'View selector is visible only on Project Tasks tab',
	{tag: ['@LPD-88846']},
	async ({apiHelpers, assignWorkflowToAssetType, tasksPage}) => {
		await assignWorkflowToAssetType('Single Approver', 'Blog');

		const blogTitle = getRandomString();

		await apiHelpers.objectEntry.postObjectEntry(
			{
				keywords: [taskTags[0]],
				objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
				title: blogTitle,
			},
			'cms/blogs',
			'Default'
		);

		await tasksPage.goto();

		await tasksPage.allTasksTab.click();

		await expect(tasksPage.viewSelectorButton).toBeHidden();

		await tasksPage.projectTasksTab.click();

		await expect(tasksPage.viewSelectorButton).toBeVisible();

		await tasksPage.workflowTasksTab.click();

		await expect(tasksPage.viewSelectorButton).toBeHidden();
	}
);

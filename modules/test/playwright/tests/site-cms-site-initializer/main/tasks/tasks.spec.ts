/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {waitForAlert} from '../../../../utils/waitForAlert';
import {cmsPagesTest} from '../fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-11235': {enabled: true},
		'LPD-17564': {enabled: true},
		'LPD-58677': {enabled: true},
	}),
	loginTest()
);

test(
	'Bulk update the due date of an task',
	{tag: ['@LPD-75299']},
	async ({apiHelpers, page, tasksPage}) => {
		const cmpProject = 'cmp/projects';
		const cmpTask = 'cmp/tasks';

		const tasks = [];
		const taskNames = [
			getRandomString(),
			getRandomString(),
			getRandomString(),
		];

		const assetLibrary =
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: getRandomString(),
				settings: {},
				type: 'Project',
			});

		const project = await apiHelpers.objectEntry.postObjectEntry(
			{
				title: getRandomString(),
			},
			cmpProject,
			assetLibrary.name
		);

		for (const taskName of taskNames) {
			const task = await apiHelpers.objectEntry.postObjectEntry(
				{
					r_cmpProjectToCMPTasks_c_cmpProjectId: project.id,
					title: taskName,
				},
				cmpTask,
				project.scopeKey
			);
			tasks.push(task);
		}

		try {
			await test.step('Select 2 task and update its due date using the Bulk Action', async () => {
				await tasksPage.goto();

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

				await waitForAlert(
					page,
					'Info:Due date update action started for 2 task.',
					{
						autoClose: true,
						type: 'info',
					}
				);

				await tasksPage.goto();

				const expectedDate = tomorrow.toLocaleDateString(locale, {
					day: 'numeric',
					month: 'short',
					year: 'numeric',
				});

				await expect(
					page.getByRole('row', {name: expectedDate})
				).toHaveCount(2);
			});
		}
		finally {
			await tasksPage.goto();

			if (project) {
				await apiHelpers.objectEntry.deleteObjectEntry(
					cmpProject,
					String(project.id)
				);
			}

			if (tasks) {
				for (const task of tasks) {
					await apiHelpers.objectEntry.deleteObjectEntry(
						cmpTask,
						task.id
					);
				}
			}

			const bulkActionTaskItems = 'cms/bulk-action-task-items';

			const bulkTaskItems =
				await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
					bulkActionTaskItems
				);

			for (const bulkTaskItem of bulkTaskItems.items) {
				await apiHelpers.objectEntry.deleteObjectEntry(
					bulkActionTaskItems,
					bulkTaskItem.id
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
		}
	}
);

test(
	'Bulk update the assignee of an task',
	{tag: ['@LPD-75299']},
	async ({apiHelpers, page, tasksPage}) => {
		const cmpProject = 'cmp/projects';
		const cmpTask = 'cmp/tasks';

		const tasks = [];
		const taskNames = [
			getRandomString(),
			getRandomString(),
			getRandomString(),
		];

		const assetLibrary =
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: getRandomString(),
				settings: {},
				type: 'Project',
			});

		const project = await apiHelpers.objectEntry.postObjectEntry(
			{
				title: getRandomString(),
			},
			cmpProject,
			assetLibrary.name
		);

		for (const taskName of taskNames) {
			const task = await apiHelpers.objectEntry.postObjectEntry(
				{
					r_cmpProjectToCMPTasks_c_cmpProjectId: project.id,
					title: taskName,
				},
				cmpTask,
				project.scopeKey
			);
			tasks.push(task);
		}

		try {
			await test.step('Select 2 task and update its assignee using the Bulk Action', async () => {
				await tasksPage.goto();

				await tasksPage
					.getItem(taskNames[0])
					.locator('input[title="Select Item"]')
					.check();
				await tasksPage
					.getItem(taskNames[1])
					.locator('input[title="Select Item"]')
					.check();

				await tasksPage.execBulkItemAction('Assign Task');

				await expect(tasksPage.assignTaskToDialog).toBeVisible();

				await page.getByPlaceholder('Unassigned').fill('Publications Viewer');

				await page.getByRole('option', {name: 'Publications Viewer'}).click();

				await tasksPage.saveButton.click();

				await expect(async()=>{

					await tasksPage.goto();
	
					await expect(
						page.getByRole('row', {name: 'Publications Viewer'})
					).toHaveCount(2, {timeout:1000});

				}).toPass({timeout:10000});
			});
		}
		finally {
			await tasksPage.goto();

			if (project) {
				await apiHelpers.objectEntry.deleteObjectEntry(
					cmpProject,
					String(project.id)
				);
			}

			if (tasks) {
				for (const task of tasks) {
					await apiHelpers.objectEntry.deleteObjectEntry(
						cmpTask,
						task.id
					);
				}
			}

			const bulkActionTaskItems = 'cms/bulk-action-task-items';

			const bulkTaskItems =
				await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
					bulkActionTaskItems
				);

			for (const bulkTaskItem of bulkTaskItems.items) {
				await apiHelpers.objectEntry.deleteObjectEntry(
					bulkActionTaskItems,
					bulkTaskItem.id
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
		}
	}
);

test(
	'Bulk update the state of an task',
	{tag: ['@LPD-75299']},
	async ({apiHelpers, page, tasksPage}) => {
		const cmpProject = 'cmp/projects';
		const cmpTask = 'cmp/tasks';

		const tasks = [];
		const taskNames = [
			getRandomString(),
			getRandomString(),
			getRandomString(),
		];

		const assetLibrary =
			await apiHelpers.headlessAssetLibrary.createAssetLibrary({
				name: getRandomString(),
				settings: {},
				type: 'Project',
			});

		const project = await apiHelpers.objectEntry.postObjectEntry(
			{
				title: getRandomString(),
			},
			cmpProject,
			assetLibrary.name
		);

		for (const taskName of taskNames) {
			const task = await apiHelpers.objectEntry.postObjectEntry(
				{
					r_cmpProjectToCMPTasks_c_cmpProjectId: project.id,
					title: taskName,
				},
				cmpTask,
				project.scopeKey
			);
			tasks.push(task);
		}
		try {
			await test.step('Select 2 task and update its state using the Bulk Action', async () => {
				await tasksPage.goto();

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

				await waitForAlert(
					page,
					'Info:State update action started for 2 task.',
					{
						autoClose: true,
						type: 'info',
					}
				);

				await tasksPage.goto();

				await expect(
					page.getByRole('row', {name: 'Blocked'})
				).toHaveCount(2);
			});
		}
		finally {
			await tasksPage.goto();

			if (project) {
				await apiHelpers.objectEntry.deleteObjectEntry(
					cmpProject,
					String(project.id)
				);
			}

			if (tasks) {
				for (const task of tasks) {
					await apiHelpers.objectEntry.deleteObjectEntry(
						cmpTask,
						task.id
					);
				}
			}

			const bulkActionTaskItems = 'cms/bulk-action-task-items';

			const bulkTaskItems =
				await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
					bulkActionTaskItems
				);

			for (const bulkTaskItem of bulkTaskItems.items) {
				await apiHelpers.objectEntry.deleteObjectEntry(
					bulkActionTaskItems,
					bulkTaskItem.id
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
		}
	}
);

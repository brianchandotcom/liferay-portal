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
import {addSpaceUser} from '../../../utils/addSpaceUser';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {performUserSwitch} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';
import {cmsPagesTest} from '../../site-cms-site-initializer/main/fixtures/cmsPagesTest';
import {cmpPagesTest} from './fixtures/cmpPagesTest';

const test = mergeTests(
	cmpPagesTest,
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-58677': {enabled: true},
		'LPD-69885': {enabled: true},
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

/**
 * Formats a date as its long month name and year.
 * For example: getMonthYearLabel(new Date(2026, 5, 15)) // "June 2026"
 */
const getMonthYearLabel = (date: Date): string =>
	date.toLocaleDateString('en-US', {month: 'long', year: 'numeric'});

/**
 * Formats a date as a "YYYY-MM-DD" string.
 * For example: toDateString(new Date(2026, 5, 15)) // "2026-06-15"
 */
const toDateString = (date: Date): string =>
	`${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;

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

			await expect(tasksPage.getItem(taskNames[0])).toBeHidden();
			await expect(tasksPage.getItem(taskNames[1])).toBeHidden();
			await expect(tasksPage.getItem(taskNames[2])).toBeVisible();
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
	'Verify task visibility across Global Tasks tabs based on user permission',
	{tag: ['@LPD-88846']},
	async ({apiHelpers, assignWorkflowToAssetType, page, tasksPage}) => {
		await assignWorkflowToAssetType('Single Approver', 'Blog');

		const spaces =
			await apiHelpers.headlessAssetLibrary.getAssetLibrariesPage();

		const defaultSpace = spaces.find((space) => space.name === 'Default');

		const user = await addSpaceUser(
			apiHelpers,
			defaultSpace.externalReferenceCode,
			'Asset Library Administrator'
		);

		await apiHelpers.headlessAssetLibrary.putAssetLibraryUserAccount(
			project.systemProperties.scope.externalReferenceCode,
			user.externalReferenceCode
		);

		await apiHelpers.headlessAssetLibrary.putAssetLibraryUserAccountRoles(
			project.systemProperties.scope.externalReferenceCode,
			user.externalReferenceCode,
			['Asset Library Administrator']
		);

		const assignedBlogTitle = getRandomString();
		const unassignedBlogTitle = getRandomString();

		await test.step('Create two CMS Blog entries; both generate KaleoTaskInstanceTokens', async () => {
			await apiHelpers.objectEntry.postObjectEntry(
				{
					keywords: [taskTags[0]],
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: assignedBlogTitle,
				},
				'cms/blogs',
				'Default'
			);

			await apiHelpers.objectEntry.postObjectEntry(
				{
					keywords: [taskTags[0]],
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: unassignedBlogTitle,
				},
				'cms/blogs',
				'Default'
			);
		});

		await test.step('Assign a workflow tasks to admin user', async () => {
			await tasksPage.goto();

			await tasksPage
				.getItem(assignedBlogTitle)
				.getByRole('button')
				.click();

			await page.getByRole('menuitem', {name: 'Assign to Me'}).click();

			await tasksPage.saveButton.click();

			await page.reload();
		});

		await test.step('Admin sees tasks separated by tab regardless of assignment', async () => {
			await tasksPage.goto();

			await tasksPage.allTasksTab.click();

			await expect(tasksPage.getItem(taskNames[0])).toBeVisible();
			await expect(tasksPage.getItem(assignedBlogTitle)).toBeVisible();
			await expect(tasksPage.getItem(unassignedBlogTitle)).toBeVisible();

			await tasksPage.projectTasksTab.click();

			await expect(tasksPage.getItem(taskNames[0])).toBeVisible();
			await expect(tasksPage.getItem(assignedBlogTitle)).toBeHidden();
			await expect(tasksPage.getItem(unassignedBlogTitle)).toBeHidden();

			await tasksPage.workflowTasksTab.click();

			await expect(tasksPage.getItem(taskNames[0])).toBeHidden();
			await expect(tasksPage.getItem(assignedBlogTitle)).toBeVisible();
			await expect(tasksPage.getItem(unassignedBlogTitle)).toBeVisible();
		});

		await test.step('Space admin sees tasks separated by tab based on assignment', async () => {
			await performUserSwitch(page, user.alternateName);

			await tasksPage.goto();

			await tasksPage.allTasksTab.click();

			await expect(tasksPage.getItem(taskNames[0])).toBeVisible();
			await expect(tasksPage.getItem(assignedBlogTitle)).toBeHidden();
			await expect(tasksPage.getItem(unassignedBlogTitle)).toBeVisible();

			await tasksPage.projectTasksTab.click();

			await expect(tasksPage.getItem(taskNames[0])).toBeVisible();
			await expect(tasksPage.getItem(assignedBlogTitle)).toBeHidden();
			await expect(tasksPage.getItem(unassignedBlogTitle)).toBeHidden();

			await tasksPage.workflowTasksTab.click();

			await expect(tasksPage.getItem(taskNames[0])).toBeHidden();
			await expect(tasksPage.getItem(assignedBlogTitle)).toBeHidden();
			await expect(tasksPage.getItem(unassignedBlogTitle)).toBeVisible();
		});

		await performUserSwitch(page, 'test');
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

test(
	'Calendar view shows tasks by due date and navigates between months',
	{tag: ['@LPD-69885']},
	async ({apiHelpers, page, projectPage, projectsPage, tasksPage}) => {
		const now = new Date();

		const currentLabel = getMonthYearLabel(now);
		const nextLabel = getMonthYearLabel(
			new Date(now.getFullYear(), now.getMonth() + 1, 1)
		);
		const previousLabel = getMonthYearLabel(
			new Date(now.getFullYear(), now.getMonth() - 1, 1)
		);
		const todayDate = toDateString(now);

		const tomorrow = new Date();

		tomorrow.setDate(tomorrow.getDate() + 1);

		const dueDate = toDateString(tomorrow);

		const taskTitleBase = getRandomString();

		const taskTitles = Array.from(
			{length: 3},
			(_, index) => `${taskTitleBase}-${index}`
		);

		await test.step('Create three tasks on the same due date', async () => {
			for (const title of taskTitles) {
				await apiHelpers.objectEntry.postObjectEntry(
					{
						dueDate: `${dueDate}T00:00:00Z`,
						r_cmpProjectToCMPTasks_c_cmpProjectId: project.id,
						title,
					},
					cmpTask,
					project.scopeKey
				);
			}
		});

		const {calendarView} = tasksPage;

		await test.step('View the project and open its Tasks tab', async () => {
			await projectsPage.goto();

			await projectsPage.getProject(project.title).click();

			await projectPage.tasksTab.click();
		});

		await test.step('Calendar view is available and can be selected', async () => {
			await tasksPage.tableViewButton.click();

			await expect(calendarView.viewOption).toBeVisible();

			await calendarView.viewOption.click();
		});

		await test.step('Calendar shows the current month and year', async () => {
			await expect(calendarView.title).toBeVisible();

			await expect(calendarView.title).toContainText(currentLabel);
		});

		await test.step('Next and previous buttons change the title', async () => {
			await calendarView.nextMonthButton.click();

			await expect(calendarView.title).toContainText(nextLabel);

			await calendarView.previousMonthButton.click();
			await calendarView.previousMonthButton.click();

			await expect(calendarView.title).toContainText(previousLabel);
		});

		await test.step('Date picker jumps the calendar to the selected month', async () => {
			await calendarView.title.click();

			await expect(calendarView.datePickerMenu).toBeVisible();

			await calendarView.datePickerMenu
				.getByText('15', {exact: true})
				.click();

			await expect(calendarView.title).toContainText(currentLabel);
		});

		await test.step('Today button returns to the current month', async () => {
			await calendarView.previousMonthButton.click();

			await expect(calendarView.title).toContainText(previousLabel);

			await calendarView.todayButton.click();

			await expect(calendarView.title).toContainText(currentLabel);
		});

		await test.step('The current date is highlighted', async () => {
			await expect(page.locator('.fc-day-today')).toBeVisible();

			await expect(page.locator('.fc-day-today')).toHaveAttribute(
				'data-date',
				todayDate
			);
		});

		await test.step('The tasks appear on their due date', async () => {
			await expect(
				page
					.locator(`[data-date="${dueDate}"]`)
					.getByText(taskTitles[0], {exact: true})
			).toBeVisible();
		});

		await test.step('A More link reveals the tasks hidden in a dense day', async () => {
			const dayCell = page.locator(`[data-date="${dueDate}"]`);

			await expect(
				dayCell.getByText(taskTitles[1], {exact: true})
			).toBeHidden();
			await expect(
				dayCell.getByText(taskTitles[2], {exact: true})
			).toBeHidden();

			await expect(calendarView.moreLinkButton).toBeVisible();

			await clickAndExpectToBeVisible({
				target: calendarView.moreLinkPopover,
				trigger: calendarView.moreLinkButton,
			});

			await expect(
				calendarView.moreLinkPopover.getByText(taskTitles[1], {
					exact: true,
				})
			).toBeVisible();
			await expect(
				calendarView.moreLinkPopover.getByText(taskTitles[2], {
					exact: true,
				})
			).toBeVisible();
		});
	}
);

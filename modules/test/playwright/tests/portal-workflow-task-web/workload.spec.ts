/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {loginTest} from '../../fixtures/loginTest';
import {notificationPagesTest} from '../../fixtures/notificationPagesTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import clearInvalidUserNotifications from '../../utils/clearInvalidUserNotifications';
import retryGetWorkflowTasksBySubmittingUser from '../../utils/retryGetWorkflowTasksBySubmittingUser';
import {blogsPagesTest} from '../blogs-web/fixtures/blogsPagesTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	apiHelpersTest,
	blogsPagesTest,
	loginTest(),
	notificationPagesTest,
	workflowPagesTest
);

interface CreatedEntities {
	blogPosts?: TBlogPost[];
	users?: TUserAccount[];
}

const createdEntities: CreatedEntities = {};

test.afterEach(async ({apiHelpers, configurationTabPage, page}) => {
	await configurationTabPage.goTo();

	await configurationTabPage.unassignWorkflowFromAssetType('Blogs Entry');

	if (createdEntities.users?.length) {
		for (const user of createdEntities.users) {
			await apiHelpers.headlessAdminUser.deleteUserAccount(
				Number(user.id)
			);
		}
	}

	delete createdEntities.users;

	if (createdEntities.blogPosts?.length) {
		for (const blog of createdEntities.blogPosts) {
			await apiHelpers.headlessDelivery.deleteBlog(blog.id);
		}
	}

	delete createdEntities.blogPosts;

	await clearInvalidUserNotifications(page);
});

test('view workload distribution for all assignees', async ({
	allItemsPage,
	apiHelpers,
	applicationsMenuPage,
	configurationTabPage,
	metricsPage,
	page,
}) => {
	const NEW_PAGE_SIZE = 4;
	const NUMBER_OF_USERS_AND_TASKS = 5;
	const WORKFLOW_DEFINITION_NAME = 'Single Approver';

	const blogPosts = [];
	const users = [];

	await configurationTabPage.goTo();

	await configurationTabPage.assignWorkflowToAssetType(
		WORKFLOW_DEFINITION_NAME,
		'Blogs Entry'
	);

	const portalContentReviewerRole = await apiHelpers.headlessAdminUser
		.getRoles('"Portal Content Reviewer"')
		.then(({items}) => items[0]);

	const site = await apiHelpers.headlessSite.getSiteByERC('L_GUEST');

	for (let index = 0; index < NUMBER_OF_USERS_AND_TASKS; index++) {
		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		await apiHelpers.headlessAdminUser.assignUserToRole(
			portalContentReviewerRole.externalReferenceCode,
			user.id
		);

		users.push(user);

		const blogPost = await apiHelpers.headlessDelivery.postBlog(site.id);

		blogPosts.push(blogPost);
	}

	createdEntities.users = users;

	createdEntities.blogPosts = blogPosts;

	const submittingUser = blogPosts[0].creator.id;

	const workflowTaskDefinitions = await retryGetWorkflowTasksBySubmittingUser(
		{
			expectedNumberOfTasks: NUMBER_OF_USERS_AND_TASKS,
			page,
			submittingUser,
		}
	);

	for (let index = 0; index < NUMBER_OF_USERS_AND_TASKS; index++) {
		await apiHelpers.headlessAdminWorkflow.postAssignTaskToUser(
			workflowTaskDefinitions.items[index].id,
			users[index].id
		);
	}

	await applicationsMenuPage.goToMetrics();

	await metricsPage.chooseProcess(WORKFLOW_DEFINITION_NAME);

	for (const user of users) {
		await expect(page.getByText(user.name)).toBeVisible();
	}

	await page
		.getByText(`View All Assignees (${NUMBER_OF_USERS_AND_TASKS})`)
		.click();

	await expect(
		page.getByText(`${WORKFLOW_DEFINITION_NAME}: Workload by Assignee`)
	).toBeVisible();

	await expect(
		page.getByText(
			`Showing 1 to ${NUMBER_OF_USERS_AND_TASKS} of ${NUMBER_OF_USERS_AND_TASKS} entries.`
		)
	).toBeVisible();

	await page.getByLabel('Items Per Page').click();

	await page.getByRole('option', {name: `${NEW_PAGE_SIZE} Entries`}).click();

	await expect(
		page.getByText(
			`Showing 1 to ${NEW_PAGE_SIZE} of ${NUMBER_OF_USERS_AND_TASKS} entries.`
		)
	).toBeVisible();

	for (const [index, user] of users.entries()) {
		const assigneeLink = page.getByText(user.name);

		await expect(assigneeLink).toBeVisible();

		await assigneeLink.click();

		await expect(
			page.getByText(`${WORKFLOW_DEFINITION_NAME}: All Items`)
		).toBeVisible();

		await expect(page.getByText(`Assignee: ${user.name}`)).toBeVisible();

		await expect(allItemsPage.pendingFilterLabel).toBeVisible();

		await allItemsPage.backButton.click();

		if (index === NEW_PAGE_SIZE - 1) {
			await page.getByLabel('Go to the next page,').click();
		}
	}
});

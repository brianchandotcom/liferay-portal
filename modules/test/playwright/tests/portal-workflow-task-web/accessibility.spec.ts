/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import {checkAccessibility} from '../../utils/checkAccessibility';
import clearInvalidUserNotifications from '../../utils/clearInvalidUserNotifications';
import retryGetWorkflowTasksBySubmittingUser from '../../utils/retryGetWorkflowTasksBySubmittingUser';

export const test = mergeTests(apiHelpersTest, loginTest(), workflowPagesTest);

interface CreatedEntities {
	blogPosts?: TBlogPost[];
}

const createdEntities: CreatedEntities = {};

test.afterEach(async ({apiHelpers, configurationTabPage, page}) => {
	await configurationTabPage.goTo();

	await configurationTabPage.unassignWorkflowFromAssetType('Blogs Entry');

	if (createdEntities.blogPosts?.length) {
		for (const blog of createdEntities.blogPosts) {
			await apiHelpers.headlessDelivery.deleteBlog(blog.id);
		}
	}

	delete createdEntities.blogPosts;

	await clearInvalidUserNotifications(page);
});

test('Workflow task view page is accessible', async ({
	apiHelpers,
	configurationTabPage,
	page,
	workflowTaskDetailsPage,
}) => {
	await configurationTabPage.goTo();

	await configurationTabPage.assignWorkflowToAssetType(
		'Single Approver',
		'Blogs Entry'
	);

	const site = await apiHelpers.headlessSite.getSiteByERC('L_GUEST');

	const blogPost = await apiHelpers.headlessDelivery.postBlog(site.id);

	createdEntities.blogPosts = [blogPost];

	const submittingUserId = blogPost.creator.id;

	const workflowTaskDefinitions = await retryGetWorkflowTasksBySubmittingUser(
		{
			expectedNumberOfTasks: 1,
			page,
			submittingUser: submittingUserId,
		}
	);

	const workflowTaskDefinition = workflowTaskDefinitions.items[0];

	await apiHelpers.headlessAdminWorkflow.postAssignTaskToUser(
		workflowTaskDefinition.id,
		submittingUserId
	);

	await page.getByTitle('User Profile Menu').click();

	await page.getByRole('menuitem', {name: 'My Workflow Tasks'}).click();

	await page
		.getByRole('link', {
			name: workflowTaskDefinition.objectReviewed.assetTitle,
		})
		.click();

	await page.waitForLoadState();

	const assetMetadataWrapperElementSelector = 'div.taglib-asset-metadata';

	const openActionsMenuLinkSelector =
		'button[aria-label="Open Actions Menu"]';

	const taskContentActionsSelectors = ['edit', 'view', 'viewUsages'].map(
		(sufix) => `#${workflowTaskDetailsPage.portletNameSpace + sufix}`
	);

	await checkAccessibility({
		page,
		selectors: [
			...taskContentActionsSelectors,
			assetMetadataWrapperElementSelector,
			openActionsMenuLinkSelector,
		],
	});

	await page.locator(openActionsMenuLinkSelector).click();

	const assignToMeMenuItemSelector = 'button[data-title="Approve"]';

	await checkAccessibility({
		page,
		selectors: [assignToMeMenuItemSelector],
	});

	expect(test.info().errors).toHaveLength(0);
});

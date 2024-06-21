/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {readFileSync} from 'fs';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {notificationPagesTest} from '../../fixtures/notificationPagesTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import {blogsPagesTest} from '../../tests/blogs-web/fixtures/blogsPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	blogsPagesTest,
	loginTest(),
	notificationPagesTest,
	workflowPagesTest
);

let assetType: string;
let blogTitle: string;
let workflowDefinitionId: number;
let workflowDefinitionName: string;
let workflowXMLDefinition: string;

test.afterEach(async ({apiHelpers, blogsPage, configurationTabPage}) => {
	if (assetType && workflowDefinitionName) {
		await configurationTabPage.goTo();

		await configurationTabPage.unassignWorkflowFromAssetType(assetType);
	}

	if (workflowDefinitionId) {
		await apiHelpers.headlessAdminWorkflow.deleteWorkflowDefinition(
			workflowDefinitionId
		);
	}

	if (blogTitle) {
		await blogsPage.goto();
		await blogsPage.deleteAllBlogEntries();
	}

	assetType = null;
	blogTitle = null;
	workflowDefinitionId = null;
	workflowDefinitionName = null;
	workflowXMLDefinition = null;
});

test('review comment is added to user notification', async ({
	apiHelpers,
	blogsEditBlogEntryPage,
	blogsPage,
	configurationTabPage,
	diagramViewPage,
	page,
	processBuilderPage,
	userPersonalBarPage,
	workflowTaskDetailsPage,
}) => {
	workflowDefinitionName = 'Workflow Definition' + getRandomInt();
	workflowXMLDefinition = readFileSync(
		__dirname +
			'/dependencies/review-user-notification-workflow-definition.xml',
		'utf8'
	);

	const workflowDefinition =
		await apiHelpers.headlessAdminWorkflow.postWorkflowDefinitionSave(
			workflowDefinitionName,
			{content: workflowXMLDefinition}
		);

	workflowDefinitionId = workflowDefinition.id;

	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.publishWorkflowDefinitionButton.click();

	await diagramViewPage.goBack();

	await configurationTabPage.goTo();

	assetType = 'Blogs Entry';

	await configurationTabPage.assignWorkflowToAssetType(
		workflowDefinitionName,
		assetType
	);

	await blogsPage.goto();

	await blogsPage.goToCreateBlogEntry();

	blogTitle = 'Blog Title' + getRandomInt();

	await blogsEditBlogEntryPage.editBlogEntry({
		content: 'Blog content.',
		submitToWorkflow: true,
		title: blogTitle,
	});

	await workflowTaskDetailsPage.goTo(blogTitle);

	await page.waitForLoadState('networkidle');

	await workflowTaskDetailsPage.reviewActionMenu.click();

	await workflowTaskDetailsPage.approveMenuItem.click();

	const approvalComment = 'Aproval Comment' + getRandomString();

	await workflowTaskDetailsPage.reviewComment.fill(approvalComment);

	await workflowTaskDetailsPage.clickDoneButton();

	await page.waitForLoadState('networkidle');

	await userPersonalBarPage.notificationBadge.click();

	await expect(
		page.getByRole('link', {
			name: `Your submission was reviewed and the reviewer applied the following ${approvalComment}.`,
		})
	).toBeVisible();
});

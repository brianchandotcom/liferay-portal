/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import {readFileSync} from 'fs';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import performLogin, {performLogout} from '../../utils/performLogin';
import {blogsPagesTest} from '../blogs-web/fixtures/blogsPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	blogsPagesTest,
	loginTest(),
	workflowPagesTest
);

let assetType: string;
let blogTitle: string;
let workflowDefinitionId: number;
let workflowDefinitionName: string;
let workflowXMLDefinition: string;

test.afterEach(
	async ({
		apiHelpers,
		blogsPage,
		configurationTabPage,
		processBuilderPage,
	}) => {
		if (assetType && workflowDefinitionName) {
			await processBuilderPage.goto();

			await configurationTabPage.goTo();

			await configurationTabPage.unassignWorkflowFromAssetType(assetType);
		}

		if (blogTitle) {
			await blogsPage.goto();
			await blogsPage.deleteAllBlogEntries();
		}

		if (workflowDefinitionId) {
			await apiHelpers.headlessAdminWorkflow.deleteWorkflowDefinition(
				workflowDefinitionId
			);
		}

		assetType = null;
		blogTitle = null;
		workflowDefinitionId = null;
		workflowDefinitionName = null;
		workflowXMLDefinition = null;
	}
);

test('send user back to my workflow tasks page after assign another user to review', async ({
	apiHelpers,
	blogsEditBlogEntryPage,
	blogsPage,
	configurationTabPage,
	diagramViewPage,
	page,
	processBuilderPage,
	workflowTaskDetailsPage,
	workflowTasksPage,
}) => {
	workflowDefinitionName = 'Workflow Definition' + getRandomString();

	workflowXMLDefinition = readFileSync(
		__dirname +
			'/dependencies/administrator-role-assignments-workflow-definition.xml',
		'utf-8'
	);

	const workflowDefinition =
		await apiHelpers.headlessAdminWorkflow.postWorkflowDefinitionSave(
			workflowDefinitionName,
			{content: workflowXMLDefinition}
		);

	workflowDefinitionId = workflowDefinition.id;

	const user =
		await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
			'demo.company.admin@liferay.com'
		);

	await processBuilderPage.goto();

	await processBuilderPage.clickWorkflowDefinitionName(
		workflowDefinitionName
	);

	await diagramViewPage.publishWorkflowDefinition();

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

	await performLogout(page);

	await performLogin(page, user.alternateName);

	await workflowTasksPage.goToAssignedToMyRoles();

	await workflowTaskDetailsPage.selectAsset(blogTitle);

	await page.waitForTimeout(3000);

	await workflowTaskDetailsPage.reviewActionMenu.click();

	await workflowTaskDetailsPage.assignToMenuItem.click();

	await page.waitForLoadState('networkidle');

	const user2 =
		await apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
			'test@liferay.com'
		);

	await workflowTaskDetailsPage.selectAssignee(user2.id.toString());

	await workflowTaskDetailsPage.doneAssigneeButton.click();

	await expect(workflowTasksPage.assignedToMyRolesLink).toBeVisible();
});

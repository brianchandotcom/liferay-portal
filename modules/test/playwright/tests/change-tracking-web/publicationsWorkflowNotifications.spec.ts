/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {changeTrackingPagesTest} from '../../fixtures/changeTrackingPagesTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import getRandomString from '../../utils/getRandomString';
import {blogsPagesTest} from '../blogs-web/fixtures/blogsPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	blogsPagesTest,
	changeTrackingPagesTest,
	workflowPagesTest
);

let blogTitle;

test.beforeEach(
	async ({
		apiHelpers,
		blogsEditBlogEntryPage,
		blogsPage,
		ctCollection,
		workflowPage,
	}) => {
		await workflowPage.goto();
		await workflowPage.changeWorkflow('Blogs Entry', 'Single Approver');

		await apiHelpers.headlessChangeTracking.checkoutCTCollection(
			ctCollection.body.id
		);

		blogTitle = getRandomString();
		await blogsPage.goto();

		await blogsPage.goToCreateBlogEntry();

		await blogsEditBlogEntryPage.editBlogEntry({
			content: 'Blog content.',
			submitToWorkflow: true,
			title: blogTitle,
		});
	}
);

test.afterEach(async ({apiHelpers, page, workflowPage}) => {
	await apiHelpers.headlessChangeTracking.checkoutCTCollection(0);

	await workflowPage.goto();

	const row = await page.getByRole('row').filter({hasText: 'Blogs Entry'});

	const workflowEnabled = await row
		.getByTitle('Workflow Definition')
		.filter({hasText: 'Single Approver'});

	if (workflowEnabled) {
		await workflowPage.changeWorkflow('Blogs Entry', 'No Workflow', {
			disable: true,
		});
	}
});

test('LPD-44372 Workflow notification persists after publication is published', async ({
	apiHelpers,
	ctCollection,
	page,
	workflowTasksPage,
}) => {
	await workflowTasksPage.goToAssignedToMyRoles();

	await workflowTasksPage.goToAssignedToMyRoles();

	await workflowTasksPage.assignToMe(blogTitle);

	await workflowTasksPage.approve(blogTitle);

	await apiHelpers.headlessChangeTracking.publishCTCollection(
		ctCollection.body.id
	);

	await page.reload();

	await page.getByTitle('User Profile Menu').click();

	await page
		.getByRole('menuitem', {
			name: 'Notifications',
		})
		.click();

	await expect(
		page
			.getByText(
				'Test Test sent you a Blogs Entry for review in the workflow.'
			)
			.first()
	).toBeVisible();
});

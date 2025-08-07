/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {workflowPagesTest} from '../../../fixtures/workflowPagesTest';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
		'LPS-179669': {enabled: true},
	}),
	loginTest(),
	workflowPagesTest
);

interface CreatedEntities {
	blogPosts?: TBlogPost[];
}

const createdEntities: CreatedEntities = {};

test.afterEach(async ({apiHelpers, configurationTabPage}) => {
	await configurationTabPage.goTo();

	await configurationTabPage.unassignWorkflowFromAssetType('Blogs Entry');

	if (createdEntities.blogPosts?.length) {
		for (const blog of createdEntities.blogPosts) {
			await apiHelpers.headlessDelivery.deleteBlog(blog.id);
		}
	}

	delete createdEntities.blogPosts;
});

test(
	'Can manage my workflow tasks',
	{tag: '@LPD-58790'},
	async ({apiHelpers, configurationTabPage, homePage, page}) => {
		await configurationTabPage.goTo();

		await configurationTabPage.assignWorkflowToAssetType(
			'Single Approver',
			'Blogs Entry'
		);

		const site = await apiHelpers.headlessSite.getSiteByERC('L_GUEST');

		await homePage.goto();

		await test.step('Verify workflow task assign to me action', async () => {
			const blogPost1 = await apiHelpers.headlessDelivery.postBlog(
				site.id
			);

			createdEntities.blogPosts = [blogPost1];

			await homePage.workflowTaskFilterButton.click();
			await homePage.assignedToMyRolesMenuItem.click();

			await expect(page.getByText(blogPost1.headline)).toBeVisible();
			await homePage.assignToMe(blogPost1.headline);
			await expect(page.getByText(blogPost1.headline)).toBeHidden();

			await homePage.workflowTaskFilterButton.click();
			await homePage.assignedToMeMenuItem.click();

			await expect(page.getByText(blogPost1.headline)).toBeVisible();
		});

		await test.step('Verify workflow task assign to... action', async () => {
			const blogPost2 = await apiHelpers.headlessDelivery.postBlog(
				site.id
			);

			createdEntities.blogPosts.push(blogPost2);

			await homePage.workflowTaskFilterButton.click();
			await homePage.assignedToMyRolesMenuItem.click();

			await expect(page.getByText(blogPost2.headline)).toBeVisible();
			await homePage.assignTo(blogPost2.headline);
			await expect(page.getByText(blogPost2.headline)).toBeHidden();

			await homePage.workflowTaskFilterButton.click();
			await homePage.assignedToMeMenuItem.click();

			await expect(page.getByText(blogPost2.headline)).toBeVisible();
		});

		await test.step('Verify workflow task approve action', async () => {
			const blogPost3 = await apiHelpers.headlessDelivery.postBlog(
				site.id
			);

			createdEntities.blogPosts.push(blogPost3);

			await homePage.workflowTaskFilterButton.click();
			await homePage.assignedToMyRolesMenuItem.click();

			await expect(page.getByText(blogPost3.headline)).toBeVisible();
			await homePage.assignToMe(blogPost3.headline);
			await expect(page.getByText(blogPost3.headline)).toBeHidden();

			await homePage.workflowTaskFilterButton.click();
			await homePage.assignedToMeMenuItem.click();

			await expect(page.getByText(blogPost3.headline)).toBeVisible();
			await homePage.approveWorkflowTask(blogPost3.headline);
			await expect(page.getByText(blogPost3.headline)).toBeHidden();
		});

		await test.step('Verify workflow task reject action', async () => {
			const blogPost4 = await apiHelpers.headlessDelivery.postBlog(
				site.id
			);

			createdEntities.blogPosts.push(blogPost4);

			await homePage.workflowTaskFilterButton.click();
			await homePage.assignedToMyRolesMenuItem.click();

			await expect(page.getByText(blogPost4.headline)).toBeVisible();
			await homePage.assignToMe(blogPost4.headline);
			await expect(page.getByText(blogPost4.headline)).toBeHidden();

			await homePage.workflowTaskFilterButton.click();
			await homePage.assignedToMeMenuItem.click();

			await expect(page.getByText(blogPost4.headline)).toBeVisible();
			await homePage.rejectWorkflowTask(blogPost4.headline);
			await expect(page.getByText(blogPost4.headline)).toBeHidden();
		});

		await test.step('Verify workflow task update due date action', async () => {
			const blogPost5 = await apiHelpers.headlessDelivery.postBlog(
				site.id
			);

			createdEntities.blogPosts.push(blogPost5);

			await homePage.workflowTaskFilterButton.click();
			await homePage.assignedToMyRolesMenuItem.click();

			await expect(page.getByText(blogPost5.headline)).toBeVisible();
			await homePage.assignToMe(blogPost5.headline);
			await expect(page.getByText(blogPost5.headline)).toBeHidden();

			await homePage.workflowTaskFilterButton.click();
			await homePage.assignedToMeMenuItem.click();

			await expect(page.getByText(blogPost5.headline)).toBeVisible();

			const now = new Date();

			const nextYear = now.getFullYear() + 1;

			const dueDate = '01/01/' + nextYear;

			await homePage.updateDueDate(dueDate, blogPost5.headline);

			const workflowTaskRow = page.getByRole('row', {
				name: blogPost5.headline,
			});
			await workflowTaskRow.getByRole('button').click();
			await page.getByRole('menuitem', {name: 'Update Due Date'}).click();

			await expect(page.getByRole('textbox').first()).toHaveValue(
				dueDate
			);
		});
	}
);

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {workflowPagesTest} from '../../../fixtures/workflowPagesTest';
import clearInvalidUserNotifications from '../../../utils/clearInvalidUserNotifications';

export const test = mergeTests(apiHelpersTest, loginTest(), workflowPagesTest);

interface CreatedEntities {
	blogPosts?: TBlogPost[];
}

const createdEntities: CreatedEntities = {};

test.afterEach(async ({apiHelpers, configurationTabPage, page}) => {
	test.slow();
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

test.describe('Workflow metrics', () => {
	test('displays the correct number of entries based on the selected entries per page option', async ({
		apiHelpers,
		configurationTabPage,
		metricsPage,
		page,
	}) => {
		await configurationTabPage.goTo();

		await configurationTabPage.assignWorkflowToAssetType(
			'Single Approver',
			'Blogs Entry'
		);

		const site = await apiHelpers.headlessSite.getSiteByERC('L_GUEST');
		const blogPosts: TBlogPost[] = [];
		for (let i = 1; i <= 21; i++) {
			blogPosts.push(
				await apiHelpers.headlessDelivery.postBlog(site.id, {
					headline: `Blogs Entry ${i}`,
				})
			);
		}

		createdEntities.blogPosts = blogPosts;

		await metricsPage.goTo();

		await page
			.getByRole('cell', {name: 'Single Approver'})
			.getByRole('link')
			.click();

		await page
			.getByRole('link')
			.filter({hasText: 'Total Pending'})
			.first()
			.click();

		await expect(
			page.getByRole('row').filter({hasText: 'Blogs Entry'})
		).toHaveCount(20);

		await page.getByLabel('Go to the next page').click();

		await expect(
			page.getByRole('row').filter({hasText: 'Blogs entry'})
		).toHaveCount(1);
	});
});

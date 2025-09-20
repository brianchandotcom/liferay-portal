/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {workflowPagesTest} from '../../../fixtures/workflowPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {cmsPagesTest} from './fixtures/cmsPagesTest';
import {DataSetPage} from './pages/DataSetPage';

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

		const blogPost1 = await apiHelpers.headlessDelivery.postBlog(site.id);

		createdEntities.blogPosts = [blogPost1];

		const blogPost2 = await apiHelpers.headlessDelivery.postBlog(site.id);

		createdEntities.blogPosts.push(blogPost2);

		const blogPost3 = await apiHelpers.headlessDelivery.postBlog(site.id);

		createdEntities.blogPosts.push(blogPost3);

		await homePage.goto();

		await test.step('Verify workflow task assign to me action', async () => {
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
			await expect(page.getByText(blogPost1.headline)).toBeVisible();
			await homePage.approveWorkflowTask(blogPost1.headline);
			await expect(page.getByText(blogPost1.headline)).toBeHidden();
		});

		await test.step('Verify workflow task reject action', async () => {
			await expect(page.getByText(blogPost2.headline)).toBeVisible();
			await homePage.rejectWorkflowTask(blogPost2.headline);
			await expect(page.getByText(blogPost2.headline)).toBeHidden();
		});

		await test.step('Verify workflow task update due date action', async () => {
			await homePage.workflowTaskFilterButton.click();
			await homePage.assignedToMyRolesMenuItem.click();

			await expect(page.getByText(blogPost3.headline)).toBeVisible();
			await homePage.assignToMe(blogPost3.headline);
			await expect(page.getByText(blogPost3.headline)).toBeHidden();

			await homePage.workflowTaskFilterButton.click();
			await homePage.assignedToMeMenuItem.click();

			await expect(page.getByText(blogPost3.headline)).toBeVisible();

			const now = new Date();

			const nextYear = now.getFullYear() + 1;

			const dueDate = nextYear + '-01-01';

			await homePage.updateDueDate(dueDate, blogPost3.headline);

			const workflowTaskRow = page.getByRole('row', {
				name: blogPost3.headline,
			});
			await workflowTaskRow.getByRole('button').click();
			await page.getByRole('menuitem', {name: 'Update Due Date'}).click();

			await expect(page.locator('input[type="date"]')).toHaveValue(
				dueDate
			);
		});
	}
);

test(
	'Can see Recent Assets',
	{tag: '@LPD-58792'},
	async ({apiHelpers, homePage, page}) => {
		const applicationName = 'cms/knowledge-bases';
		const spaceName = 'Default';
		let objectEntry1;
		let objectEntry2;

		const file1Title = `title ${getRandomString()}`;

		try {
			objectEntry1 = await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: file1Title,
				},
				applicationName,
				spaceName
			);

			objectEntry2 = await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: `some content ${getRandomString()}`,
				},
				applicationName,
				spaceName
			);

			await homePage.goto();

			const dataSetFragmentPage: DataSetPage = new DataSetPage(page);

			const row = dataSetFragmentPage.table.bodyRows.filter({
				hasText: file1Title,
			});

			await expect(row.getByText(file1Title)).toBeVisible();
		}
		finally {
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry1.id)
			);
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry2.id)
			);
		}
	}
);

test(
	'Can use Quick Actions to create new content',
	{tag: '@LPD-58793'},
	async ({apiHelpers, homePage, page}) => {
		await apiHelpers.headlessAssetLibrary.createAssetLibrary({
			name: `Space ${getRandomString()}`,
			settings: {
				logoColor: 'outline-3',
				sharingEnabled: true,
			},
			type: 'Space',
		});

		await test.step('Check redirection after clicking New Basic Web Content button', async () => {
			await homePage.goto();

			await homePage.basicWebContentButton.click();

			await homePage.selectSpace('Default');

			await expect(
				page.getByPlaceholder('New Basic Web Content')
			).toBeVisible();
		});

		await test.step('Check redirection after clicking Blog button', async () => {
			await homePage.goto();

			await homePage.blogButton.click();

			await homePage.selectSpace('Default');

			await expect(page.getByPlaceholder('New Blog')).toBeVisible();
		});

		await test.step('Check redirection after clicking Knowledge Base button', async () => {
			await homePage.goto();

			await homePage.knowledgeBaseButton.click();

			await homePage.selectSpace('Default');

			await expect(
				page.getByPlaceholder('New Knowledge Base')
			).toBeVisible();
		});

		await test.step('Check redirection after clicking Basic Document button', async () => {
			await homePage.goto();

			await homePage.basicDocumentButton.click();

			await homePage.selectSpace('Default');

			await expect(
				page.getByPlaceholder('New Basic Document')
			).toBeVisible();
		});

		await test.step('Check redirection after clicking Vocabulary button', async () => {
			await homePage.goto();

			await homePage.vocabularyButton.click();

			await expect(page.getByText('Basic Info')).toBeVisible();
		});
	}
);

test(
	'Can use Search Bar to search for content',
	{tag: '@LPD-61220'},
	async ({apiHelpers, assetsPage, homePage, page}) => {
		const applicationName = 'cms/knowledge-bases';
		const spaceName = 'Default';
		let objectEntry1;
		let objectEntry2;

		const file1Title = `title ${getRandomString()}`;

		try {
			objectEntry1 = await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: file1Title,
				},
				applicationName,
				spaceName
			);

			objectEntry2 = await apiHelpers.objectEntry.postObjectEntry(
				{
					objectEntryFolderExternalReferenceCode: 'L_CONTENTS',
					title: `some content ${getRandomString()}`,
				},
				applicationName,
				spaceName
			);

			await homePage.goto();

			const searchInput = await page.getByPlaceholder('Search');

			await searchInput.fill('title');

			await searchInput.press('Enter');

			const row = assetsPage.table.bodyRows.filter({hasText: file1Title});

			await expect(row.getByText(file1Title)).toBeVisible();
		}
		finally {
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry1.id)
			);
			await apiHelpers.objectEntry.deleteObjectEntry(
				applicationName,
				String(objectEntry2.id)
			);
		}
	}
);

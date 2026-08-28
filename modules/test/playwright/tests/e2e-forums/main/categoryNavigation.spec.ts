/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {
	FORUMS_HOME_PATH,
	FORUMS_MESSAGES_PATH,
	FORUM_CATEGORY_APPLICATION_NAME,
	FORUM_THREAD_APPLICATION_NAME,
	getForumsSiteId,
} from './forumsApi';

const test = mergeTests(dataApiHelpersTest, loginTest());

test(
	'Browsing a category shows its own threads and not another category',
	{
		tag: ['@LPD-103732'],
	},
	async ({apiHelpers, page}) => {
		const siteId = await getForumsSiteId(apiHelpers);

		const [first, second] = await Promise.all(
			[1, 2].map(() =>
				apiHelpers.objectEntry.postObjectEntry(
					{
						categoryDescription: 'Created by an end to end test',
						categoryName: `Category ${getRandomString()}`,
					},
					FORUM_CATEGORY_APPLICATION_NAME,
					siteId
				)
			)
		);

		const firstTitle = `Topic ${getRandomString()}`;
		const secondTitle = `Topic ${getRandomString()}`;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				messageTitle: firstTitle,
				r_categoryThreads_c_forumCategoryId: first.id,
			},
			FORUM_THREAD_APPLICATION_NAME,
			siteId
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{
				messageTitle: secondTitle,
				r_categoryThreads_c_forumCategoryId: second.id,
			},
			FORUM_THREAD_APPLICATION_NAME,
			siteId
		);

		await page.goto(`${FORUMS_MESSAGES_PATH}?categoryId=${first.id}`);

		await expect(page.getByText(firstTitle, {exact: true})).toBeVisible();

		// The other category's topic is what makes this a filter rather than a
		// listing that happens to contain the right row.

		await expect(page.getByText(secondTitle, {exact: true})).toHaveCount(0);

		await page.goto(`${FORUMS_MESSAGES_PATH}?categoryId=${second.id}`);

		await expect(page.getByText(secondTitle, {exact: true})).toBeVisible();

		await expect(page.getByText(firstTitle, {exact: true})).toHaveCount(0);
	}
);

test(
	'A subcategory is listed under its parent',
	{
		tag: ['@LPD-103732'],
	},
	async ({apiHelpers, page}) => {
		const siteId = await getForumsSiteId(apiHelpers);

		const parent = await apiHelpers.objectEntry.postObjectEntry(
			{
				categoryDescription: 'Created by an end to end test',
				categoryName: `Parent ${getRandomString()}`,
			},
			FORUM_CATEGORY_APPLICATION_NAME,
			siteId
		);

		const childName = `Child ${getRandomString()}`;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				categoryDescription: 'Created by an end to end test',
				categoryName: childName,
				r_categorySubcategories_c_forumCategoryId: parent.id,
			},
			FORUM_CATEGORY_APPLICATION_NAME,
			siteId
		);

		await page.goto(`${FORUMS_MESSAGES_PATH}?categoryId=${parent.id}`);

		await expect(page.getByText(childName, {exact: true})).toBeVisible();
	}
);

test(
	'A category card on the home page opens that category',
	{
		tag: ['@LPD-103732'],
	},
	async ({apiHelpers, page}) => {
		const siteId = await getForumsSiteId(apiHelpers);

		const category = await apiHelpers.objectEntry.postObjectEntry(
			{
				categoryDescription: 'Created by an end to end test',
				categoryName: `Category ${getRandomString()}`,
			},
			FORUM_CATEGORY_APPLICATION_NAME,
			siteId
		);

		const messageTitle = `Topic ${getRandomString()}`;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				messageTitle,
				r_categoryThreads_c_forumCategoryId: category.id,
			},
			FORUM_THREAD_APPLICATION_NAME,
			siteId
		);

		await page.goto(FORUMS_HOME_PATH);

		// The grid renders at most its configured page size, twelve by
		// default, out of every category in the site, so a category created
		// here is not reliably on it. Follow a card that is, and assert it
		// lands on that category rather than on the unfiltered list.

		const card = page.locator('.forums-category-grid__card').first();

		await expect(card).toBeVisible();

		const href = await card.getAttribute('href');

		expect(href).toContain('categoryId=');

		await card.click();

		await page.waitForURL(/categoryId=/);

		expect(page.url()).toContain(
			new URL(href, page.url()).searchParams.get('categoryId')
		);
	}
);

/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {
	FORUMS_MESSAGES_PATH,
	FORUM_CATEGORY_APPLICATION_NAME,
	FORUM_THREAD_APPLICATION_NAME,
	THREAD_PRIORITY,
	getForumsSiteId,
	getThreadPath,
} from './forumsApi';

const test = mergeTests(dataApiHelpersTest, loginTest());

// The composer renders a priority <select> whose options carry these same
// words, so matching bare text finds a control that is always on the page
// rather than a badge on a thread.

function priorityBadge(page, label: string) {
	return page.locator('.forums-message-card__solved', {hasText: label});
}

test(
	'A category lists every thread type and marks each priority',
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

		const titles = {
			announcement: `Announcement ${getRandomString()}`,
			discussion: `Discussion ${getRandomString()}`,
			question: `Question ${getRandomString()}`,
			sticky: `Sticky ${getRandomString()}`,
			urgent: `Urgent ${getRandomString()}`,
		};

		const threads = [
			{
				priority: THREAD_PRIORITY.NONE,
				question: false,
				title: titles.discussion,
			},
			{
				priority: THREAD_PRIORITY.NONE,
				question: true,
				title: titles.question,
			},
			{
				priority: THREAD_PRIORITY.ANNOUNCEMENT,
				question: false,
				title: titles.announcement,
			},
			{
				priority: THREAD_PRIORITY.STICKY,
				question: false,
				title: titles.sticky,
			},
			{
				priority: THREAD_PRIORITY.URGENT,
				question: false,
				title: titles.urgent,
			},
		];

		for (const thread of threads) {
			await apiHelpers.objectEntry.postObjectEntry(
				{
					messageTitle: thread.title,
					priority: thread.priority,
					question: thread.question,
					r_categoryThreads_c_forumCategoryId: category.id,
				},
				FORUM_THREAD_APPLICATION_NAME,
				siteId
			);
		}

		await page.goto(`${FORUMS_MESSAGES_PATH}?categoryId=${category.id}`);

		for (const thread of threads) {
			await expect(
				page.getByText(thread.title, {exact: true})
			).toBeVisible();
		}

		// A badge is rendered only from priority 1 upwards, so the plain
		// discussion is what proves the mapping is read rather than guessed.

		for (const label of ['Announcement', 'Sticky', 'Urgent']) {
			await expect(priorityBadge(page, label)).toHaveCount(1);
		}

		// A category holding only an unprioritized thread must show no badge at
		// all. Without this the assertions above would also pass against a
		// label that belongs to the page rather than to a thread.

		const plainCategory = await apiHelpers.objectEntry.postObjectEntry(
			{
				categoryDescription: 'Created by an end to end test',
				categoryName: `Category ${getRandomString()}`,
			},
			FORUM_CATEGORY_APPLICATION_NAME,
			siteId
		);

		const plainTitle = `Discussion ${getRandomString()}`;

		await apiHelpers.objectEntry.postObjectEntry(
			{
				messageTitle: plainTitle,
				priority: THREAD_PRIORITY.NONE,
				r_categoryThreads_c_forumCategoryId: plainCategory.id,
			},
			FORUM_THREAD_APPLICATION_NAME,
			siteId
		);

		await page.goto(
			`${FORUMS_MESSAGES_PATH}?categoryId=${plainCategory.id}`
		);

		await expect(page.getByText(plainTitle, {exact: true})).toBeVisible();

		for (const label of ['Announcement', 'Sticky', 'Urgent']) {
			await expect(priorityBadge(page, label)).toHaveCount(0);
		}
	}
);

test(
	'Opening a thread from its category shows that thread',
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

		const thread = await apiHelpers.objectEntry.postObjectEntry(
			{
				messageTitle,
				r_categoryThreads_c_forumCategoryId: category.id,
			},
			FORUM_THREAD_APPLICATION_NAME,
			siteId
		);

		await page.goto(getThreadPath(thread.friendlyUrlPath));

		await expect(page.getByText(messageTitle).first()).toBeVisible();
	}
);

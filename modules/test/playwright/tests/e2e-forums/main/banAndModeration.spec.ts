/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {
	FORUMS_MODERATION_PATH,
	FORUMS_NEW_DISCUSSION_PATH,
	FORUM_BAN_APPLICATION_NAME,
	FORUM_CATEGORY_APPLICATION_NAME,
	FORUM_MESSAGE_APPLICATION_NAME,
	FORUM_SUSPICIOUS_ACTIVITY_APPLICATION_NAME,
	FORUM_THREAD_APPLICATION_NAME,
	createForumMember,
	getForumsSiteId,
	requestAsUser,
	signInAs,
} from './forumsApi';

const test = mergeTests(dataApiHelpersTest, loginTest());

async function createThread(apiHelpers, siteId: string) {
	const category = await apiHelpers.objectEntry.postObjectEntry(
		{
			categoryDescription: 'Created by an end to end test',
			categoryName: `Category ${getRandomString()}`,
		},
		FORUM_CATEGORY_APPLICATION_NAME,
		siteId
	);

	return await apiHelpers.objectEntry.postObjectEntry(
		{
			messageTitle: `Topic ${getRandomString()}`,
			r_categoryThreads_c_forumCategoryId: category.id,
		},
		FORUM_THREAD_APPLICATION_NAME,
		siteId
	);
}

test(
	'A banned member cannot submit a reply and is told why',
	{
		tag: ['@LPD-103732'],
	},
	async ({apiHelpers, browser, page}) => {
		const siteId = await getForumsSiteId(apiHelpers);

		const banned = await createForumMember(apiHelpers, siteId, 'Banned');

		await apiHelpers.objectEntry.postObjectEntry(
			{banUserId: banned.userAccount.id},
			FORUM_BAN_APPLICATION_NAME,
			siteId
		);

		const context = await browser.newContext({
			storageState: {cookies: [], origins: []},
		});

		try {
			const bannedPage = await context.newPage();

			await signInAs(bannedPage, banned.userAccount.emailAddress);

			await bannedPage.goto(FORUMS_NEW_DISCUSSION_PATH);

			// The fragment renders two submit buttons sharing an id, one per
			// layout, so the enabled one is what the member actually sees.

			await expect(
				bannedPage.locator('#forumsMessageComposerSubmit').first()
			).toBeDisabled();

			await expect(
				bannedPage.getByText(
					'Your account has been banned from participating in the forums.'
				)
			).toBeVisible();
		}
		finally {
			await context.close();
		}

		// A member who is not banned is what proves the composer is reacting to
		// the ban rather than being disabled for everyone.

		const allowed = await createForumMember(apiHelpers, siteId, 'Allowed');

		await signInAs(page, allowed.userAccount.emailAddress);

		await page.goto(FORUMS_NEW_DISCUSSION_PATH);

		await expect(
			page.locator('#forumsMessageComposerSubmit').first()
		).toBeEnabled();

		await expect(
			page.getByText(
				'Your account has been banned from participating in the forums.'
			)
		).toHaveCount(0);
	}
);

// The composer disables its submit button, but nothing refuses the write, so a
// banned member still posts through the API. This records the gap rather than
// asserting the behaviour is correct; remove the annotation once the ban is
// enforced where the entry is created.

test(
	'A ban is not enforced when the reply is posted directly',
	{
		tag: ['@LPD-103732'],
	},
	async ({apiHelpers, browser}) => {
		test.fail();

		const siteId = await getForumsSiteId(apiHelpers);

		const thread = await createThread(apiHelpers, siteId);

		const banned = await createForumMember(apiHelpers, siteId, 'Banned');

		await apiHelpers.objectEntry.postObjectEntry(
			{banUserId: banned.userAccount.id},
			FORUM_BAN_APPLICATION_NAME,
			siteId
		);

		const {status} = await requestAsUser(browser, {
			body: {
				body: '<p>Posted while banned.</p>',
				r_threadMessages_c_forumThreadId: thread.id,
				subject: `Reply ${getRandomString()}`,
			},
			emailAddress: banned.userAccount.emailAddress,
			method: 'POST',
			path: `/o/${FORUM_MESSAGE_APPLICATION_NAME}/scopes/${siteId}`,
		});

		expect(status).toBe(403);
	}
);

test(
	'A flagged message waits in the moderation queue until it is validated',
	{
		tag: ['@LPD-103732'],
	},
	async ({apiHelpers, page}) => {
		const siteId = await getForumsSiteId(apiHelpers);

		const thread = await createThread(apiHelpers, siteId);

		const reason = `Reported ${getRandomString()}`;

		const suspiciousActivity = await apiHelpers.objectEntry.postObjectEntry(
			{
				messageId: thread.id,
				r_threadSuspiciousActivities_c_forumThreadId: thread.id,
				reason,
				validated: false,
			},
			FORUM_SUSPICIOUS_ACTIVITY_APPLICATION_NAME,
			siteId
		);

		await page.goto(FORUMS_MODERATION_PATH);

		await expect(page.getByText(reason, {exact: true})).toBeVisible();

		await apiHelpers.objectEntry.patchObjectEntry(
			{validated: true},
			FORUM_SUSPICIOUS_ACTIVITY_APPLICATION_NAME,
			suspiciousActivity.id
		);

		await page.goto(FORUMS_MODERATION_PATH);

		// The queue opens on the pending filter, so a validated report must no
		// longer be on it.

		await expect(page.getByText(reason, {exact: true})).toHaveCount(0);
	}
);

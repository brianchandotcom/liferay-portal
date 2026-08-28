/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {
	FAN_OUT_TIMEOUT,
	FORUM_CATEGORY_APPLICATION_NAME,
	FORUM_MESSAGE_APPLICATION_NAME,
	FORUM_THREAD_APPLICATION_NAME,
	createForumMember,
	getForumsSiteId,
	requestAsUser,
} from './forumsApi';

const test = mergeTests(dataApiHelpersTest, loginTest());

test(
	'Mentioning a member in a reply notifies that member and nobody else',
	{
		tag: ['@LPD-103732', '@LPD-101022'],
	},
	async ({apiHelpers, browser}) => {
		const siteId = await getForumsSiteId(apiHelpers);

		const mentioned = await createForumMember(
			apiHelpers,
			siteId,
			'Mentioned'
		);
		const author = await createForumMember(apiHelpers, siteId, 'Author');

		const category = await apiHelpers.objectEntry.postObjectEntry(
			{
				categoryDescription: 'Created by an end to end test',
				categoryName: `Category ${getRandomString()}`,
			},
			FORUM_CATEGORY_APPLICATION_NAME,
			siteId
		);

		const topicTitle = `Topic ${getRandomString()}`;

		const thread = await apiHelpers.objectEntry.postObjectEntry(
			{
				messageTitle: topicTitle,
				r_categoryThreads_c_forumCategoryId: category.id,
			},
			FORUM_THREAD_APPLICATION_NAME,
			siteId
		);

		// Only somebody the forum already knows can be mentioned, because the
		// picker and the resolver both read the forum's own user object rather
		// than the user directory. Posting is what puts them there.

		const firstReply = await requestAsUser(browser, {
			body: {
				body: '<p>Posting so the forum knows me.</p>',
				r_threadMessages_c_forumThreadId: thread.id,
				subject: `Reply ${getRandomString()}`,
			},
			emailAddress: mentioned.userAccount.emailAddress,
			method: 'POST',
			path: `/o/${FORUM_MESSAGE_APPLICATION_NAME}/scopes/${siteId}`,
		});

		expect(firstReply.status).toBe(200);

		const mentionReply = await requestAsUser(browser, {
			body: {
				body: `<p>Asking @${mentioned.screenName} to take a look.</p>`,
				r_threadMessages_c_forumThreadId: thread.id,
				subject: `Reply ${getRandomString()}`,
			},
			emailAddress: author.userAccount.emailAddress,
			method: 'POST',
			path: `/o/${FORUM_MESSAGE_APPLICATION_NAME}/scopes/${siteId}`,
		});

		expect(mentionReply.status).toBe(200);

		const mentionedFullName = `${mentioned.userAccount.givenName} ${mentioned.userAccount.familyName}`;

		await expect
			.poll(
				async () => {
					const queue = await apiHelpers.get(
						`${apiHelpers.baseUrl}notification/v1.0/notification-queue-entries?pageSize=100`
					);

					return (queue.items || []).filter(
						(item) =>
							String(item.subject || '').includes(topicTitle) &&
							(item.recipients || []).some(
								(recipient) =>
									recipient.userFullName === mentionedFullName
							)
					).length;
				},
				{timeout: FAN_OUT_TIMEOUT}
			)
			.toBeGreaterThan(0);
	}
);

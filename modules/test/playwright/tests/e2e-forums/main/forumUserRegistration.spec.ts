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
	FORUM_USER_APPLICATION_NAME,
	createForumMember,
	getForumsSiteId,
	requestAsUser,
} from './forumsApi';

const test = mergeTests(dataApiHelpersTest, loginTest());

test(
	'A member who has never posted is recorded as a forum user by their first reply',
	{
		tag: ['@LPD-103732', '@LPD-101022'],
	},
	async ({apiHelpers, browser}) => {
		const siteId = await getForumsSiteId(apiHelpers);

		const {screenName, userAccount} = await createForumMember(
			apiHelpers,
			siteId
		);

		// The forum records an author only for somebody with no row yet, so a
		// member who has never posted is the only way to exercise it.

		const forumUsersBefore =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntriesByScope(
				FORUM_USER_APPLICATION_NAME,
				siteId,
				new URLSearchParams({filter: `screenName eq '${screenName}'`})
			);

		expect(forumUsersBefore.totalCount).toBe(0);

		const category = await apiHelpers.objectEntry.postObjectEntry(
			{
				categoryDescription: 'Created by an end to end test',
				categoryName: `Category ${getRandomString()}`,
			},
			FORUM_CATEGORY_APPLICATION_NAME,
			siteId
		);

		const thread = await apiHelpers.objectEntry.postObjectEntry(
			{
				messageTitle: `Topic ${getRandomString()}`,
				r_categoryThreads_c_forumCategoryId: category.id,
			},
			FORUM_THREAD_APPLICATION_NAME,
			siteId
		);

		const {status} = await requestAsUser(browser, {
			body: {
				body: '<p>My first reply.</p>',
				r_threadMessages_c_forumThreadId: thread.id,
				subject: `Reply ${getRandomString()}`,
			},
			emailAddress: userAccount.emailAddress,
			method: 'POST',
			path: `/o/${FORUM_MESSAGE_APPLICATION_NAME}/scopes/${siteId}`,
		});

		expect(status).toBe(200);

		await expect
			.poll(
				async () => {
					const forumUsers =
						await apiHelpers.objectEntry.getObjectDefinitionObjectEntriesByScope(
							FORUM_USER_APPLICATION_NAME,
							siteId,
							new URLSearchParams({
								filter: `screenName eq '${screenName}'`,
							})
						);

					return forumUsers.totalCount;
				},
				{timeout: FAN_OUT_TIMEOUT}
			)
			.toBe(1);
	}
);

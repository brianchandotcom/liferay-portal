/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {
	FORUM_NOTIFICATION_APPLICATION_NAME,
	FORUM_SUBSCRIPTION_APPLICATION_NAME,
	FORUM_USER_APPLICATION_NAME,
	createForumMember,
	getForumsSiteId,
	requestAsUser,
} from './forumsApi';

const test = mergeTests(dataApiHelpersTest, loginTest());

// The fan-out writes forum users and notifications as a service account rather
// than as the member who posted, so a member holds no permission to write
// either one directly. Were the grant still on the member, anyone could address
// a notification to somebody else.

test(
	'A member cannot write a forum notification or a forum user directly',
	{
		tag: ['@LPD-103732'],
	},
	async ({apiHelpers, browser}) => {
		const siteId = await getForumsSiteId(apiHelpers);

		const {userAccount} = await createForumMember(apiHelpers, siteId);

		const forgedNotification = await requestAsUser(browser, {
			body: {
				authorName: 'Somebody else',
				bodyExcerpt: 'Forged',
				notificationKind: 'mention',
				recipientUserId: 20132,
				topicTitle: 'Forged',
			},
			emailAddress: userAccount.emailAddress,
			method: 'POST',
			path: `/o/${FORUM_NOTIFICATION_APPLICATION_NAME}/scopes/${siteId}`,
		});

		expect(forgedNotification.status).toBe(403);

		const forgedForumUser = await requestAsUser(browser, {
			body: {
				forumUserId: 99999,
				screenName: `forged${getRandomString()}`,
			},
			emailAddress: userAccount.emailAddress,
			method: 'POST',
			path: `/o/${FORUM_USER_APPLICATION_NAME}/scopes/${siteId}`,
		});

		expect(forgedForumUser.status).toBe(403);

		// What a member does do for themselves stays available, so the grants
		// above are narrowed rather than simply removed.

		const readForumUsers = await requestAsUser(browser, {
			emailAddress: userAccount.emailAddress,
			path: `/o/${FORUM_USER_APPLICATION_NAME}/scopes/${siteId}?pageSize=1`,
		});

		expect(readForumUsers.status).toBe(200);

		const readSubscriptions = await requestAsUser(browser, {
			emailAddress: userAccount.emailAddress,
			path: `/o/${FORUM_SUBSCRIPTION_APPLICATION_NAME}/scopes/${siteId}?pageSize=1`,
		});

		expect(readSubscriptions.status).toBe(200);
	}
);

/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Browser, Page, expect} from '@playwright/test';

import {
	DataApiHelpers,
	clearAuthToken,
	getHeader,
	readAuthToken,
} from '../../../helpers/ApiHelpers';
import getRandomString from '../../../utils/getRandomString';

export const FORUMS_SITE_EXTERNAL_REFERENCE_CODE = 'LIFERAY_FORUMS';

export const FORUM_BAN_APPLICATION_NAME = 'c/forumbans';

export const FORUM_CATEGORY_APPLICATION_NAME = 'c/forumcategories';

export const FORUM_MESSAGE_APPLICATION_NAME = 'c/forummessages';

export const FORUM_NOTIFICATION_APPLICATION_NAME = 'c/forumnotifications';

export const FORUM_SUBSCRIPTION_APPLICATION_NAME = 'c/forumsubscriptions';

export const FORUM_SUSPICIOUS_ACTIVITY_APPLICATION_NAME =
	'c/forumsuspiciousactivities';

export const FORUM_THREAD_APPLICATION_NAME = 'c/forumthreads';

export const FORUM_USER_APPLICATION_NAME = 'c/forumusers';

// The fan-out runs on its own thread in the microservice, so nothing it writes
// is observable the moment the post returns.

export const FAN_OUT_TIMEOUT = 60000;

// The site is provisioned by the site initializer client extension rather than
// by a Playwright setup project, so it is present only where the Forums
// workspace is deployed.

export async function getForumsSiteId(
	apiHelpers: DataApiHelpers
): Promise<string> {
	const site = await apiHelpers.headlessAdminSite.getSite(
		FORUMS_SITE_EXTERNAL_REFERENCE_CODE
	);

	return String(site.id);
}

// Sends a request authenticated with a Basic Authorization header, from a fresh
// browser context with an empty storage state, so the acting user is the only
// authentication in play and no portal session cookie leaks in from the admin
// fixture.

export async function requestAsUser(
	browser: Browser,
	{
		body,
		emailAddress,
		method = 'GET',
		password = 'test',
		path,
	}: {
		body?: unknown;
		emailAddress: string;
		method?: 'GET' | 'PATCH' | 'POST';
		password?: string;
		path: string;
	}
): Promise<{body: Record<string, unknown> | null; status: number}> {
	const context = await browser.newContext({
		storageState: {cookies: [], origins: []},
	});

	try {
		const page = await context.newPage();

		await page.goto('/');

		return await page.evaluate(
			async ({credentials, method, path, requestBody}) => {
				const headers: Record<string, string> = {
					Authorization: `Basic ${btoa(credentials)}`,
				};

				if (requestBody) {
					headers['Content-Type'] = 'application/json';
				}

				const response = await fetch(path, {
					body: requestBody ? JSON.stringify(requestBody) : undefined,
					headers,
					method,
					redirect: 'manual',
				});

				const contentType = response.headers.get('content-type') || '';

				return {
					body: contentType.includes('application/json')
						? await response.json()
						: null,
					status: response.status,
				};
			},
			{
				credentials: `${emailAddress}:${password}`,
				method,
				path,
				requestBody: body,
			}
		);
	}
	finally {
		await context.close();
	}
}

// Creates an ordinary member of the Forums site. Site membership matters: the
// fan-out resolves the site through headless-admin-site as the acting user, and
// somebody who cannot view the site resolves no site scope at all.

export async function createForumMember(
	apiHelpers: DataApiHelpers,
	siteId: string,
	givenName = 'Forum'
) {
	const screenName = `member${getRandomString()}`.toLowerCase();

	const userAccount = await apiHelpers.headlessAdminUser.postUserAccount({
		alternateName: screenName,
		emailAddress: `${screenName}@liferay.com`,
		familyName: 'Member',
		givenName,
		password: 'test',
	});

	const siteMemberRole =
		await apiHelpers.headlessAdminUser.getRoleByName('Site Member');

	await apiHelpers.headlessAdminUser.assignUserToSite(
		siteMemberRole.id,
		siteId,
		userAccount.id
	);

	return {screenName, userAccount};
}

// Message Boards parity, which the list fragment mirrors: 3 Urgent, 2 Sticky,
// 1 Announcement, and anything at or below 0 renders no badge at all.

export const THREAD_PRIORITY = {
	ANNOUNCEMENT: 1,
	NONE: 0,
	STICKY: 2,
	URGENT: 3,
};

export const FORUMS_MESSAGES_PATH = '/web/forums/forums-messages';

export const FORUMS_HOME_PATH = '/web/forums';

// A thread's display page is reached through the object definition's friendly
// URL separator rather than a page of its own.

export function getThreadPath(friendlyUrlPath: string) {
	return `/web/forums/c_forumthread/${friendlyUrlPath}`;
}

export const FORUMS_MODERATION_PATH = '/web/forums/forums-moderation';

export const FORUMS_NEW_DISCUSSION_PATH = '/web/forums/new-discussion';

// performLoginViaApi resolves the password from a fixed map of predefined
// users, so a member created by a test cannot use it. The rest matches it: the
// auth token is cleared before the post and reread afterwards, or the session
// keeps the previous user's token.

export async function signInAs(
	page: Page,
	emailAddress: string,
	password = 'test'
) {
	await page.goto('/');

	clearAuthToken(page);

	await expect
		.poll(async () => {
			const response = await page.request.post('/c/portal/login', {
				data: new URLSearchParams({
					login: emailAddress,
					password,
					rememberMe: 'true',
				}).toString(),
				headers: await getHeader(
					page,
					'application/x-www-form-urlencoded'
				),
			});

			return response.status();
		})
		.toBe(200);

	await page.goto('/');

	await readAuthToken(page);
}

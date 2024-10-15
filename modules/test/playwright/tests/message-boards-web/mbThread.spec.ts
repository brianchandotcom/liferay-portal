/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {messageBoardsPagesTest} from '../../fixtures/messageBoardsTest';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	messageBoardsPagesTest,
	loginTest()
);

test(
	'Can reply, cancel and reply again to a thread',
	{
		tag: '@LPD-39085',
	},
	async ({messageBoardsEditThreadPage, page, site}) => {
		await messageBoardsEditThreadPage.goto(site.friendlyUrlPath);

		const mbTitle = getRandomString();

		await messageBoardsEditThreadPage.publishNewBasicThread(
			mbTitle,
			site.friendlyUrlPath
		);

		await page.getByRole('button', {name: 'Reply'}).click();

		await expect(messageBoardsEditThreadPage.bodyTextBox).toBeVisible();

		await page.getByRole('button', {name: 'Cancel'}).click();

		await page.getByRole('button', {name: 'Reply'}).click();

		await expect(messageBoardsEditThreadPage.bodyTextBox).toBeVisible();
	}
);

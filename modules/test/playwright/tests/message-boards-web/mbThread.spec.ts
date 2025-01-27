/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {messageBoardsPagesTest} from '../../fixtures/messageBoardsTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
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

test(
	'BBcode not parsed as HTML',
	{
		tag: '@LPD-45630',
	},
	async ({messageBoardsEditThreadPage, page, site}) => {
		await messageBoardsEditThreadPage.publishNewBasicThread(
			'MB subject',
			`
- [b]BBcode list item strong / bold[/b]

- Another BBCode list item
(Lorem ipsum dolor sit amet consectetur adipisicing elit)

- Lorem ipsum dolor sit amet consectetur adipisicing   40  elit
   (consectetur adipisicing elit) ab 01/2022
`,
			site.friendlyUrlPath
		);

		expect(page.getByText('&nbsp;')).toBeHidden();
	}
);

test(
	'Can open two different replies',
	{
		tag: '@LPD-45199',
	},
	async ({
		apiHelpers,
		messageBoardsEditThreadPage,
		messageBoardsPage,
		page,
		site,
	}) => {
		const threadTitle = 'MB Thread title';
		const messageBody = 'MB Thread message';

		const messageBoardThread =
			await apiHelpers.headlessDelivery.postMessageBoardThread({
				articleBody: getRandomString(),
				headline: threadTitle,
				siteId: site.id,
			});

		await apiHelpers.headlessDelivery.postMessageBoardMessage({
			articleBody: messageBody,
			messageBoardThreadId: messageBoardThread.id,
		});

		await messageBoardsPage.goto(site.friendlyUrlPath);

		await page.getByRole('link', {name: threadTitle}).click();

		await page.getByRole('button', {name: 'Reply'}).click();

		await expect(messageBoardsEditThreadPage.bodyTextBox).toBeVisible();

		const actionsButton = page
			.locator('.panel-heading')
			.filter({hasText: 'RE: ' + threadTitle})
			.getByRole('button', {name: 'Actions'});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('link', {
				exact: true,
				name: 'Reply',
			}),
			trigger: actionsButton,
		});

		const editors = await page.locator('iframe');

		await expect(editors).toHaveCount(2);
	}
);

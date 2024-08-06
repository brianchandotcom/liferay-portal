/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';
import path from 'path';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {messageBoardsPagesTest} from '../../fixtures/messageBoardsTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	messageBoardsPagesTest,
	loginTest(),
	workflowPagesTest
);

test('LPD-25630 Show the status to guest user', async ({
	messageBoardsEditThreadPage,
	messageBoardsPage,
	messageBoardsWidgetPage,
	page,
	site,
	workflowPage,
}) => {
	await messageBoardsPage.setGuestCategoryPermissions(site.friendlyUrlPath);

	await messageBoardsEditThreadPage.publishNewBasicTread(
		'Thread Subject',
		'Thread Body',
		site.friendlyUrlPath
	);

	await workflowPage.goto(site.friendlyUrlPath);

	await workflowPage.changeWorkflow(
		'Message Boards Message',
		'Single Approver'
	);

	const layout = await messageBoardsWidgetPage.addMessageBoardsPortlet(site);

	await messageBoardsWidgetPage.addGuestReply(
		site,
		layout,
		'Submit for Workflow'
	);

	await expect(page.getByText('Pending')).toBeVisible();
});

test('LPD-29524 Search for message board thread by keywords', async ({
	apiHelpers,
	messageBoardsWidgetPage,
	page,
	site,
}) => {
	const messageBoardThread1 =
		await apiHelpers.headlessDelivery.postMessageBoardThread({
			articleBody: getRandomString(),
			headline: getRandomString(),
			siteId: site.id,
		});
	const messageBoardThread2 =
		await apiHelpers.headlessDelivery.postMessageBoardThread({
			articleBody: getRandomString(),
			headline: getRandomString(),
			siteId: site.id,
		});

	await messageBoardsWidgetPage.addMessageBoardsPortlet(site);

	await expect(
		page.getByRole('link', {name: messageBoardThread1.headline})
	).toBeVisible();

	await expect(
		page.getByRole('link', {name: messageBoardThread2.headline})
	).toBeVisible();

	await page.getByTestId('searchInput').fill(messageBoardThread1.headline);
	await page.getByTestId('searchButton').click();

	await expect(
		page.getByRole('link', {name: messageBoardThread1.headline})
	).toBeVisible();

	await expect(
		page.getByRole('link', {name: messageBoardThread2.headline})
	).toBeHidden();
});

test('LPD-27633 Do not show site in breadcrumb', async ({
	messageBoardsWidgetPage,
	page,
	site,
}) => {
	const layout = await messageBoardsWidgetPage.addMessageBoardsPortlet(site);

	const categoryName = getRandomString();

	await messageBoardsWidgetPage.addCategory(site, layout, categoryName);

	const searchMenu = page.locator(
		'[id="_com_liferay_message_boards_web_portlet_MBPortlet_mbCategoriesSearchContainer_1_menu"]'
	);

	await searchMenu.waitFor();
	await searchMenu.click();

	await page.getByRole('menuitem', {name: 'Move'}).click();

	await page.getByRole('button', {name: 'Select'}).click();

	await expect(
		page
			.frameLocator('iframe[title="Select Category"]')
			.getByText(site.name)
	).toBeHidden();
});

test('LPD-33132 Posting a Document to Forums', async ({
	messageBoardsEditThreadPage,
	page,
	site,
}) => {
	const fileName = 'attachment , file.txt';

	await messageBoardsEditThreadPage.publishNewBasicTread(
		'Thread Subject',
		'Thread Body',
		site.friendlyUrlPath,
		path.join(__dirname, '/dependencies/' + fileName)
	);

	await expect(page.locator('li').filter({hasText: fileName})).toBeVisible();
});

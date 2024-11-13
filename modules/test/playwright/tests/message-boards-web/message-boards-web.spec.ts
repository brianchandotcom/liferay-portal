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

test(
	'Thread Priorities can be translated',
	{tag: '@LPD-41689'},
	async ({messageBoardsPage, page, site}) => {
		await messageBoardsPage.goToThreadPriorities(site.friendlyUrlPath);

		const languageLocator = page.getByLabel('Localized Language');

		await languageLocator.waitFor();

		await languageLocator.selectOption('hu_HU');

		const expectedPriorityName = 'test';

		const priorityNameLocator = page.locator('[id$="priorityName0_temp"]');

		await priorityNameLocator.fill(expectedPriorityName);

		await page.getByRole('button', {name: 'Save'}).click();

		await expect(priorityNameLocator).toBeHidden();

		await languageLocator.selectOption('hu_HU');

		expect(await priorityNameLocator.inputValue()).toBe(
			expectedPriorityName
		);
	}
);

test(
	'Show the status to guest user',
	{tag: '@LPD-25630'},
	async ({
		messageBoardsEditThreadPage,
		messageBoardsPage,
		messageBoardsWidgetPage,
		page,
		site,
		workflowPage,
	}) => {
		await messageBoardsPage.setGuestCategoryPermissions(
			site.friendlyUrlPath
		);

		await messageBoardsEditThreadPage.publishNewBasicThread(
			'Thread Subject',
			'Thread Body',
			site.friendlyUrlPath
		);

		await workflowPage.goto(site.friendlyUrlPath);

		await workflowPage.changeWorkflow(
			'Message Boards Message',
			'Single Approver'
		);

		const layout =
			await messageBoardsWidgetPage.addMessageBoardsPortlet(site);

		await messageBoardsWidgetPage.addGuestReply(
			site,
			layout,
			'Submit for Workflow'
		);

		await expect(page.getByText('Pending')).toBeVisible();
	}
);

test(
	'Search for message board thread by keywords',
	{tag: '@LPD-29524'},
	async ({apiHelpers, messageBoardsWidgetPage, page, site}) => {
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

		await page
			.getByTestId('searchInput')
			.fill(messageBoardThread1.headline);
		await page.getByTestId('searchButton').click();

		await expect(
			page.getByRole('link', {name: messageBoardThread1.headline})
		).toBeVisible();

		await expect(
			page.getByRole('link', {name: messageBoardThread2.headline})
		).toBeHidden();
	}
);

test(
	'Do not show site in breadcrumb',
	{tag: '@LPD-27633'},
	async ({messageBoardsWidgetPage, page, site}) => {
		const layout =
			await messageBoardsWidgetPage.addMessageBoardsPortlet(site);

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
	}
);

test(
	'Posting a Document to Forums',
	{tag: '@LPD-33132'},
	async ({messageBoardsEditThreadPage, page, site}) => {
		const fileName = 'attachment , file.txt';

		await messageBoardsEditThreadPage.publishNewBasicThread(
			'Thread Subject',
			'Thread Body',
			site.friendlyUrlPath,
			path.join(__dirname, '/dependencies/' + fileName)
		);

		await expect(
			page.locator('li').filter({hasText: fileName})
		).toBeVisible();
	}
);

test(
	'Message Boards Admin: Change delta to a higher value when on last page',
	{tag: '@LPD-37727'},
	async ({apiHelpers, messageBoardsPage, page, site}) => {
		for (let i = 0; i < 5; i++) {
			await apiHelpers.headlessDelivery.postMessageBoardThread({
				articleBody: getRandomString(),
				headline: 'Thread with headline #' + i,
				siteId: site.id,
			});
		}

		await messageBoardsPage.goto(site.friendlyUrlPath);

		await page.getByLabel('Items per Page').click();
		await page.getByRole('option', {name: /4\s+Entries per Page/}).click();

		await expect(
			page.getByRole('link', {name: /Thread with headline #\d+/})
		).toHaveCount(4);

		await page.getByRole('link', {name: /Page\s+2/}).click();

		await expect(
			page.getByRole('link', {name: /Thread with headline #\d+/})
		).toHaveCount(1);

		await page.getByLabel('Items per Page').click();
		await page.getByRole('option', {name: /8\s+Entries per Page/}).click();

		await expect(
			page.getByRole('link', {name: /Thread with headline #\d+/})
		).toHaveCount(5);
	}
);

test(
	'Message Boards Widget: Change delta to a higher value when on last page',
	{tag: '@LPD-39570'},
	async ({apiHelpers, messageBoardsWidgetPage, page, site}) => {
		for (let i = 0; i < 5; i++) {
			await apiHelpers.headlessDelivery.postMessageBoardThread({
				articleBody: getRandomString(),
				headline: 'Thread with headline #' + i,
				siteId: site.id,
			});
		}

		await messageBoardsWidgetPage.addMessageBoardsPortlet(site);

		await page.getByLabel('Items per Page').click();
		await page.getByRole('option', {name: /4\s+Entries per Page/}).click();

		await expect(
			page.getByRole('link', {name: /Thread with headline #\d+/})
		).toHaveCount(4);

		await page.getByRole('link', {name: /Page\s+2/}).click();

		await expect(
			page.getByRole('link', {name: /Thread with headline #\d+/})
		).toHaveCount(1);

		await page.getByLabel('Items per Page').click();
		await page.getByRole('option', {name: /8\s+Entries per Page/}).click();

		await expect(
			page.getByRole('link', {name: /Thread with headline #\d+/})
		).toHaveCount(5);
	}
);

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {messageBoardsPagesTest} from '../../fixtures/messageBoardsTest';
import {workflowPagesTest} from '../../fixtures/workflowPagesTest';
export const test = mergeTests(
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

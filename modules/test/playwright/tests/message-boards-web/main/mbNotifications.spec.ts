/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {messageBoardsPagesTest} from '../../../fixtures/messageBoardsTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../../utils/getRandomString';
import {performUserSwitch, userData} from '../../../utils/performLogin';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	messageBoardsPagesTest
);

test('Can view a website notification for a new thread in a subscribed category', async ({
	apiHelpers,
	messageBoardsEditThreadPage,
	messageBoardsPage,
	messageBoardsWidgetPage,
	page,
	site,
}) => {
	const categoryName = getRandomString();

	await apiHelpers.headlessDelivery.postSiteMessageBoardSection({
		siteId: site.id,
		title: categoryName,
	});

	const layout = await messageBoardsWidgetPage.addMessageBoardsPortlet(site);

	// A second user subscribes to the category

	const administratorRole =
		await apiHelpers.headlessAdminUser.getRoleByName('Administrator');

	const subscriber = await apiHelpers.headlessAdminUser.postUserAccount();

	await apiHelpers.headlessAdminUser.assignUserToRole(
		administratorRole.externalReferenceCode,
		subscriber.id
	);

	userData[subscriber.alternateName] = {
		name: subscriber.givenName,
		password: 'test',
		surname: subscriber.familyName,
	};

	await performUserSwitch(page, subscriber.alternateName);

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

	await page.getByRole('link', {name: categoryName}).click();

	// Subscribe to the category through its actions menu

	await clickAndExpectToBeVisible({
		autoClick: true,
		target: page.locator('.dropdown-menu').getByText('Subscribe', {
			exact: true,
		}),
		trigger: page.locator('a.component-action.dropdown-toggle').last(),
	});

	// The administrator posts a new thread in the category

	await performUserSwitch(page, 'test');

	await messageBoardsPage.goto(site.friendlyUrlPath);

	await page.getByRole('link', {name: categoryName}).click();

	await messageBoardsPage.goToCreateNewThread();

	await messageBoardsEditThreadPage.subjectSelector.fill(getRandomString());
	await messageBoardsEditThreadPage.bodyTextBox.fill(getRandomString());
	await messageBoardsEditThreadPage.publishButton.click();

	// The subscriber is notified about the new thread

	await performUserSwitch(page, subscriber.alternateName);

	await expect(async () => {
		await page.reload();

		await expect(page.locator('a.panel-notifications-count')).toHaveText(
			'1',
			{timeout: 5000}
		);
	}).toPass();

	await page.locator('a.panel-notifications-count').click();

	await expect(
		page.getByText('Test Test added a new message boards message.')
	).toBeVisible();
});

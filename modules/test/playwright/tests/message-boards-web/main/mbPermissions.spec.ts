/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {messageBoardsPagesTest} from '../../../fixtures/messageBoardsTest';
import getRandomString from '../../../utils/getRandomString';
import {
	performLogout,
	performUserSwitch,
	userData,
} from '../../../utils/performLogin';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	messageBoardsPagesTest
);

test(
	'A site member cannot edit or delete a thread without permissions',
	{tag: ['@LPS-136934', '@LPS-136936']},
	async ({
		apiHelpers,
		messageBoardsEditThreadPage,
		messageBoardsWidgetPage,
		page,
		site,
	}) => {
		const headline = getRandomString();

		const layout =
			await messageBoardsWidgetPage.addMessageBoardsPortlet(site);

		// The administrator creates a thread through the UI

		await messageBoardsEditThreadPage.gotoAndPublishNewBasicThread(
			headline,
			getRandomString(),
			site.friendlyUrlPath
		);

		// A site member is added to the site

		const siteMemberRole =
			await apiHelpers.headlessAdminUser.getRoleByName('Site Member');

		const member = await apiHelpers.headlessAdminUser.postUserAccount();

		await apiHelpers.headlessAdminUser.assignUserToSite(
			siteMemberRole.id,
			site.id,
			member.id
		);

		userData[member.alternateName] = {
			name: member.givenName,
			password: 'test',
			surname: member.familyName,
		};

		await performUserSwitch(page, member.alternateName);

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		// The member can see the thread

		await expect(
			page.getByRole('link', {name: headline}).first()
		).toBeVisible();

		// The thread action menu offers Subscribe but not Edit or Delete

		const dropdownMenu = page.locator('.dropdown-menu');

		await expect(async () => {
			await page
				.locator('a.component-action.dropdown-toggle')
				.first()
				.click();

			await expect(
				dropdownMenu.getByText('Subscribe', {exact: true}).first()
			).toBeVisible({timeout: 3000});
		}).toPass();

		await expect(dropdownMenu.getByText('Edit', {exact: true})).toHaveCount(
			0
		);
		await expect(
			dropdownMenu.getByText('Delete', {exact: true})
		).toHaveCount(0);
	}
);

test(
	'A guest cannot view a thread without permissions',
	{tag: '@LPS-136939'},
	async ({apiHelpers, messageBoardsWidgetPage, page, site}) => {
		const headline = getRandomString();

		const layout =
			await messageBoardsWidgetPage.addMessageBoardsPortlet(site);

		// A thread seeded through the API carries no guest view permission

		await apiHelpers.headlessDelivery.postMessageBoardThread({
			articleBody: getRandomString(),
			headline,
			siteId: site.id,
		});

		// The administrator sees the thread on the widget page

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		await expect(
			page.getByRole('link', {name: headline}).first()
		).toBeVisible();

		// A guest cannot see the thread

		await performLogout(page);

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		await expect(
			page.getByText('There are no threads or categories.')
		).toBeVisible();

		await expect(page.getByRole('link', {name: headline})).toBeHidden();
	}
);

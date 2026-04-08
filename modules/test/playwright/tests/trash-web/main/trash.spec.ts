/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {performUserSwitch, userData} from '../../../utils/performLogin';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {waitForAlert} from '../../../utils/waitForAlert';
import {blogsPagesTest} from '../../blogs-web/main/fixtures/blogsPagesTest';

const test = mergeTests(dataApiHelpersTest, blogsPagesTest, loginTest());

test(
	'Cannot view trash entry from another site in current site recycle bin',
	{tag: '@LPD-79076'},
	async ({apiHelpers, blogsPage, page}) => {
		const blogName = `Blog ${getRandomString()}`;
		const siteOneName = `Site One ${getRandomString()}`;
		const siteTwoName = `Site Two ${getRandomString()}`;
		let siteOne: Site;
		let siteTwo: Site;
		let blog;
		let trashEntryId;
		let user;

		await test.step('Create a new Site', async () => {
			siteOne = await apiHelpers.headlessAdminSite.postSite({
				name: siteOneName,
			});
		});

		await test.step('Create a new blog in first Site', async () => {
			blog = await apiHelpers.headlessDelivery.postBlog(siteOne.id, {
				headline: blogName,
			});
		});

		await test.step('Delete the created blog so it goes into the Recycle Bin', async () => {
			await blogsPage.goto(siteOne.friendlyUrlPath);

			await blogsPage.goToBlogEntryAction('Delete', blog.title);

			await waitForAlert(
				page,
				`Success: The element ${blogName} was moved to the Recycle Bin.`
			);
		});

		await test.step('Go to Recycle Bin to confirm deletion and get trash entry ID', async () => {
			await page.goto(
				`/group${siteOne.friendlyUrlPath}${PORTLET_URLS.recycleBin}`
			);

			trashEntryId = await page
				.locator(
					`[id="_com_liferay_trash_web_portlet_TrashPortlet_trash_1"]`
				)
				.locator('input[type=checkbox]')
				.inputValue();
		});

		await test.step('Create a new second Site', async () => {
			siteTwo = await apiHelpers.headlessAdminSite.postSite({
				name: siteTwoName,
			});
		});

		await test.step('Create new site administrator user for second Site and login as site administrator', async () => {
			user = await apiHelpers.headlessAdminUser.postUserAccount();

			userData[user.alternateName] = {
				name: user.givenName,
				password: 'test',
				surname: user.familyName,
			};

			const siteAdminRole =
				await apiHelpers.headlessAdminUser.getRoleByName(
					'Site Administrator'
				);

			await apiHelpers.headlessAdminUser.assignUserToSite(
				siteAdminRole.id,
				siteTwo.id,
				user.id
			);

			await performUserSwitch(page, user.alternateName);
		});

		await test.step('Go to the deleted content in Recycle Bin of second site and try to view the deleted content', async () => {
			await page.goto(
				`/group${siteTwo.friendlyUrlPath}${PORTLET_URLS.recycleBin}entry/${trashEntryId}`
			);

			await expect(
				page.getByText('You do not have the required permissions')
			).toBeVisible();
		});
	}
);

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests, test} from '@playwright/test';

import {ApiHelpers} from '../helpers/ApiHelpers';
import getRandomString from '../utils/getRandomString';
import {loginTest} from './loginTest';

const isolatedSiteFixture = test.extend<{
	site: Site;
}>({
	site: [
		async ({page}, use) => {
			await page.goto('/');

			const apiHelpers = new ApiHelpers(page);

			let site;

			try {

				// Create site and go Site Settings

				site = await apiHelpers.headlessSite.createSite(
					getRandomString()
				);

				await page.goto(
					`/group${site.friendlyUrlPath}/~/control_panel/manage/-/site/settings`
				);

				await use(site);
			}
			catch {
				throw new Error(
					`Isolated site could not be created, the default site will be used instead`
				);
			}
			finally {

				// Delete the site

				if (site?.id) {
					await apiHelpers.headlessSite.deleteSite(site.id);
				}
			}
		},
		{auto: true},
	],
});

const isolatedSiteTest = mergeTests(loginTest, isolatedSiteFixture);

export {isolatedSiteTest};

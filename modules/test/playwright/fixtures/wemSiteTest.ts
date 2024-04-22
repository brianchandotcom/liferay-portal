/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {ApiHelpers} from '../helpers/ApiHelpers';
import {WEM_SITE_ERC} from '../setup/wem-site/constants';
import {backendPageTest} from './backendPageTest';

const test = mergeTests(backendPageTest);

const wemSiteTest = test.extend<{
	wemSite: Site;
}>({
	wemSite: [
		async ({backendPage}, use) => {
			await backendPage.goto('/');

			const apiHelpers = new ApiHelpers(backendPage);

			let site;

			try {
				site = await apiHelpers.headlessSite.getSiteByERC(WEM_SITE_ERC);

				await use(site);
			}
			catch {
				throw new Error(
					`Web Experience site could not be fetched, make sure this project has wem-site-setup as dependency`
				);
			}
		},
		{auto: true},
	],
});

export {wemSiteTest, WEM_SITE_ERC};

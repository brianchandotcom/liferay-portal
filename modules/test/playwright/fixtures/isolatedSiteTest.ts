/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {ApiHelpers} from '../helpers/ApiHelpers';
import getRandomString from '../utils/getRandomString';
import {backendPageTest} from './backendPageTest';

const test = mergeTests(backendPageTest);

const isolatedSiteTest = test.extend<{
	site: Site;
}>({
	site: [
		async ({backendPage}, use) => {
			await backendPage.goto('/');

			const apiHelpers = new ApiHelpers(backendPage);

			let site: Site;

			try {
				site = await apiHelpers.headlessAdminSite.postSite({
					name: getRandomString(),
				});

				await use(site);
			}
			catch {
				throw new Error(
					`Isolated site could not be created, the default site will be used instead`
				);
			}
			finally {
				if (site?.externalReferenceCode) {
					await apiHelpers.headlessAdminSite.deleteSite(
						site.externalReferenceCode
					);
				}
			}
		},
		{auto: true},
	],
});

export {isolatedSiteTest};

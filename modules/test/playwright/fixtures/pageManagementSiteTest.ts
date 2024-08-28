/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {ApiHelpers} from '../helpers/ApiHelpers';
import {PAGE_MANAGEMENT_SITE_ERC} from '../tests/setup/page-management-site/constants';
import {backendPageTest} from './backendPageTest';

const test = mergeTests(backendPageTest);

const pageManagementSiteTest = test.extend<{
	pageManagementSite: Site;
}>({
	pageManagementSite: [
		async ({backendPage}, use) => {
			await backendPage.goto('/');

			const apiHelpers = new ApiHelpers(backendPage);

			let site: Site;

			try {
				site = await apiHelpers.headlessSite.getSiteByERC(
					PAGE_MANAGEMENT_SITE_ERC
				);

				await use(site);
			}
			catch {
				throw new Error(
					`Page Management site could not be fetched, make sure this project has page-management-site-setup as dependency`
				);
			}
			finally {

				// Delete all pages after each test

				const {items} = await apiHelpers.headlessDelivery.getSitePages(
					site.id
				);

				if (items) {
					for (const page of items) {
						await apiHelpers.jsonWebServicesLayout.deleteLayout(
							page.id
						);
					}
				}
			}
		},
		{auto: true},
	],
});

export {PAGE_MANAGEMENT_SITE_ERC, pageManagementSiteTest};

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {ApiHelpers} from '../helpers/ApiHelpers';
import {
	DEFAULT_ENTRIES_ERCS,
	OBJECT_ENTITIES,
} from '../tests/setup/page-management-site/constants/objects';
import {PAGE_MANAGEMENT_SITE_ERC} from '../tests/setup/page-management-site/constants/site';
import {deleteObjectEntries} from '../utils/deleteObjectEntries';
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

				// Delete also all existing object entries

				const names = Object.values(OBJECT_ENTITIES).map(
					(entity) => entity.name
				);

				for (const entityName of names) {
					await deleteObjectEntries({
						apiHelpers,
						entityName,
						excludeERC: DEFAULT_ENTRIES_ERCS,
						scopeKey: site.key,
					});
				}
			}
		},
		{auto: true},
	],
});

export {PAGE_MANAGEMENT_SITE_ERC, pageManagementSiteTest};

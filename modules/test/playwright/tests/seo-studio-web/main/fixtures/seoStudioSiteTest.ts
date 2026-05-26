/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';
import path from 'path';

import {backendPageTest} from '../../../../fixtures/backendPageTest';
import {ApiHelpers} from '../../../../helpers/ApiHelpers';
import getRandomString from '../../../../utils/getRandomString';

const SITE_INITIALIZER_PATH = path.resolve(
	__dirname,
	'../../../../../../dxp/apps/seo-studio/seo-studio-site-initializer/src/main/resources/site-initializer'
);

const test = mergeTests(backendPageTest);

export const seoStudioSiteTest = test.extend<{
	seoStudioSite: Site;
}>({
	seoStudioSite: [
		async ({backendPage}, use) => {
			await backendPage.goto('/');

			const apiHelpers = new ApiHelpers(backendPage);

			let seoStudioSite: Site | undefined;

			try {
				seoStudioSite =
					await apiHelpers.headlessAdminSite.postSiteSiteInitializer(
						{name: getRandomString()},
						SITE_INITIALIZER_PATH
					);

				await use(seoStudioSite);
			}
			finally {
				if (seoStudioSite?.externalReferenceCode) {
					await apiHelpers.headlessAdminSite.deleteSite(
						seoStudioSite.externalReferenceCode
					);
				}
			}
		},
		{scope: 'test'},
	],
});

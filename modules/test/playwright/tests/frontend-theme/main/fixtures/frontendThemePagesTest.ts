/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {appManagerPagesTest} from '../../../../fixtures/appManagerPagesTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {pageEditorPagesTest} from '../../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../../fixtures/pagesAdminPagesTest';
import {BundleBlacklistPage} from '../../../../pages/marketplace-app-manager-web/BundleBlacklistPage';
import {PageHelper} from '../helpers/PageHelper';
import {ThemeHelper} from '../helpers/ThemeHelper';

interface FrontendThemePagesTest {
	pageHelper: PageHelper;
	themeHelper: ThemeHelper;
}

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	pageEditorPagesTest,
	pagesAdminPagesTest,
	appManagerPagesTest
);

export const frontendThemePagesTest = test.extend<FrontendThemePagesTest>({
	pageHelper: async ({apiHelpers, page, pageEditorPage, site}, use) => {
		await use(new PageHelper(apiHelpers, page, pageEditorPage, site));
	},

	themeHelper: async (
		{appManagerPage, page, pageEditorPage, pagesAdminPage, site},
		use
	) => {
		const bundleBlacklistPage = new BundleBlacklistPage(page);

		await use(
			new ThemeHelper(
				appManagerPage,
				bundleBlacklistPage,
				page,
				pageEditorPage,
				pagesAdminPage,
				site
			)
		);
	},
});

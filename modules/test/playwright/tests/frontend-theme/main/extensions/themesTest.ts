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
import {AssertionFixture} from '../fixtures/AssertionFixture';
import {DesignFixture} from '../fixtures/DesignFixture';
import {PageFixture} from '../fixtures/PageFixture';
import {ThemeFixture} from '../fixtures/ThemeFixture';

interface FixturesExtension {
	assertionFixture: AssertionFixture;
	designFixture: DesignFixture;
	pageFixture: PageFixture;
	themeFixture: ThemeFixture;
}

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	pageEditorPagesTest,
	pagesAdminPagesTest,
	appManagerPagesTest
);

export const themesTest = test.extend<FixturesExtension>({
	assertionFixture: async ({page}, use) => {
		await use(new AssertionFixture(page));
	},

	designFixture: async (
		{page, pageEditorPage, pagesAdminPage, site},
		use
	) => {
		await use(
			new DesignFixture(page, pageEditorPage, pagesAdminPage, site)
		);
	},

	pageFixture: async ({apiHelpers, page, pageEditorPage, site}, use) => {
		await use(new PageFixture(apiHelpers, page, pageEditorPage, site));
	},

	themeFixture: async (
		{appManagerPage, page, pageEditorPage, pagesAdminPage, site},
		use
	) => {
		await use(
			new ThemeFixture(
				appManagerPage,
				page,
				pageEditorPage,
				pagesAdminPage,
				site
			)
		);
	},
});

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {pageEditorPagesTest} from '../../../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../../../fixtures/pagesAdminPagesTest';
import {DesignFixture} from '../fixtures/DesignFixture';

interface FrontendCssTestFixtures {
	designFixture: DesignFixture;
}

const test = mergeTests(
	pageEditorPagesTest,
	pagesAdminPagesTest,
	isolatedSiteTest
);

export const frontendCssTest = test.extend<FrontendCssTestFixtures>({
	designFixture: async ({pageEditorPage, pagesAdminPage, site}, use) => {
		await use(new DesignFixture(pageEditorPage, pagesAdminPage, site));
	},
});

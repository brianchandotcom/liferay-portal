/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import getRandomString from '../../utils/getRandomString';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pagesAdminPagesTest,
	pagesPagesTest,
	pageEditorPagesTest,
	pageViewModePagesTest
);

test(
	'Can convert a widget page to a content page via management toolbar',
	{
		tag: '@LPS-149232',
	},
	async ({apiHelpers, page, pagesAdminPage, site, widgetPagePage}) => {

		// Accept dialog

		page.on('dialog', async (dialog) => {
			await dialog.accept();
		});

		// Create a content page

		const layoutTitle = getRandomString();

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: layoutTitle,
		});

		// Go to view mode and add asset publisher widget

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		await widgetPagePage.addPortlet('Asset Publisher');

		// Go to page administration

		await pagesAdminPage.goto(site.friendlyUrlPath);

		// Bulk convert

		await page.getByLabel('Select All Items on the Page').check();

		await page
			.getByRole('button', {name: 'Convert to Content Page'})
			.click();

		// Assert conversion

		await expect(
			page.locator('.miller-columns-item').filter({hasText: layoutTitle})
		).toHaveText(/Content Page/);

		// Assert asset publisher widget in edit mode

		await pagesAdminPage.editPage(layoutTitle);

		await expect(page.locator('.portlet-asset-publisher')).toBeVisible();
	}
);

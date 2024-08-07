/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {journalPagesTest} from '../journal-web/fixtures/journalPagesTest';

const baseTest = mergeTests(
	documentLibraryPagesTest,
	isolatedSiteTest,
	journalPagesTest,
	loginTest()
);

baseTest(
	'Check if Item Selectors Browser Breadcrumb is updated after change folder',
	{
		tag: '@LPD-31633',
	},
	async ({documentLibraryPage, journalEditArticlePage, page, site}) => {
		await documentLibraryPage.goto(site.friendlyUrlPath);
		await documentLibraryPage.goToCreateNewFolder();
		const folderName = getRandomString();
		await page.getByLabel('Name Required').fill(folderName);
		await page.getByRole('button', {name: 'Save'}).click();

		await journalEditArticlePage.goto({siteUrl: site.friendlyUrlPath});

		await page.getByLabel('Image', {exact: true}).click();

		const iframeFolder = page
			.frameLocator('iframe[title="Select Item"]')
			.getByRole('link', {name: folderName});
		await iframeFolder.click();
		await expect(iframeFolder).toBeVisible();
	}
);

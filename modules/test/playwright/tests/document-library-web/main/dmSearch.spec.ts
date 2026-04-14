/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {documentLibraryPagesTest} from '../../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(
	apiHelpersTest,
	documentLibraryPagesTest,
	featureFlagsTest({
		'LPD-11313': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test(
	'DM Search bar hint',
	{tag: '@LPD-6878'},
	async ({documentLibraryPage, page, site}) => {
		await documentLibraryPage.goto(site.friendlyUrlPath);

		await expect(page.getByPlaceholder('Search')).toBeVisible();
	}
);

test(
	'Search bar is not disabled after zero-result search',
	{tag: '@LPD-86105'},
	async ({apiHelpers, documentLibraryPage, page, site}) => {
		await apiHelpers.headlessDelivery.postDocumentFolder(site.id);

		await documentLibraryPage.goto(site.friendlyUrlPath);

		const searchInput = page.getByRole('searchbox');

		await searchInput.fill(getRandomString());
		await searchInput.press('Enter');

		await expect(searchInput).not.toBeDisabled();
	}
);

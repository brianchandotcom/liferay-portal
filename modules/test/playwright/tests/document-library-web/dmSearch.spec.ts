/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';

const test = mergeTests(
	documentLibraryPagesTest,
	featureFlagsTest({
		'LPD-11313': true,
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

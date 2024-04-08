/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	loginTest(),
	featureFlagsTest({
		'LPD-11313': true,
	}),
	documentLibraryPagesTest
);

test('LPD-6878 DM Search bar hint', async ({documentLibraryPage, page}) => {
	await documentLibraryPage.goto();

	await expect(page.getByPlaceholder('Search')).toBeVisible();
});

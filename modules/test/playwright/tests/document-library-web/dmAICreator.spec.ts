/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {documentLibraryPages}  from '../../fixtures/documentLibraryPages';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';

const testFeatureFlagsEnabled = mergeTests(
	loginTest,
	featureFlagsTest({
		'LPD-10793': true,
	}),
	documentLibraryPages
);

testFeatureFlagsEnabled(
	'can see default folder in  DM',
	async ({
		documentLibraryPage,
		page,
	}) => {
        await documentLibraryPage.goto();

		const defaultFolder = page.getByTitle('Provided by Liferay');

		await expect(defaultFolder).toBeVisible();
	}
);

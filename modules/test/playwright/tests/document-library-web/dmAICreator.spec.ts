/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {documentLibraryPages} from '../../fixtures/documentLibraryPages';
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
	'Create AI Image option in Management Toolbar without API Key opens an alert',
	async ({documentLibraryPage, page}) => {
		await documentLibraryPage.goto();

		await documentLibraryPage.new();

		await page
			.getByRole('menuitem', {
				name: 'Create AI Image',
			})
			.click();

		await expect(page.getByText('Configure OpenAI')).toBeVisible();
	}
);

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
		'LPD-19787': true,
	}),
	isolatedSiteTest,
	loginTest()
);

test(
	'DM Permissions initial status',
	{tag: ['@LPD-39898', '@LPD-39899']},
	async ({documentLibraryEditFilePage, page, site}) => {
		await documentLibraryEditFilePage.goto(site.friendlyUrlPath);

		await documentLibraryEditFilePage.openFieldset('Permissions');

		await expect(
			page.getByText('Viewable and downloadable by')
		).toBeVisible();
		await expect(
			page.getByRole('cell', {exact: true, name: 'Role'})
		).toBeVisible();
	}
);

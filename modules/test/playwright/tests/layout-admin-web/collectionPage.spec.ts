/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import getRandomString from '../../utils/getRandomString';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pagesAdminPagesTest
);

test(
	'No alert shown when view the collection page based on collection with XSS name',
	{
		tag: '@LPS-129109',
	},
	async ({apiHelpers, page, pagesAdminPage, site}) => {

		// Create a collection

		await apiHelpers.jsonWebServicesAssetListEntry.addDynamicAssetListEntry(
			{
				groupId: site.id,
				title: '<script>alert(123);</script>',
			}
		);

		// Add listener with expect, so it fails when a browser dialog is shown

		page.on('dialog', async (dialog) => {
			dialog.accept();

			expect(
				dialog.message(),
				'This alert should not be shown'
			).toBeNull();
		});

		// Create collection page and go to view mode to check dialog is not shown

		await pagesAdminPage.goto(site.friendlyUrlPath);

		const layoutName = getRandomString();

		await pagesAdminPage.addCollectionPage({
			collectionName: '<script>alert(123);</script>',
			name: layoutName,
		});

		await pagesAdminPage.clickOnAction('View', layoutName);
	}
);

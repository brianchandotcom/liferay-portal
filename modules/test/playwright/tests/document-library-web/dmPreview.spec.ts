/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {documentLibraryPagesTest} from '../../fixtures/documentLibraryPages.fixtures';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';

const test = mergeTests(
	documentLibraryPagesTest,
	isolatedSiteTest,
	loginTest()
);

test(
	'DM Preview page has not a fixed navbar',
	{tag: '@LPD-26290'},
	async ({documentLibraryEditFilePage, documentLibraryPage, page, site}) => {
		const title = 'DM File Entry title';

		await documentLibraryEditFilePage.publishNewBasicFileEntry(
			title,
			site.friendlyUrlPath
		);

		await page.getByRole('link', {name: title}).click();

		const navItem = await page.locator(
			'nav.component-tbar.subnav-tbar-light'
		);

		const navItemPosition = await navItem.evaluate((element) => {
			return window
				.getComputedStyle(element)
				.getPropertyValue('position');
		});

		await expect(navItemPosition).not.toBe('fixed');

		await documentLibraryPage.goto(site.friendlyUrlPath);
	}
);

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
import fillAndClickOutside from '../../utils/fillAndClickOutside';
import getRandomString from '../../utils/getRandomString';
import {PORTLET_URLS} from '../../utils/portletUrls';

export const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	pageEditorPagesTest,
	loginTest()
);

test(
	'User can search locked pages',
	{
		tag: ['@LPS-182024', '@LPS-194499'],
	},
	async ({apiHelpers, context, pageEditorPage, site}) => {

		// Create a content page and navigate to view mode

		const layoutTitle = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: layoutTitle,
		});

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		// Add new fragment to content page to lock the content page

		await pageEditorPage.addFragment('Basic Components', 'Heading');

		// Navigate to locked pages in a new page

		const newPage = await context.newPage();

		await newPage.goto(
			`/group${site.friendlyUrlPath}${PORTLET_URLS.lockedPages}`
		);

		// Assert locked page name is a link and it opens in a new window

		await expect(
			newPage.getByLabel(layoutTitle, {exact: true})
		).toHaveAttribute('target', '_blank');

		// Assert search

		await fillAndClickOutside(
			newPage,
			newPage.getByPlaceholder('Search for'),
			'Welcome'
		);

		const searchButton = newPage.getByRole('button', {name: 'Search'});

		await searchButton.click();

		await expect(
			newPage.getByText('No locked pages were found.', {exact: true})
		).toBeVisible();

		await newPage.getByRole('button', {name: 'Clear'}).click();

		await expect(
			newPage.getByLabel(layoutTitle, {exact: true})
		).toBeVisible();
	}
);

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {instanceSettingsPagesTest} from '../../fixtures/instanceSettingsPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import fillAndClickOutside from '../../utils/fillAndClickOutside';
import getRandomString from '../../utils/getRandomString';
import {PORTLET_URLS} from '../../utils/portletUrls';

export const test = mergeTests(
	apiHelpersTest,
	instanceSettingsPagesTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	pageEditorPagesTest,
	loginTest()
);

const testWithIsolatedSite = mergeTests(test, isolatedSiteTest);

test(
	'Time without autosave is a number between 1 and 99999',
	{
		tag: ['@LPS-182060'],
	},
	async ({instanceSettingsPage, page}) => {

		// Go to locked pages in instance settings

		await instanceSettingsPage.goToInstanceSetting('Pages', 'Locked Pages');

		// Assert time without autosave is a number between 1 and 999999

		await fillAndClickOutside(
			page,
			page.getByLabel('Time Without Autosave'),
			'200000'
		);

		await expect(
			page.getByText('Please enter a value less than or equal to 99999.')
		).toBeVisible();
	}
);

testWithIsolatedSite(
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

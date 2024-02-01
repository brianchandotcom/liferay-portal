/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HeadlessDeliveryClient} from '@liferay/headless-delivery-client-js';
import {HeadlessSiteClient} from '@liferay/headless-site-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPages';
import getRandomId from '../../utils/getRandomId';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest,
	pageEditorPagesTest
);

type NonDesktopPanels = Array<{
	name: ConfigurationTab;
	sections: Array<{name: ConfigurationSection; visible: boolean}>;
}>;

const NON_DESKTOP_PANELS: NonDesktopPanels = [
	{
		name: 'General',
		sections: [
			{name: 'Frame', visible: true},
			{name: 'Options', visible: false},
		],
	},
	{
		name: 'Styles',
		sections: [
			{name: 'Background', visible: true},
			{name: 'Borders', visible: true},
			{name: 'Effects', visible: true},
			{name: 'Spacing', visible: true},
			{name: 'Text', visible: true},
		],
	},
	{
		name: 'Advanced',
		sections: [
			{name: 'Hide from Site Search Results', visible: false},
			{name: 'CSS', visible: true},
		],
	},
];

test('shows correct sections on each configuration panel when viewport is not Desktop', async ({
	authenticate,
	page,
	pageEditorPage,
}) => {
	await page.goto('/');

	// Create a site

	const site = await authenticate(HeadlessSiteClient).site.postSite({
		requestBody: {name: getRandomId()},
	});

	// Create a page with a Heading fragment

	const headingId = getRandomId();

	const headingFragment = getFragmentDefinition(
		headingId,
		'BASIC_COMPONENT-heading'
	);

	const layout = await authenticate(
		HeadlessDeliveryClient
	).sitePage.postSiteSitePage({
		requestBody: {
			pageDefinition: getPageDefinition([headingFragment]),
			title: getRandomId(),
		},
		siteId: site.id,
	});

	// Go to edit mode of page

	await pageEditorPage.goToEditMode(site, layout);

	// Switch to Tablet viewport and select the fragment

	await pageEditorPage.switchViewport('Tablet');
	await pageEditorPage.selectFragment(headingId, false);

	// Go to each panel and check correct sections are shown

	for (const {name, sections} of NON_DESKTOP_PANELS) {
		await pageEditorPage.goToConfigurationTab(name);

		for (const {name, visible} of sections) {
			const section = page.locator('.panel-title').getByText(name);

			if (visible) {
				await expect(section).toBeVisible();
			}
			else {
				await expect(section).not.toBeVisible();
			}
		}
	}

	// Delete the site

	await authenticate(HeadlessSiteClient).site.deleteSite({siteId: site.id});
});

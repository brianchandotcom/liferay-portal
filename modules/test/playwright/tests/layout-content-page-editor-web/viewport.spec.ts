/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {pageEditorPagesTest} from './fixtures/pageEditorPagesTest';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	isolatedSiteTest,
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
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	await page.goto(liferayConfig.environment.baseUrl);

	// Create a page with a Heading fragment

	const headingId = getRandomString();

	const headingFragment = getFragmentDefinition(
		headingId,
		'BASIC_COMPONENT-heading'
	);

	const layout = await apiHelpers.headlessDelivery.createSitePage(
		site.id,
		getRandomString(),
		getPageDefinition([headingFragment])
	);

	// Go to edit mode of page

	await pageEditorPage.goToEditMode(layout, site.friendlyUrlPath);

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
});

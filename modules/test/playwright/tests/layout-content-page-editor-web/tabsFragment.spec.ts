/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {checkAccessibility} from '../../utils/checkAccessibility';
import getRandomString from '../../utils/getRandomString';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest()
);

test('Checks that the Tabs fragment works correctly and has the correct semantics in small resolution', async ({
	apiHelpers,
	page,
	site,
}) => {

	// Create page with a Tabs fragment

	const tabsDefinition = getFragmentDefinition({
		fragmentConfig: {
			numberOfTabs: 2,
		},
		fragmentFields: [
			{
				id: 'title1',
				value: {},
			},
			{
				id: 'title2',
				value: {},
			},
		],
		id: getRandomString(),
		key: 'BASIC_COMPONENT-tabs',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([tabsDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Set small resolution and go to view mode

	await page.setViewportSize({height: 600, width: 600});

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	let dropdownButton = page.getByLabel('Current Selection: Tab 1');

	await expect(dropdownButton).toHaveAttribute('aria-activedescendant', '');
	await expect(dropdownButton).toHaveAttribute('aria-expanded', 'false');
	await expect(dropdownButton).toHaveAttribute('aria-haspopup', 'listbox');
	await expect(dropdownButton).toHaveAttribute('role', 'combobox');

	// Open the dropdown and navigate by keyboard to select the Tab 2

	await dropdownButton.press('Enter');

	await expect(dropdownButton).toHaveAttribute('aria-expanded', 'true');

	await page.keyboard.press('Tab');
	await page.keyboard.press('Tab');
	await page.keyboard.press('Enter');

	dropdownButton = page.getByLabel('Current Selection: Tab 2');

	// Check that the button has the correct text and the focus when the tab is selected

	expect((await dropdownButton.textContent()).trim()).toBe('Tab 2');

	await expect(dropdownButton).toBeFocused();

	// Check accessibility

	await checkAccessibility({page, selectors: ['.component-tabs']});
});

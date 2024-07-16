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
import {PageEditorPage} from '../../pages/layout-content-page-editor-web/PageEditorPage';
import getRandomString from '../../utils/getRandomString';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

const ENTER_KEY = 'Enter';

async function openDropdownAndCheckStyle(
	pageEditorPage: PageEditorPage,
	dropdownId: string,
	isDesktop = true,
	style: string,
	value: string
) {

	// Select dropdown

	await pageEditorPage.selectFragment(dropdownId, isDesktop);

	const dropdownFragment = pageEditorPage.getFragment(dropdownId, isDesktop);

	const dropdownButton = dropdownFragment.locator(
		'.dropdown-fragment-toggle'
	);

	// Open dropdown

	await dropdownButton.press(ENTER_KEY);

	const dropdownMenu = dropdownFragment.locator('.dropdown-fragment-menu');

	await dropdownMenu.waitFor();

	// Check style

	expect(
		await dropdownMenu.evaluate((element, style) => {
			return window.getComputedStyle(element).getPropertyValue(style);
		}, style)
	).toBe(value);

	// Close dropdown

	await dropdownButton.press(ENTER_KEY);

	await dropdownMenu.waitFor({state: 'hidden'});
}

test('Check dropdown menu is displayed correctly in all resolutions', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create content page with a Dropdown fragment and go to edit mode

	const dropdownId = getRandomString();

	const fragmentDefinition = getFragmentDefinition({
		cssClasses: ['d-flex', 'justify-content-end'],
		id: dropdownId,
		key: 'BASIC_COMPONENT-dropdown',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([fragmentDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Check dropdown style in all viewports

	await openDropdownAndCheckStyle(
		pageEditorPage,
		dropdownId,
		true,
		'right',
		'280px'
	);

	await pageEditorPage.switchViewport('Portrait Phone');

	await openDropdownAndCheckStyle(
		pageEditorPage,
		dropdownId,
		false,
		'right',
		'0px'
	);

	await pageEditorPage.goToConfigurationTab('Advanced');

	await page.getByRole('button', {name: 'Clear All'}).click();

	await openDropdownAndCheckStyle(
		pageEditorPage,
		dropdownId,
		false,
		'left',
		'0px'
	);
});

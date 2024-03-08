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

test('allows changing and resetting spacing', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	await page.goto('/');

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

	await pageEditorPage.goToEditMode(layout, site.friendlyUrlPath);

	// Change Margin Top with custom value and check change is applied

	await pageEditorPage.changeFragmentSpacing(
		headingId,
		'Margin Top',
		'5',
		'px'
	);
	expect(await pageEditorPage.getFragmentStyle(headingId, 'marginTop')).toBe(
		'5px'
	);

	// Change Margin Top with token value and check change is applied

	await pageEditorPage.changeFragmentSpacing(headingId, 'Margin Top', '2');
	expect(await pageEditorPage.getFragmentStyle(headingId, 'marginTop')).toBe(
		'8px'
	);

	// Reset to initial value and check change is applied

	await pageEditorPage.resetSpacing(headingId, 'Margin Top');
	expect(await pageEditorPage.getFragmentStyle(headingId, 'marginTop')).toBe(
		'0px'
	);
});

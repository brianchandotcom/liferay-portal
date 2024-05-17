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
import getRandomString from '../../utils/getRandomString';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test('allows editing inline text from Page Content Panel', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a page with a Heading fragment

	const headingId = getRandomString();
	const headingDefinition = getFragmentDefinition(
		headingId,
		'BASIC_COMPONENT-heading'
	);

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([headingDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	// Go to edit mode of page

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	// Go to Page Contents panel and edit inline text

	await pageEditorPage.goToSidebarTab('Page Content');

	await page.getByLabel('Edit Text Heading Example').click();

	const editable = pageEditorPage.getEditable(headingId, 'element-text');

	await editable.locator('.cke_editable_inline').waitFor();

	// Clear current content and fill with new one

	await page.keyboard.press('Control+KeyA');
	await page.keyboard.press('Backspace');

	await page.keyboard.type('New Content');
	await page.locator('body').click();

	await pageEditorPage.waitForChangesSaved();

	await expect(
		page.locator('.page-editor__page-contents__page-content')
	).toContainText('New Content');
});

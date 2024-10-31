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

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPD-18221': true,
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test('Saves edited contend when leaving page while editing', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {

	// Create a page with a Paragraph fragment and go to view mode

	const paragraphId = getRandomString();
	const paragraphDefinition = getFragmentDefinition({
		id: paragraphId,
		key: 'BASIC_COMPONENT-paragraph',
	});

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([paragraphDefinition]),
		siteId: site.id,
		title: getRandomString(),
	});

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	// Go to edit mode

	await page
		.getByLabel('Control Menu')
		.getByText('Edit', {exact: true})
		.click();

	await page.locator('.page-editor').waitFor();

	// Write text in editable

	await pageEditorPage.selectFragment(paragraphId);

	await pageEditorPage.selectEditable(paragraphId, 'element-text');

	const editable = pageEditorPage.getEditable({
		editableId: 'element-text',
		fragmentId: paragraphId,
	});

	await editable.click();

	await editable.locator('.cke_editable_inline').waitFor();

	await editable.locator('.cke_editable_inline').click();

	// Clear current content and fill with new one

	await page.keyboard.press('Control+KeyA');
	await page.keyboard.press('Backspace');

	await page.keyboard.type('Papa');

	// Leave page while editing

	await page.getByLabel('Control Menu').locator('.lfr-back-link').click();

	// Go back to edit mode and check value was saved

	await page
		.getByLabel('Control Menu')
		.getByText('Edit', {exact: true})
		.click();

	await expect(page.getByText('Papa')).toBeVisible();
});

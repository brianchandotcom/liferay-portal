/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPages';
import getRandomId from '../../utils/getRandomId';
import getFragmentDefinition from './utils/getFragmentDefinition';
import getPageDefinition from './utils/getPageDefinition';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	loginTest,
	pageEditorPagesTest
);

test('View Undo interaction state is cleared after refreshing the page', async ({
	apiHelpers,
	page,
	pageEditorPage,
}) => {
	await page.goto('/');

	// Create a site

	const site = await apiHelpers.headlessSite.createSite(getRandomId());

	// Create a page with a Heading fragment

	const headingId = getRandomId();

	const headingFragment = getFragmentDefinition(
		headingId,
		'BASIC_COMPONENT-heading'
	);

	const layout = await apiHelpers.headlessDelivery.createSitePage(
		site.id,
		getRandomId(),
		getPageDefinition([headingFragment])
	);

	// Go to edit mode of page

	await pageEditorPage.goToEditMode(site, layout);

	// Assert undo button is disabled

	await expect(pageEditorPage.undoButton).toBeDisabled();

	// Select the fragment

	await pageEditorPage.selectFragment(headingId);

	// Go to Styles panel and set text to Align Center

	await pageEditorPage.goToConfigurationTab('Styles');
	await page.getByLabel('Align Center').click();

	// Assert undo button is enabled

	await expect(pageEditorPage.undoButton).toBeEnabled();

	// Refresh the page

	await page.reload();

	// Assert Undo button is disabled

	await expect(pageEditorPage.undoButton).toBeDisabled();

	// Delete the site

	await apiHelpers.headlessSite.deleteSite(site.id);
});

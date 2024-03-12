/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {pageEditorPagesTest} from './fixtures/pageEditorPagesTest';
import getContainerDefinition from './utils/getContainerDefinition';
import getFragmentDefinition from './utils/getFragmentDefinition';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	loginTest(),
	isolatedSiteTest,
	pageEditorPagesTest
);

test('checks that the corresponding message appears when a parent fragment is hidden from search and the link redirects correctly', async ({
	apiHelpers,
	page,
	pageEditorPage,
	site,
}) => {
	const containerId = getRandomString();
	const headingId = getRandomString();

	// Create a container with a heading fragment inside

	const containerDefinition = getContainerDefinition(containerId, [
		getFragmentDefinition(headingId, 'BASIC_COMPONENT-heading'),
	]);

	await pageEditorPage.createPageWithFragmentAndGoToEditMode({
		apiHelpers,
		fragment: containerDefinition,
		site,
	});

	await page.getByTitle('Browser').click();

	await page.getByLabel('Select Container').click();

	await pageEditorPage.goToConfigurationTab('Advanced');

	const hideFromSiteSearchResultsInput = await page.getByLabel(
		'Hide from Site Search Results'
	);

	await hideFromSiteSearchResultsInput.check();

	await expect(hideFromSiteSearchResultsInput).toBeChecked();

	await pageEditorPage.selectFragment(headingId);

	await pageEditorPage.goToConfigurationTab('Advanced');

	await expect(
		page.getByText('This configuration is inherited')
	).toBeVisible();

	await page.getByText('Go to parent fragment to edit.').click();

	const containerIsActive = await pageEditorPage.isActive(containerId);

	await expect(containerIsActive).toBe(true);
});

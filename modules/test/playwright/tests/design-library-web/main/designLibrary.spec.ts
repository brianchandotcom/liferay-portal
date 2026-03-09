/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {designLibrariesPageTest} from './fixtures/designLibrariesPageTest';

const test = mergeTests(
	designLibrariesPageTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
		'LPD-57283': {enabled: true},
	}),
	loginTest()
);

test('Check if Design Library is working correctly', async ({
	designLibrariesPage,
	page,
}) => {
	await test.step('Can navigate to Design Libraries page', async () => {
		await designLibrariesPage.goto();

		await expect(page.getByTestId('header')).toHaveText('Design Libraries');
	});

	await test.step('Check that the empty state labels are correct', async () => {
		await expect(page.getByText('No Design Libraries Yet')).toBeVisible();

		await expect(
			page.getByText('Click "New" to create your first Design Library.')
		).toBeVisible();

		await expect(
			page.getByRole('button', {name: 'New Design Library'})
		).toBeVisible();
	});

	await test.step('Check that the "/states/design_library_empty_state.svg" image is displayed', async () => {
		await expect(
			designLibrariesPage.emptyStateContainer.locator(
				'img[src$="/states/design_library_empty_state.svg"]'
			)
		).toBeVisible();
	});
});

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {languageOverridePageTest} from '../../../fixtures/languageOverridePageTest';
import {samplePageTest} from './fixtures/samplePageTest';

export const test = mergeTests(
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	languageOverridePageTest,
	samplePageTest
);

test(
	'Override language works in module',
	{tag: '@LPD-55263'},
	async ({languageOverridePage, page, samplePage}) => {
		await test.step('Update an existing language key', async () => {
			await languageOverridePage.goto();

			const searchBar = page.getByRole('searchbox', {
				name: 'Search for:',
			});

			await searchBar.fill('taglib-provided-bar');

			await page.keyboard.press('Enter');

			await languageOverridePage.editLanguageKey('taglib-provided-bar');

			await languageOverridePage.updateTranslation(
				'en-US',
				'bar override'
			);

			await languageOverridePage.saveButton.click();

			await expect(page.getByText('Success:')).toBeVisible();

			await samplePage.goto();

			await page.getByText('Override Language').click();

			await expect(page.getByText('Success')).toBeVisible();

			await expect(page.getByText('bar override')).toBeVisible();

			await expect(page.getByText('foo')).toBeVisible();
		});
	}
);

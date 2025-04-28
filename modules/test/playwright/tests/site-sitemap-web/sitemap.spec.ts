/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {instanceSettingsPagesTest} from '../../fixtures/instanceSettingsPagesTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(instanceSettingsPagesTest, loginTest());

test(
	'XML Sitemap configuration does not cause issues going to different configuration',
	{
		tag: '@LPD-54034',
	},
	async ({instanceSettingsPage, page}) => {
		await instanceSettingsPage.goToInstanceSetting('SEO', 'XML Sitemap');

		await expect(page.getByText('XML Sitemap Index Enabled')).toBeVisible();

		await page.getByRole('menuitem', {name: 'Friendly URL'}).click();

		await expect(
			page.getByText('URL Separator', {exact: true})
		).toBeVisible();

		await expect(
			page.getByRole('menuitem', {name: 'Friendly URL'})
		).toBeVisible();
	}
);

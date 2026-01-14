/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';

const test = mergeTests(isolatedSiteTest, loginTest(), pageViewModePagesTest);

test('Verify portlet-topper options are accessible', async ({page}) => {
	await test.step('When viewing the Look and Feel configuration', async () => {
		const portletTopper = page.locator('.portlet-topper', {
			hasText: 'Menu Display',
		});

		await expect(portletTopper).toBeVisible();

		await portletTopper.getByLabel('Options').click();

		const lookAndFeelConfigurationMenuItem = page.getByRole('menuitem', {
			name: 'Look and Feel Configuration',
		});

		await expect(lookAndFeelConfigurationMenuItem).toBeVisible();

		await lookAndFeelConfigurationMenuItem.click();
	});

	await test.step('Then the look and feel configuration modal is present', async () => {
		const lookAndFeelModal = page.locator('.liferay-modal', {
			hasText: 'Look and Feel Configuration',
		});

		await expect(lookAndFeelModal).toBeVisible();
	});
});

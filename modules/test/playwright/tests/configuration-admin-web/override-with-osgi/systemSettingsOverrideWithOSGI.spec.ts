/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {accessibilityMenuPagesTest} from '../../../fixtures/accessibilityMenuPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';

export const test = mergeTests(
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	systemSettingsPageTest,
	accessibilityMenuPagesTest
);

test('Should not allow access Accessibility Menu by OSGI when disabled by Portal Properties', async ({
	accessibilityMenuPage,
	page,
	systemSettingsPage,
}) => {
	await test.step('Verify Accessibility Menu restriction in System Settings', async () => {
		await systemSettingsPage.goToSystemSetting(
			'Accessibility',
			'Accessibility Menu'
		);

		await expect(
			accessibilityMenuPage.enableAccessibilityMenuCheckbox
		).not.toBeChecked();

		await expect(
			page
				.getByText(
					'Enable the accessibility menu which can be accessed by tabbing focus to the quick access menu. When enabled, users are able to save their accessibility settings in the browser local storage when not signed in and in the database when signed in. This field has been set by a portal property and cannot be changed here.'
				)
				.first()
		).toBeVisible();
	});

	await test.step('Verify that Accessibility Menu is not accessible', async () => {
		await expect(
			accessibilityMenuPage.openAccessibilityMenuButton
		).not.toBeAttached();
	});
});

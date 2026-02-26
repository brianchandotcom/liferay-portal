/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';

const test = mergeTests(
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	systemSettingsPageTest
);

test('AUI modules can be enabled to preload', async ({systemSettingsPage}) => {
	await systemSettingsPage.goToSystemSetting('Third Party', 'AUI');

	const checkbox = systemSettingsPage.page.getByLabel(
		'Preload Widely Used AUI'
	);

	await expect(checkbox).not.toBeChecked();

	await checkbox.check();

	await systemSettingsPage.saveAndWaitForAlert();

	await systemSettingsPage.goToSystemSetting('Third Party', 'AUI');

	await expect(checkbox).toBeChecked();

	await test.step('Clean up', async () => {
		await checkbox.uncheck();

		await systemSettingsPage.saveAndWaitForAlert();
	});
});

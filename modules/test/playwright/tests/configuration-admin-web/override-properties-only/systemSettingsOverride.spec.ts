/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../../fixtures/systemSettingsPageTest';

export const test = mergeTests(
	apiHelpersTest,
	loginTest(),
	systemSettingsPageTest
);

test('Verify global comments restriction in System Settings', async ({
	page,
	systemSettingsPage,
}) => {
	await systemSettingsPage.goToSystemSetting('Web Content', 'Web Content');

	await expect(page.getByLabel('Article Comments Enabled')).not.toBeChecked();

	await expect(
		page
			.getByText(
				'Set this to true to enable comments for journal articles. This field has been set by a portal property and cannot be changed here.'
			)
			.first()
	).toBeVisible();
});

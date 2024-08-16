/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {featureFlagPagesTest} from '../../../feature-flag-web/fixtures/featureFlagPagesTest';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';

export const test = mergeTests(
	dataSetsPageTest,
	featureFlagPagesTest,
	featureFlagsTest({
		'LPS-164563': true,
	}),
	loginTest()
);

test.describe('Data Set Manager with Feature Flag Enabled', () => {
	test('Confirm the description in the FF Data Set @LPS-188590', async ({
		featureFlagsInstanceSettingsPage,
		page,
	}) => {
		await test.step('Navigate to Feature Flag page', async () => {
			await featureFlagsInstanceSettingsPage.goto('Beta');
		});

		await test.step('Check that the feature flag description is displayed', async () => {
			await expect(
				page.getByText(
					'Create tables to show data coming from headless resources. Choose the columns to show as well as how data will be paginated. Use a frontend data set cell renderer client extension to customize how data is rendered.'
				)
			).toBeVisible();
		});
	});
});

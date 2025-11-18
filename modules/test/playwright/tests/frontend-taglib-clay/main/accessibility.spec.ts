/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {checkAccessibility} from '../../../utils/checkAccessibility';
import {claySamplePageTest} from './fixtures/claySamplePageTest';
import {TabName as ClayTabs} from './pages/ClaySamplePage';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	claySamplePageTest,
	loginTest()
);

test('When accessing all clay sample portlet tabs, then verifies that the components are compliant with axe accessibility.', async ({
	claySamplePage,
	page,
}) => {
	const tabNames = Object.values(ClayTabs);
	for (const tabName of tabNames) {
		await test.step(`Verifying ${tabName} accessibility`, async () => {
			await claySamplePage.selectTab(tabName);

			await checkAccessibility({
				page,
			});
		});
	}
});

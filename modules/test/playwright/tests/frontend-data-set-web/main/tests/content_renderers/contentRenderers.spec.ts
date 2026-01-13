/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {EFDSVisualizationMode, waitForFDS} from '../../../../../utils/waitFor';
import {fdsSamplePageTest} from '../../fixtures/fdsSamplePageTest';

const test = mergeTests(
	apiHelpersTest,
	fdsSamplePageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(async ({fdsSamplePage, page, site}) => {
	await fdsSamplePage.setupFDSSampleWidget({site});

	await fdsSamplePage.selectTab('Content Renderers');

	await waitForFDS({page, visualizationMode: EFDSVisualizationMode.TABLE});
});

test('Number fields', {tag: ['@LPD-64534']}, async ({fdsSamplePage}) => {
	await test.step('Check that the actionLink content renderer displays 0', async () => {
		const numberActionLinkCell = fdsSamplePage.table.bodyRows
			.nth(0)
			.locator('.cell-numberActionLink');

		await expect(numberActionLinkCell).toHaveText('0');
	});

	await test.step('Check that the default content renderer displays 0', async () => {
		const numberDefaultCell = fdsSamplePage.table.bodyRows
			.nth(0)
			.locator('.cell-numberDefault');

		await expect(numberDefaultCell).toHaveText('0');
	});
});

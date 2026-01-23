/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {checkAccessibility} from '../../../../../utils/checkAccessibility';
import {EFDSVisualizationMode, waitForFDS} from '../../../../../utils/waitFor';
import {fdsSamplePageTest} from '../../fixtures/fdsSamplePageTest';

const test = mergeTests(
	fdsSamplePageTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(async ({fdsSamplePage, site}) => {
	await fdsSamplePage.setupFDSSampleWidget({site});

	await fdsSamplePage.selectTab('React');
});

test('Check accessibility of FDS visualization modes', async ({
	fdsSamplePage,
	page,
}) => {
	await waitForFDS({page, visualizationMode: EFDSVisualizationMode.TABLE});

	await checkAccessibility({
		bestPractices: true,
		page,
		selectors: ['.data-set-wrapper'],
	});

	await test.step('Change visualization mode to List', async () => {
		await fdsSamplePage.changeVisualizationMode({
			page,
			visualizationMode: EFDSVisualizationMode.LIST,
		});
	});

	await checkAccessibility({
		bestPractices: true,
		page,
		selectors: ['.data-set-wrapper'],
	});

	await test.step('Change visualization mode to Cards', async () => {
		await fdsSamplePage.changeVisualizationMode({
			page,
			visualizationMode: EFDSVisualizationMode.CARDS,
		});
	});

	await checkAccessibility({
		bestPractices: true,
		page,
		selectors: ['.data-set-wrapper'],
	});
});

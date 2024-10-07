/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {ckeditorSamplePageTest} from './fixtures/ckeditorSamplePageTest';

export const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	ckeditorSamplePageTest
);

test.beforeEach(async ({ckeditorSamplePage, page, site}) => {
	await ckeditorSamplePage.createAndGotoSitePage({site});

	const reactTab = page.getByRole('tab', {
		name: 'React',
	});

	await reactTab.click();

	expect(
		page.getByText('Classic Editor used from a React component')
	).toBeVisible();
});

test(
	'Editor configuration object must not be mutated',
	{tag: '@LPD-37782'},
	async ({page}) => {
		const iframe = page.frameLocator(
			'iframe[title$="sampleReactClassicEditor"]'
		);

		expect(
			iframe.getByText('Editor configuration object was not mutated.')
		).toBeVisible();
	}
);

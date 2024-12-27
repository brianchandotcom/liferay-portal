/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {samplePageTest} from '../../fixtures/samplePageTest';

export const test = mergeTests(
	isolatedSiteTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	loginTest(),
	samplePageTest
);

const linkName = 'Fieldset';

test(
	'Tooltip should be translated correctly',
	{
		tag: '@LPD-43309',
	},
	async ({page, samplePage, site}) => {
		await test.step('Add taglib sample to page', async () => {
			await samplePage.setupSampleWidget({
				site,
			});

			await samplePage.selectLink(linkName);
		});

		await test.step('Check tooltip is translated', async () => {
			const svgElement = page
				.locator('svg[aria-label="Help Text"]')
				.first();

			await expect(svgElement).toBeHidden();
		});
	}
);

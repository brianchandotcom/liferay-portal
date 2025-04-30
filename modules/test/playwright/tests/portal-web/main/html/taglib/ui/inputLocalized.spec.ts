/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../../fixtures/loginTest';
import {samplePageTest} from '../../../../../frontend-taglib/main/fixtures/samplePageTest';

export const test = mergeTests(
	isolatedSiteTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	loginTest(),
	samplePageTest
);

const linkName = 'Input Localized';

test(
	'Input localized id and label match',
	{
		tag: '@LPD-42768',
	},
	async ({page, samplePage, site}) => {
		await test.step('Add taglib sample to page', async () => {
			await samplePage.setupSampleWidget({
				site,
			});

			await samplePage.selectLink(linkName);
		});

		await test.step('Check id and label match', async () => {
			const labelFor = await page
				.getByText('input-localized-label')
				.getAttribute('for');

			const inputId = await page
				.locator(
					'input[id^="_com_liferay_frontend_taglib_sample_web_portlet_SamplePortlet_INSTANCE_"]'
				)
				.first()
				.getAttribute('id');

			expect(labelFor).toBe(inputId);
		});
	}
);

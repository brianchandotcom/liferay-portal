/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import {connectToAnalyticsCloudWithNoSiteSynced} from '../analytics-settings-web/utils/analytics-settings';
import {faroConfig} from '../osb-faro-web/faro.config';
import {openABTesSidebar} from '../segment-experiment-web/utils/ab-test';

export const test = mergeTests(loginAnalyticsCloudTest(), loginTest());

test(
	'Sync button on empty state should redirect the user to Analytics Cloud when site is not synced',
	{
		tag: '@LPD-34179',
	},
	async ({page}) => {
		await connectToAnalyticsCloudWithNoSiteSynced(page);

		await page.goto(liferayConfig.environment.baseUrl);

		await openABTesSidebar(page);

		expect(
			await page.getByText('Sync to Liferay Analytics Cloud')
		).toBeVisible();

		const tagA = await page.locator(
			'#_com_liferay_segments_experiment_web_internal_portlet_SegmentsExperimentPortlet_-segments-experiment-root a'
		);

		const href = (await tagA.getAttribute('href')) || '';

		await page.goto(
			href.replace(
				'http://localhost:8080',
				faroConfig.environment.baseUrl
			)
		);

		const title = await page.locator('h1.title');

		expect(await title.textContent()).toBe('Sites');
	}
);

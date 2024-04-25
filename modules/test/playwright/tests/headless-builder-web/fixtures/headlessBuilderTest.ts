/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {FeatureFlagsOptions, featureFlagsTest} from '../../../fixtures/featureFlagsTest';

export const headlessBuilderTest = (featureFlags?: FeatureFlagsOptions) => mergeTests(
	featureFlagsTest({
		...featureFlags,
		'LPS-178642': true,
	}),
	apiHelpersTest.extend<{
		headlessBuilder: void;
	}>({
		headlessBuilder: [
			async ({apiHelpers, page}, use) => {
				await page.goto('/');
				for (const endpoint of [
					'applications',
					'endpoints',
					'filters',
					'properties',
					'schemas',
					'sorts',
				]) {
					await expect
						.poll(async () =>
							(
								await page.request.get(
									`/o/headless-builder/${endpoint}`,
									{
										headers: await apiHelpers.getHeader(),
									}
								)
							).status()
						)
						.toBe(200);
				}
				await use();
			},
			{auto: true},
		],
	})
);

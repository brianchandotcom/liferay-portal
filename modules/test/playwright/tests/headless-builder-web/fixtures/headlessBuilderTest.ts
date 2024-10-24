/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests, test} from '@playwright/test';

import {
	FeatureFlagsOptions,
	featureFlagsTest,
} from '../../../fixtures/featureFlagsTest';
import {getHeader} from '../../../helpers/ApiHelpers';

function headlessBuilderTest(featureFlags?: FeatureFlagsOptions) {
	return mergeTests(
		featureFlagsTest({
			...featureFlags,
			'LPS-178642': true,
		}),
		test.extend<{
			headlessBuilder: void;
		}>({
			headlessBuilder: [
				async ({page}, use) => {
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
											headers: await getHeader(page),
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
}

export {headlessBuilderTest};

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';

export const availableDataSetTest = mergeTests(
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPS-164563': true,
	}),
	loginTest()
);

availableDataSetTest.describe('Data Sets Application', () => {
	availableDataSetTest(
		'Data Sets page exists if FF LPS-164563 is enabled',
		async ({applicationsMenuPage, page}) => {
			await availableDataSetTest.step(
				'Navigate to Data Sets',
				async () => {
					await applicationsMenuPage.goToDataSetManager();
				}
			);

			await availableDataSetTest.step(
				'Data Sets page is empty',
				async () => {
					await page.getByText('No Data Sets Created').waitFor();

					await expect(
						await page.getByText('No Data Sets Created')
					).toBeVisible();
				}
			);
		}
	);
});

export const unavailableDataSetTest = mergeTests(
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPS-164563': false,
	}),
	loginTest()
);

unavailableDataSetTest.describe('Data Sets Application', () => {
	unavailableDataSetTest(
		'Data Sets page does not exist if FF LPS-164563 is disabled',
		async ({applicationsMenuPage, page}) => {
			await unavailableDataSetTest.step(
				'Open Control Panel',
				async () => {
					await applicationsMenuPage.goto();
				}
			);

			await unavailableDataSetTest.step(
				'Check that Data Sets menu item does not exist',
				async () => {
					await expect(
						page.getByRole('menuitem', {
							exact: true,
							name: 'Data Sets',
						})
					).not.toBeInViewport();
				}
			);
		}
	);
});

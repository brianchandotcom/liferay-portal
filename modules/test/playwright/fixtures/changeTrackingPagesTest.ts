/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests, test} from '@playwright/test';

import {ApiHelpers} from '../helpers/ApiHelpers';
import {ChangeTrackingInstanceSettingsPage} from '../pages/change-tracking-web/ChangeTrackingInstanceSettingsPage';
import {ChangeTrackingPage} from '../pages/change-tracking-web/ChangeTrackingPage';
import getRandomString from '../utils/getRandomString';
import {loginTest} from './loginTest';

const changeTrackingPages = test.extend<{
	ChangeTrackingInstanceSettingsPage: ChangeTrackingInstanceSettingsPage;
	changeTrackingPage: ChangeTrackingPage;
	ctCollection;
}>({
	ChangeTrackingInstanceSettingsPage: async ({page}, use) => {
		await use(new ChangeTrackingInstanceSettingsPage(page));
	},
	changeTrackingPage: async ({page}, use) => {
		await use(new ChangeTrackingPage(page));
	},
	ctCollection: [
		async ({page}, use) => {
			await page.goto('/');

			const apiHelpers = new ApiHelpers(page);

			let ctCollection;

			try {

				// Create ctCollection

				ctCollection =
					await apiHelpers.headlessChangeTracking.createCTCollection(
						getRandomString()
					);

				// Checkout ctCollection

				await apiHelpers.headlessChangeTracking.checkoutCTCollection(
					ctCollection.id
				);

				await use(ctCollection);
			}
			catch {
				throw new Error(`Could not checkout ctCollection`);
			}
			finally {

				// Delete ctCollection

				await apiHelpers.headlessChangeTracking.deleteCTCollection(
					ctCollection.id
				);
			}
		},
		{auto: true},
	],
});

const changeTrackingPagesTest = mergeTests(loginTest(), changeTrackingPages);

export {changeTrackingPagesTest};

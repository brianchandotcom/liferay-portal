/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {dataSetManagerSetupTest} from '../../data-set-admin/fixtures/dataSetManagerSetupTest';
import {FDSFragmentPage} from '../pages/FDSFragmentPage';

// ensures data-set-fragment tests can run before data-set-admin tests

const test = mergeTests(dataSetManagerSetupTest);

const fdsFragmentPageTest = test.extend<{
	fdsFragmentPage: FDSFragmentPage;
}>({
	fdsFragmentPage: async ({page}, use) => {
		await use(new FDSFragmentPage(page));
	},
});

export {fdsFragmentPageTest};

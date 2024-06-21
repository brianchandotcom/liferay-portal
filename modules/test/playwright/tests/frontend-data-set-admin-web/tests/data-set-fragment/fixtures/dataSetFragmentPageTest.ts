/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {dataSetManagerSetupTest} from '../../data-set-admin/fixtures/dataSetManagerSetupTest';
import {DataSetFragmentPage} from '../pages/DataSetFragmentPage';

// ensures data-set-fragment tests can run before data-set-admin tests

const test = mergeTests(dataSetManagerSetupTest);

const dataSetFragmentPageTest = test.extend<{
	dataSetFragmentPage: DataSetFragmentPage;
}>({
	dataSetFragmentPage: async ({page}, use) => {
		await use(new DataSetFragmentPage(page));
	},
});

export {dataSetFragmentPageTest};

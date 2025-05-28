/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {KaleoFormModalPage} from '../pages/portal-workflow-kaleo-forms-web/KaleoFormModalPage';
import {KaleoFormsAdminPage} from '../pages/portal-workflow-kaleo-forms-web/KaleoFormsAdminPage';

const kaleoFormsPagesTest = test.extend<{
	kaleoFormModalPage: KaleoFormModalPage;
	kaleoFormsAdminPage: KaleoFormsAdminPage;
}>({
	kaleoFormModalPage: async ({page}, use) => {
		await use(new KaleoFormModalPage(page));
	},
	kaleoFormsAdminPage: async ({page}, use) => {
		await use(new KaleoFormsAdminPage(page));
	},
});

export {kaleoFormsPagesTest};

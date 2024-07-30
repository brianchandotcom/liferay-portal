/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {AddCustomFieldPage} from '../pages/expando-web/AddCustomFieldPage';
import {CustomFieldsPage} from '../pages/expando-web/CustomFieldsPage';
import {ViewAttributesPage} from '../pages/expando-web/ViewAttributesPage';

const customFieldsPagesTest = test.extend<{
	addCustomFieldPage: AddCustomFieldPage;
	customFieldsPage: CustomFieldsPage;
	viewAttributesPage: ViewAttributesPage;
}>({
	addCustomFieldPage: async ({page}, use) => {
		await use(new AddCustomFieldPage(page));
	},
	customFieldsPage: async ({page}, use) => {
		await use(new CustomFieldsPage(page));
	},
	viewAttributesPage: async ({page}, use) => {
		await use(new ViewAttributesPage(page));
	},
});

export {customFieldsPagesTest};

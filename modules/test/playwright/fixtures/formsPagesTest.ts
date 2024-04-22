/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {FormBuilderPage} from '../pages/dynamic-data-mapping-form-web/FormBuilderPage';
import {FormBuilderSidePanelPage} from '../pages/dynamic-data-mapping-form-web/FormBuilderSidePanelPage';
import {FormsPage} from '../pages/dynamic-data-mapping-form-web/FormsPage';

const formsPagesTest = test.extend<{
	formBuilderPage: FormBuilderPage;
	formBuilderSidePanelPage: FormBuilderSidePanelPage;
	formsPage: FormsPage;
}>({
	formBuilderPage: async ({page}, use) => {
		await use(new FormBuilderPage(page));
	},
	formBuilderSidePanelPage: async ({page}, use) => {
		await use(new FormBuilderSidePanelPage(page));
	},
	formsPage: async ({page}, use) => {
		await use(new FormsPage(page));
	},
});

export {formsPagesTest};

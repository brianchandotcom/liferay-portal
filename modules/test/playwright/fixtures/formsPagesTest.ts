/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {FormBuilderPage} from '../pages/dynamic-data-mapping-form-web/FormBuilderPage';
import {FormBuilderSidePanelPage} from '../pages/dynamic-data-mapping-form-web/FormBuilderSidePanelPage';
import {FormSettingsModalPage} from '../pages/dynamic-data-mapping-form-web/FormSettingsModalPage';
import {FormWidgetPage} from '../pages/dynamic-data-mapping-form-web/FormWidgetPage';
import {FormsPage} from '../pages/dynamic-data-mapping-form-web/FormsPage';
import {PageEditorPage} from '../pages/layout-content-page-editor-web/PageEditorPage';
import {ConfigurationTabPage} from '../pages/portal-workflow-kaleo-designer-web/ConfigurationTabPage';

const formsPagesTest = test.extend<{
	configurationTabPage: ConfigurationTabPage;
	formBuilderPage: FormBuilderPage;
	formBuilderSidePanelPage: FormBuilderSidePanelPage;
	formSettingsModalPage: FormSettingsModalPage;
	formWidgetPage: FormWidgetPage;
	formsPage: FormsPage;
	pageEditorPage: PageEditorPage;
}>({
	configurationTabPage: async ({page}, use) => {
		await use(new ConfigurationTabPage(page));
	},
	formBuilderPage: async ({page}, use) => {
		await use(new FormBuilderPage(page));
	},
	formBuilderSidePanelPage: async ({page}, use) => {
		await use(new FormBuilderSidePanelPage(page));
	},
	formSettingsModalPage: async ({page}, use) => {
		await use(new FormSettingsModalPage(page));
	},
	formWidgetPage: async ({page}, use) => {
		await use(new FormWidgetPage(page));
	},
	formsPage: async ({page}, use) => {
		await use(new FormsPage(page));
	},
	pageEditorPage: async ({page}, use) => {
		await use(new PageEditorPage(page));
	},
});

export {formsPagesTest};

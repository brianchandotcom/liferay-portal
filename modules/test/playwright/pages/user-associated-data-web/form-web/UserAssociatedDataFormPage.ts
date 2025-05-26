/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

import {ApiHelpers, DataApiHelpers} from '../../../helpers/ApiHelpers';
import getPageDefinition from '../../../tests/layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../../tests/layout-content-page-editor-web/main/utils/getWidgetDefinition';
import getRandomString from '../../../utils/getRandomString';
import {FormWidgetPage} from '../../dynamic-data-mapping-form-web/FormWidgetPage';
import {PageEditorPage} from '../../layout-content-page-editor-web/PageEditorPage';

export class UserAssociatedDataFormPage {
	readonly formWidgetSubmitButton: Locator;
	readonly formWidgetTextFieldLabel: (labelName: string) => Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.formWidgetSubmitButton = page.getByRole('button', {
			name: 'Submit',
		});
		this.formWidgetTextFieldLabel = (labelName: string) =>
			page.getByLabel(labelName);
		this.page = page;
	}

	async createFormPage(
		apiHelpers: ApiHelpers | DataApiHelpers,
		formTitle: string,
		site: Site,
		options?: {title?: string}
	) {
		options = {
			title: getRandomString(),
			...(options || {}),
		};

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName:
						'com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormPortlet',
				}),
			]),
			siteId: String(site.id),
			title: options.title,
		});

		const formWidgetPage = new FormWidgetPage(this.page);
		const pageEditorPage = new PageEditorPage(this.page);

		await pageEditorPage.goto(layout, site.friendlyUrlPath);

		await formWidgetPage.setFormWidgetConfiguration(formTitle);
		await pageEditorPage.publishPage();

		return layout;
	}
}

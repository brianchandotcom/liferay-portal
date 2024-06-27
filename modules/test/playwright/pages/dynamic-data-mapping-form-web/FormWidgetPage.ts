/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class FormWidgetPage {
	readonly configurationDropdownButton: Locator;
	readonly dropdownButton: Locator;
	readonly formLabelLink: Locator;
	readonly formWidgetContainer: Locator;
	readonly nextButton: Locator;
	readonly page: Page;
	readonly previousButton: Locator;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.configurationDropdownButton = page.getByRole('menuitem', {
			name: 'Configuration',
		});
		this.dropdownButton = page.locator(
			'[id^="portlet-topper-toolbar_com_liferay_dynamic_data_mapping_form_web_portlet_DDMFormPortlet_INSTANCE_"]'
		);
		this.formLabelLink = page.locator(
			'tbody.table-data tr td a:has-text("Form A")'
		);
		this.formWidgetContainer = page.locator(
			'.lfr-layout-structure-item-com-liferay-dynamic-data-mapping-form-web-portlet-ddmformportlet'
		);
		this.nextButton = page.getByRole('button', {
			exact: true,
			name: 'Next',
		});
		this.previousButton = page.getByRole('button', {
			exact: true,
			name: 'Previous',
		});
		this.page = page;
		this.saveButton = page.frameLocator('#modalIframe').getByText('Save');
	}

	getFormLink(formName: string) {
		return this.page
			.frameLocator('#modalIframe')
			.getByRole('link', {name: formName});
	}

	async setFormWidgetConfiguration(formName: string) {
		await this.formWidgetContainer.click();
		await this.dropdownButton.hover();
		await this.dropdownButton.click();

		await this.configurationDropdownButton.first().click();

		const formLink = this.getFormLink(formName);

		await formLink.click();

		await this.saveButton.click();

		await this.page.keyboard.press('Escape');
	}
}

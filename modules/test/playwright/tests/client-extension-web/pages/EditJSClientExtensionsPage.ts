/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeHidden} from '../../../utils/clickAndExpectToBeHidden';
import {EditClientExtensionsPage} from './EditClientExtensionsPage';

export class EditJSClientExtensionsPage extends EditClientExtensionsPage {
	currentAttributeIndex = 0;

	readonly portletName =
		'_com_liferay_client_extension_web_internal_portlet_ClientExtensionAdminPortlet';
	readonly javaScriptURLInput: Locator;

	constructor(page: Page) {
		super(page, 'globalJS');

		this.javaScriptURLInput = page.getByRole('textbox', {
			name: 'JavaScript URL',
		});
	}

	async addScriptAttribute(name: string, type: string, value: string) {
		if (this.currentAttributeIndex !== 0) {
			const addNewAttributeButton = this.page
				.getByLabel('Add New Attribute')
				.last();

			await addNewAttributeButton.click();
		}

		await this.page
			.locator(`#${this.portletName}_name_${this.currentAttributeIndex}`)
			.fill(name);

		await this.page
			.locator(`#${this.portletName}_type_${this.currentAttributeIndex}`)
			.click();

		const option = this.page.getByRole('option', {name: type});

		await clickAndExpectToBeHidden({target: option, trigger: option});

		const attributeValueField = this.page.locator(
			`#${this.portletName}_value_${this.currentAttributeIndex}`
		);

		if (type === 'boolean') {
			await attributeValueField.click();

			await this.page.getByRole('option', {name: value}).click();
		}
		else {
			await attributeValueField.fill(value);
		}

		this.currentAttributeIndex++;
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class FormBuilderSidePanelPage {
	readonly advancedTab: Locator;
	readonly backButton: Locator;
	readonly htmlAutocompleteAttributeField: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.advancedTab = page.getByRole('tab', {
			name: 'Advanced',
		});
		this.backButton = page.getByRole('button', {name: 'Back'});
		this.htmlAutocompleteAttributeField = page.getByLabel(
			'HTML Autocomplete Attribute'
		);
		this.page = page;
	}

	async addFieldByDoubleClick(formFieldTypeTitle: FormFieldTypeTitle) {
		await this.page
			.getByTitle(formFieldTypeTitle, {exact: true})
			.dblclick();
	}

	async clickAdvancedTab() {
		await this.advancedTab.click();
	}

	async clickBackButton() {
		await this.backButton.click();
	}
}

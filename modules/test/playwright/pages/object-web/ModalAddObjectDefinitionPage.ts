/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class ModalAddObjectDefinitionPage {
	readonly objectDefinitionSaveButton: Locator;
	readonly objectLabelInput: Locator;
	readonly objectPluralLabelInput: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.objectDefinitionSaveButton = page.getByText('Save', {exact: true});
		this.objectLabelInput = page.locator('input[name="label"]');
		this.objectPluralLabelInput = page.locator('input[name="pluralLabel"]');
		this.page = page;
	}

	async createObjectDefinition(objectDefinitionLabel: string) {
		await this.objectLabelInput.click();
		await this.objectLabelInput.fill(objectDefinitionLabel);
		await this.objectPluralLabelInput.click();
		await this.objectPluralLabelInput.fill(objectDefinitionLabel);

		const responsePromise = this.page.waitForResponse(
			'**/object-definitions'
		);

		await this.objectDefinitionSaveButton.click();
		const response = await responsePromise;

		return response.json();
	}
}

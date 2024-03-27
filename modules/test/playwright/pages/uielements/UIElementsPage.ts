/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class UIElementsPage {
	readonly addButton: Locator;
	readonly alertMessage: Locator;
	readonly anySuccessAlert: Locator;
	readonly closeClickable: Locator;
	readonly newButton: Locator;
	readonly pageCreatedAlert: Locator;
	readonly pageUpdatedAlert: Locator;
	readonly publishButton: Locator;
	readonly saveButton: Locator;
	readonly setupUpdatedAlert: Locator;

	constructor(page: Page) {
		this.newButton = page.getByRole('button', {name: 'New'});
		this.addButton = page.getByRole('button', {name: 'Add'});
		this.alertMessage = page.locator('div.content >> div.alert-info');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.pageCreatedAlert = page.getByText(
			'Success:The page was created successfully.'
		);
		this.pageUpdatedAlert = page.getByText(
			'Success:The page was updated successfully.'
		);
		this.setupUpdatedAlert = page.getByText(
			'Success:You have successfully updated the setup.'
		);
		this.anySuccessAlert = page.getByText('successfully.');
		this.closeClickable = page.getByLabel('close', {exact: true});
		this.publishButton = page.getByLabel('Publish', {exact: true});
	}

	async clickNewButton() {
		await this.newButton.waitFor({state: 'visible'});
		await this.newButton.click();
	}
}

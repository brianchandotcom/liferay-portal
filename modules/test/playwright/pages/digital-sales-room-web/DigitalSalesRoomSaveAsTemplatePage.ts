/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {getRandomInt} from '../../utils/getRandomInt';

export class DigitalSalesRoomSaveAsTemplatePage {
	readonly descriptionInput: Locator;
	readonly page: Page;
	readonly roomNameInput: Locator;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.descriptionInput = page.getByTestId('descriptionInput');
		this.page = page;
		this.roomNameInput = page.getByTestId('roomNameInput');
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}

	async saveAsTemplate({
		description = '',
		roomName = `A${getRandomInt()}`,
	}: {
		description?: string;
		roomName?: string;
	}) {
		await expect(this.roomNameInput).toBeEnabled();

		await this.descriptionInput.fill(description);
		await this.roomNameInput.fill(roomName);

		await this.saveButton.click();

		await expect(
			this.page.getByRole('heading', {name: 'Onboarding'})
		).toBeVisible();
	}
}

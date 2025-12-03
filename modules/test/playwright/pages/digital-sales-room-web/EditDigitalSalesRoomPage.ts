/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';

export class EditDigitalSalesRoomPage {
	readonly bannerImage: Locator;
	readonly clientLogoButton: Locator;
	readonly clientNameInput: Locator;
	readonly page: Page;
	readonly primaryColorInput: Locator;
	readonly roomNameInput: Locator;
	readonly saveButton: Locator;
	readonly secondaryColorInput: Locator;

	constructor(page: Page) {
		this.bannerImage = page.getByTestId('bannerImage');
		this.clientLogoButton = page.getByTestId('clientLogoButton');
		this.clientNameInput = page.getByLabel('Client Name');
		this.page = page;
		this.primaryColorInput = page.getByTestId('primaryColorInput');
		this.roomNameInput = page.getByLabel('Room Name');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.secondaryColorInput = page.getByTestId('secondaryColorInput');
	}

	async addDigitalSalesRoom({
		banner,
		clientLogo,
		clientName = getRandomString(),
		primaryColor = '#FF0000',
		roomName = `A${getRandomInt()}`,
		secondaryColor = '#00FF00',
	}: {
		banner?: string;
		clientLogo?: string;
		clientName?: string;
		primaryColor?: string;
		roomName?: string;
		secondaryColor?: string;
	}) {
		await expect(this.clientNameInput).toBeEnabled();

		await this.clientNameInput.fill(clientName);
		await this.primaryColorInput.fill(primaryColor);
		await this.roomNameInput.fill(roomName);
		await this.secondaryColorInput.fill(secondaryColor);

		if (banner || clientLogo) {
			const fileChooserPromise = this.page.waitForEvent('filechooser');

			if (banner) {
				await this.bannerImage.click();

				const fileChooser = await fileChooserPromise;

				await fileChooser.setFiles(banner);
			}

			if (clientLogo) {
				await this.clientLogoButton.click();

				const fileChooser = await fileChooserPromise;

				await fileChooser.setFiles(clientLogo);
			}
		}

		await this.saveButton.click();

		await expect(
			this.page.getByRole('heading', {name: 'Onboarding'})
		).toBeVisible();
	}
}

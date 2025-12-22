/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import getRandomString from '../../utils/getRandomString';

export class EditDigitalSalesRoomTemplatePage {
	readonly bannerImage: Locator;
	readonly clientLogoButton: Locator;
	readonly descriptionInput: Locator;
	readonly nameInput: Locator;
	readonly nextButton: Locator;
	readonly page: Page;
	readonly primaryColorInput: Locator;
	readonly saveButton: Locator;
	readonly secondaryColorInput: Locator;

	constructor(page: Page) {
		this.bannerImage = page.getByTestId('bannerImage');
		this.clientLogoButton = page.getByTestId('clientLogoButton');
		this.descriptionInput = page.getByLabel('Description');
		this.nameInput = page.getByLabel('Name');
		this.nextButton = page.getByRole('button', {name: 'Next'});
		this.page = page;
		this.primaryColorInput = page.getByTestId('primaryColorInput');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.secondaryColorInput = page.getByTestId('secondaryColorInput');
	}

	async addDigitalSalesRoomTemplate({
		banner,
		clientLogo,
		description,
		name = getRandomString(),
		primaryColor = '#FF0000',
		secondaryColor = '#00FF00',
	}: {
		banner?: string;
		clientLogo?: string;
		description?: string;
		name?: string;
		primaryColor?: string;
		secondaryColor?: string;
	}) {
		await expect(this.nameInput).toBeEnabled();

		await this.nameInput.fill(name);
		if (description) {
			await this.descriptionInput.fill(description);
		}

		await this.nextButton.click();

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

		await this.primaryColorInput.fill(primaryColor);
		await this.secondaryColorInput.fill(secondaryColor);
		await this.saveButton.click();

		await expect(
			this.page.getByRole('heading', {name: 'Onboarding'})
		).toBeVisible();
	}
}

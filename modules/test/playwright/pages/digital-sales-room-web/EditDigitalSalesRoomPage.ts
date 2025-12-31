/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';

export class EditDigitalSalesRoomPage {
	readonly bannerImage: Locator;
	readonly cancelButton: Locator;
	readonly clientLogoButton: Locator;
	readonly clientNameInput: Locator;
	readonly nextButton: Locator;
	readonly page: Page;
	readonly primaryColorInput: Locator;
	readonly roomNameInput: Locator;
	readonly saveButton: Locator;
	readonly secondaryColorInput: Locator;
	readonly selectAccountInput: Locator;
	readonly selectChannelInput: Locator;
	readonly selectOption: (value: string) => Locator;
	readonly templatePreviewFrame: FrameLocator;
	readonly usersEmailAddressesGridCell: (email: string) => Locator;
	readonly usersEmailAddressesInput: Locator;

	constructor(page: Page) {
		this.bannerImage = page.getByTestId('bannerImage');
		this.cancelButton = page.getByRole('button', {
			exact: true,
			name: 'Cancel',
		});
		this.clientLogoButton = page.getByTestId('clientLogoButton');
		this.clientNameInput = page.getByLabel('Client Name');
		this.nextButton = page.getByRole('button', {name: 'Next'});
		this.page = page;
		this.primaryColorInput = page.getByTestId('primaryColorInput');
		this.roomNameInput = page.getByLabel('Room Name');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.secondaryColorInput = page.getByTestId('secondaryColorInput');
		this.selectAccountInput = page.getByRole('combobox', {
			name: 'Select Account',
		});
		this.selectChannelInput = page.getByRole('combobox', {
			name: 'Select Channel',
		});
		this.selectOption = (value: string) =>
			page.getByRole('option', {name: value});
		this.templatePreviewFrame = page
			.getByLabel('Create New Digital Sales Room')
			.frameLocator('iframe');
		this.usersEmailAddressesGridCell = (email: string) =>
			page.getByRole('gridcell', {exact: true, name: email});
		this.usersEmailAddressesInput = page.getByTestId('emailAddressesInput');
	}

	async addDigitalSalesRoom({
		accountName,
		banner,
		channelName,
		clientLogo,
		clientName = getRandomString(),
		primaryColor = '#FF0000',
		roomName = `A${getRandomInt()}`,
		secondaryColor = '#00FF00',
		usersEmailAddresses,
	}: {
		accountName?: string;
		banner?: string;
		channelName?: string;
		clientLogo?: string;
		clientName?: string;
		primaryColor?: string;
		roomName?: string;
		secondaryColor?: string;
		usersEmailAddresses?: Array<string>;
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

		await this.nextButton.click();

		if (accountName) {
			await this.selectAccountInput.click();
			await this.selectOption(accountName).click();

			await expect(this.selectAccountInput).toHaveValue(accountName);
		}

		if (channelName) {
			await this.selectChannelInput.click();
			await this.selectOption(channelName).click();

			await expect(this.selectChannelInput).toHaveValue(channelName);
		}

		await this.nextButton.click();

		if (usersEmailAddresses) {
			for (const email of usersEmailAddresses) {
				await this.usersEmailAddressesInput.fill(email);
				await this.usersEmailAddressesInput.press('Enter');

				await expect(
					this.usersEmailAddressesGridCell(email)
				).toBeVisible();
			}
		}

		await this.saveButton.click();

		await expect(
			this.page.getByRole('heading', {name: 'Onboarding'})
		).toBeVisible();
	}
}

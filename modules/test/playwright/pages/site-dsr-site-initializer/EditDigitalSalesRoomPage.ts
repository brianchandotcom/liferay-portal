/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page, expect} from '@playwright/test';

import {getRandomInt} from '../../utils/getRandomInt';

export class EditDigitalSalesRoomPage {
	readonly cancelButton: Locator;
	readonly friendlyURLInput: Locator;
	readonly nextButton: Locator;
	readonly onboardingMenuItem: Locator;
	readonly page: Page;
	readonly roomNameInput: Locator;
	readonly saveButton: Locator;
	readonly selectAccountInput: Locator;
	readonly selectOption: (value: string) => Locator;
	readonly templatePreviewFrame: FrameLocator;

	constructor(page: Page) {
		this.cancelButton = page.getByRole('button', {
			exact: true,
			name: 'Cancel',
		});
		this.friendlyURLInput = page.getByLabel('Friendly URL');
		this.nextButton = page.getByRole('button', {name: 'Next'});
		this.onboardingMenuItem = page.getByRole('menuitem', {
			name: 'Onboarding',
		});
		this.page = page;
		this.roomNameInput = page.getByLabel('Room Name');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.selectAccountInput = page.getByRole('combobox', {
			name: 'Select Account',
		});
		this.selectOption = (value: string) =>
			page.getByRole('option', {name: value});
		this.templatePreviewFrame = page
			.getByLabel('Create New Digital Sales Room')
			.frameLocator('iframe');
	}

	async addDigitalSalesRoom({
		accountName,
		friendlyURL = '',
		roomName = `A${getRandomInt()}`,
		templateName = 'DSR',
	}: {
		accountName?: string;
		friendlyURL?: string;
		roomName?: string;
		templateName?: string;
	}) {
		await expect(this.selectAccountInput).toBeEnabled();

		await this.selectAccountInput.click();
		await this.selectOption(accountName).click();

		await expect(this.selectAccountInput).toHaveValue(accountName);

		await this.nextButton.click();

		await this.page.getByText(templateName, {exact: true}).click();

		await expect(
			this.templatePreviewFrame.getByRole('heading', {name: 'WELCOME'})
		).toBeVisible();

		await this.nextButton.click();

		await this.roomNameInput.fill(roomName);
		await this.friendlyURLInput.fill(friendlyURL);

		await this.saveButton.click();

		await expect(
			this.page.getByRole('heading', {name: 'Onboarding'})
		).toBeVisible();
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class DigitalSalesRoomTemplateSettingsPage {
	readonly clientLogoButton: Locator;
	readonly clientLogoSticker: Locator;
	readonly descriptionInput: Locator;
	readonly lookAndFeelLink: Locator;
	readonly page: Page;
	readonly primaryColorInput: Locator;
	readonly roomNameInput: Locator;
	readonly saveButton: Locator;
	readonly secondaryColorInput: Locator;
	readonly selectFileButton: Locator;

	constructor(page: Page) {
		this.clientLogoButton = page.getByTestId('clientLogoButton');
		this.clientLogoSticker = page.getByTestId('clientLogoSticker');
		this.descriptionInput = page.getByTestId('descriptionInput');
		this.lookAndFeelLink = page.getByRole('link', {name: 'Look and Feel'});
		this.page = page;
		this.primaryColorInput = page.getByTestId('primaryColorInput');
		this.roomNameInput = page.getByTestId('roomNameInput');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.secondaryColorInput = page.getByTestId('secondaryColorInput');
		this.selectFileButton = page.getByTestId('selectFileButton');
	}
}

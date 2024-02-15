/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Page} from '@playwright/test';

const MOCK_API_KEY = 'VALID_API_KEY';
const STR_BLANK = '';
export class AICreatorInstanceSettingsPage {
    readonly apiKeyInput: Locator;
    readonly dalleCheckbox: Locator;
	readonly page: Page;
    readonly saveButton: Locator;

	constructor(page: Page) {
		this.page = page;

        this.apiKeyInput = this.page.getByLabel('API Key');
        this.dalleCheckbox = this.page.getByText('Enable DALL-E to Create Images');
        this.saveButton = this.page.getByRole('button', {name: 'Save'});
	}

    async goto() {
		await this.page.getByLabel('Open Applications MenuCtrl+').click();
		await this.page.getByRole('tab', {name: 'Control Panel'}).click();
		await this.page
			.getByRole('menuitem', {name: 'Instance Settings'})
			.click();
		await this.page.getByRole('link', {name: 'AI Creator'}).click();
	}

	async enableDalleCreateImages() {
		await this.goto();

		await this.dalleCheckbox.check();
		await this.saveButton.click();
		await this.page.waitForLoadState();
	}

    async disableDalleCreateImages() {
		await this.goto();

		await this.dalleCheckbox.uncheck();
		await this.saveButton.click();
		await this.page.waitForLoadState();
	}

    async addApiKey() {
        await this.setAPIKey(MOCK_API_KEY);
    }

    async removeApiKey() {
        await this.setAPIKey(STR_BLANK);
    }

    async setAPIKey(apikey) {
        await this.goto();

        await this.apiKeyInput.fill(apikey);
        await this.saveButton.click();
		await this.page.waitForLoadState();
    }
}

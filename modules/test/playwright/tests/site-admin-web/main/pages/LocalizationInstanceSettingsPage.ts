/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {InstanceSettingsPage} from '../../../../pages/configuration-admin-web/InstanceSettingsPage';
import {waitForAlert} from '../../../../utils/waitForAlert';

export class LocalizationInstanceSettingsPage {
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly availableLanguages: Locator;
	readonly currentLanguages: Locator;
	readonly defaultLanguage: Locator;
	readonly moveToAvaiable: Locator;
	readonly moveToCurrent: Locator;
	readonly saveButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.availableLanguages = page.getByLabel('Available', {exact: true});
		this.currentLanguages = page.getByLabel('Current', {exact: true});
		this.defaultLanguage = page.getByRole('option', {selected: true});
		this.moveToAvaiable = page.getByLabel(
			'Move selected items from Current to Available.'
		);
		this.moveToCurrent = page.getByLabel(
			'Move selected items from Available to Current.'
		);
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.page = page;
	}

	async goto(configuration, forceReload = true) {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Localization',
			configuration,
			forceReload
		);
	}

	async setLanguage(languages: string[]) {
		let firstIndex = 0;
		const languagesSize = languages.length;
		do {
			const value = await this.currentLanguages
				.locator('.reorder-option')
				.nth(firstIndex)
				.getAttribute('value');
			if (!languages.includes(value)) {
				await this.currentLanguages.selectOption(value);
				await this.moveToAvaiable.click();
			}
			else {
				const index = languages.indexOf(value);
				languages.splice(index, 1);

				firstIndex++;
			}
		} while (
			firstIndex + 1 <= languagesSize &&
			firstIndex + 1 <=
				(await this.currentLanguages.locator('.reorder-option').all())
					.length
		);

		for (const language of languages) {
			await this.availableLanguages.selectOption(language);
			await this.moveToCurrent.click();
		}

		await this.saveSettings();
	}

	async saveSettings() {
		await this.saveButton.click();

		await waitForAlert(this.page);
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {SiteSettingsPage} from './SiteSettingsPage';

export class LocalizationSiteSettingsPage {
	readonly languageSelector: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly siteSettingsPage: SiteSettingsPage;

	constructor(page: Page) {
		this.languageSelector = page.getByRole('combobox');
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.siteSettingsPage = new SiteSettingsPage(page);
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.siteSettingsPage.goToSiteSetting(
			'Localization',
			'Languages',
			siteUrl
		);
	}

	async setDefaultCustomLanguage(
		language: string,
		siteUrl?: Site['friendlyUrlPath']
	) {
		await this.goto(siteUrl);

		clickAndExpectToBeVisible({
			target: this.languageSelector,
			trigger: this.page.getByRole('radio', {
				name: 'Define a custom default language',
			}),
		});

		await this.languageSelector.selectOption(language);

		await this.saveButton.click();

		await this.page.waitForLoadState();
	}
}

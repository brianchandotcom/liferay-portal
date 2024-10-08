/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {InstanceSettingsPage} from '../../../pages/configuration-admin-web/InstanceSettingsPage';

export class LocalizationInstanceSettingsPage {
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly currentLanguages: Locator;
	readonly defaultLanguage: Locator;

	constructor(page: Page) {
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.currentLanguages = page.getByLabel('Current');
		this.defaultLanguage = page.getByRole('option', {selected: true});
	}

	async goto(configuration) {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Localization',
			configuration
		);
	}
}

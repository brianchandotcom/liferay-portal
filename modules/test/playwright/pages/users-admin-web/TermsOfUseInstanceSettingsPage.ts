/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class TermsOfUseInstanceSettingsPage {
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly termsOfUseRequiredCheckbox: Locator;

	constructor(page: Page) {
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.termsOfUseRequiredCheckbox = page.getByLabel(
			'Require Terms of Use'
		);
	}

	async goto() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Instance Configuration',
			'Terms of Use'
		);
	}
}

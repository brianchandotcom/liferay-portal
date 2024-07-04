/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class FriendlyUrlInstanceSettingsPage {
	readonly page: Page;
	readonly saveButton: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;

	constructor(page: Page) {
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.instanceSettingsPage = new InstanceSettingsPage(page);
	}

	async goto() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'SEO',
			'Friendly URL'
		);
	}

	async modifySeparator(label: string, value: string) {
		const separatorInput = this.page.getByLabel(label);
		await separatorInput.click();
		await separatorInput.fill(value);

		await this.saveButton.click();
		await waitForSuccessAlert(this.page);
	}

	async resetSeparator(testId: string) {
		await this.page.getByTestId(testId).waitFor();
		await this.page.getByTestId(testId).click();
		await this.page.getByRole('button', {name: 'Save'}).click();
		await waitForSuccessAlert(this.page);
	}
}

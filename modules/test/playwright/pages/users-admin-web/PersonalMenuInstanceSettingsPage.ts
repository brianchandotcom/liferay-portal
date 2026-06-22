/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class PersonalMenuInstanceSettingsPage {
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly page: Page;
	readonly personalApplicationsLookAndFeelSelect: Locator;
	readonly showInControlMenuCheckbox: Locator;

	constructor(page: Page) {
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.page = page;
		this.personalApplicationsLookAndFeelSelect = page.getByRole(
			'combobox',
			{
				name: 'Personal Applications Look and Feel',
			}
		);
		this.showInControlMenuCheckbox = page.getByLabel(
			'Show in Control Menu'
		);
	}

	async goToPersonalMenuSettings() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Users',
			'Personal Menu'
		);
	}

	async save() {
		await this.instanceSettingsPage.saveAndWaitForAlert();
	}

	async selectPersonalApplicationsLookAndFeel(option: string) {
		const optionLocator = this.page.getByRole('option', {name: option});

		await clickAndExpectToBeVisible({
			target: optionLocator,
			timeout: 1000,
			trigger: this.personalApplicationsLookAndFeelSelect,
		});

		await optionLocator.click({force: true});

		await this.page.keyboard.press('Escape');
	}
}

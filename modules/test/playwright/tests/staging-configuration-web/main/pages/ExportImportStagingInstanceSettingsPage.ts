/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {InstanceSettingsPage} from '../../../../pages/configuration-admin-web/InstanceSettingsPage';

export class ExportImportStagingInstanceSettingsPage {
	readonly actionsButton: Locator;
	readonly page: Page;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly resetDefaultValuesOption: Locator;

	constructor(page: Page) {
		this.page = page;
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.actionsButton = this.instanceSettingsPage.page
			.locator(
				'[id="_com_liferay_configuration_admin_web_portlet_InstanceSettingsPortlet_fm"]'
			)
			.getByRole('button')
			.first();
		this.resetDefaultValuesOption = this.page.getByRole('menuitem', {
			name: 'Reset Default Values',
		});
	}

	async goto() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Infrastructure',
			'Export/Import, Staging'
		);

		await this.page.waitForLoadState();
	}

	async checkConfigurationOption({
		checked,
		label,
	}: {
		checked: boolean;
		label: string;
	}) {
		await this.instanceSettingsPage.checkOption(label, checked);
		await this.instanceSettingsPage.saveButton.click();
	}

	async resetDefaultValues() {
		await this.actionsButton.click();
		await this.resetDefaultValuesOption.click();
	}
}

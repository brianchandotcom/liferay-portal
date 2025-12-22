/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {SystemSettingsPage} from '../../../../pages/configuration-admin-web/SystemSettingsPage';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';

export class ExportImportStagingSystemSettingsPage {
	readonly actionsButton: Locator;
	readonly page: Page;
	readonly resetDefaultValuesOption: Locator;
	readonly showAdvancedConfigurationByDefault: Locator;
	readonly systemSettingsPage: SystemSettingsPage;

	constructor(page: Page) {
		this.page = page;
		this.systemSettingsPage = new SystemSettingsPage(page);
		this.showAdvancedConfigurationByDefault =
			this.systemSettingsPage.page.getByLabel(
				'Show Advanced Staging Configuration by Default'
			);
		this.actionsButton = this.systemSettingsPage.page
			.locator(
				'[id="_com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet_fm"]'
			)
			.getByRole('button')
			.first();
		this.resetDefaultValuesOption = this.page.getByRole('menuitem', {
			name: 'Reset Default Values',
		});
	}

	async goto() {
		await this.systemSettingsPage.goToSystemSetting(
			'Infrastructure',
			'Export/Import, Staging'
		);
	}

	async checkShowAdvancedStagingConfiguration(checked: boolean) {
		await this.systemSettingsPage.checkOption(
			'Show Advanced Staging Configuration by Default',
			checked
		);
		await this.systemSettingsPage.saveAndWaitForAlert();
	}

	async resetDefaultValues() {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.resetDefaultValuesOption,
			trigger: this.actionsButton,
		});
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

export class StagingConfigurationPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async disableTemporaryLARdeletion() {
		await this.page
			.getByLabel(
				'Delete temporary LAR during a successful staging publish process'
			)
			.uncheck();

		if (
			await this.page
				.getByRole('button', {name: 'Save'})
				.isVisible({timeout: 200})
		) {
			await this.page.getByRole('button', {name: 'Save'}).click();
		}

		await this.page.getByRole('button', {name: 'Update'}).click();
	}

	async goto(siteKey: string) {
		await this.page.goto(
			`/group/${siteKey}/~/control_panel/manage?p_p_id=com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet&_com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet_factoryPid=com.liferay.staging.configuration.StagingConfiguration&_com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet_mvcRenderCommandName=%2Fconfiguration_admin%2Fedit_configuration`
		);
	}
}

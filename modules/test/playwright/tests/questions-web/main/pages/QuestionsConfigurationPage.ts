/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {waitForAlert} from '../../../../utils/waitForAlert';

export class QuestionsConfigurationPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async goto() {
		await this.page.goto(
			'/group/control_panel/manage?p_p_id=com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet&_com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet_factoryPid=com.liferay.questions.web.internal.configuration.QuestionsConfiguration&_com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet_mvcRenderCommandName=%2Fconfiguration_admin%2Fedit_configuration'
		);
	}

	async setBasePathForHistoryRouter(basePath: string) {
		await this.goto();

		await this.page
			.getByLabel('Set Base Path for History Router')
			.fill(basePath);

		await this.save();
	}

	async setEnableAnonymousRead(enabled: boolean) {
		await this.goto();

		await this.page.getByLabel('Enable Anonymous Read').setChecked(enabled);

		await this.save();
	}

	async setEnableRedirectToLogin(enabled: boolean) {
		await this.goto();

		await this.page
			.getByLabel('Enable Redirect to Login')
			.setChecked(enabled);

		await this.save();
	}

	private async save() {
		await this.page.locator('.configuration-submit-button').click();

		await waitForAlert(this.page);
	}
}

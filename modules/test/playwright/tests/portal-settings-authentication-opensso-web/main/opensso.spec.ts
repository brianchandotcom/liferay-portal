/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(loginTest());

test(
	'Block request with invalid CSRF token',
	{tag: '@LPD-81903'},
	async ({page}) => {
		await page.goto(
			'/group/guest/~/control_panel/manage' +
				'?p_p_id=com_liferay_configuration_admin_web_portlet_InstanceSettingsPortlet' +
				'&p_p_lifecycle=0' +
				'&p_p_state=exclusive' +
				'&p_p_mode=view' +
				'&_com_liferay_configuration_admin_web_portlet_InstanceSettingsPortlet_mvcRenderCommandName=%2Fportal_settings_authentication_opensso%2Ftest_open_sso' +
				'&_com_liferay_configuration_admin_web_portlet_InstanceSettingsPortlet_openSsoLoginURL=http%3A%2F%2Flocalhost%3A8008%2Flogin' +
				'&_com_liferay_configuration_admin_web_portlet_InstanceSettingsPortlet_openSsoServiceURL=http%3A%2F%2Flocalhost%3A8008%2Fredirect' +
				'&csrf_poc=from_external'
		);

		await expect(
			page.getByText('Portlet is temporarily unavailable.', {exact: true})
		).toBeVisible();
	}
);

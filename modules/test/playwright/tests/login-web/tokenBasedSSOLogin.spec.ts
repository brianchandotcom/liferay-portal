/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {accountsPagesTest} from '../../fixtures/accountsPagesTest';
import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {serverAdministrationPageTest} from '../../fixtures/serverAdministrationPageTest';
import {liferayConfig} from '../../liferay.config';
import {InstanceSettingsPage} from '../../pages/configuration-admin-web/InstanceSettingsPage';
import {SystemSettingsPage} from '../../pages/configuration-admin-web/SystemSettingsPage';
import {VirtualInstancesPage} from '../../pages/portal-instances-web/VirtualInstancesPage';
import performLogin from '../../utils/performLogin';
import {
	enableTokenBasedSSO,
	resetTokenBasedSSOConfiguration,
	saveOrUpdateTokenBasedSSOConfiguration,
	verifyTokenBasedSSO,
} from './utils/tokenBasedSSO';

export const test = mergeTests(
	accountsPagesTest,
	apiHelpersTest,
	applicationsMenuPageTest,
	dataApiHelpersTest,
	loginTest(),
	serverAdministrationPageTest
);

const DEFAULT_BASE_URL = liferayConfig.environment.baseUrl;
const DEFAULT_VIRTUAL_INSTANCE_NAME = 'www.able.com';
const DEFAULT_VIRTUAL_INSTANCE_URL = `http://${DEFAULT_VIRTUAL_INSTANCE_NAME}:8080`;

test.afterAll(async ({browser}) => {
	const page = await browser.newPage();

	await performLogin(page, 'test');

	const systemSettingsPage = new SystemSettingsPage(page);

	await resetTokenBasedSSOConfiguration(systemSettingsPage);
});

test.beforeAll(async ({browser}) => {
	const page = await browser.newPage();

	await performLogin(page, 'test');

	const systemSettingsPage = new SystemSettingsPage(page);

	await resetTokenBasedSSOConfiguration(systemSettingsPage);

	await enableTokenBasedSSO(systemSettingsPage);
});

test.describe('Users could login using Token Based SSO.  See LRQA-27622.', () => {
	test('Verify token based login with default instance', async () => {
		await verifyTokenBasedSSO('test@liferay.com', DEFAULT_BASE_URL);
	});

	test('Verify token based login with virtual instance', async ({
		browser,
		page,
	}) => {
		const virtualInstancesPage = new VirtualInstancesPage(page);

		await virtualInstancesPage.addNewVirtualInstance(
			DEFAULT_VIRTUAL_INSTANCE_NAME
		);

		liferayConfig.environment.baseUrl = DEFAULT_VIRTUAL_INSTANCE_URL;

		const newPage = await browser.newPage({
			baseURL: DEFAULT_VIRTUAL_INSTANCE_URL,
		});

		await performLogin(
			newPage,
			'test',
			'?p_p_id=com_liferay_login_web_portlet_LoginPortlet&' +
				'p_p_state=maximized',
			`@${DEFAULT_VIRTUAL_INSTANCE_NAME}.com`
		);

		const instanceSettingsPage = new InstanceSettingsPage(newPage);

		await instanceSettingsPage.goToInstanceSetting(
			'SSO',
			'Token Based SSO'
		);

		const tokenBasedSSOPage = instanceSettingsPage.page;

		await tokenBasedSSOPage.waitForLoadState();
		await tokenBasedSSOPage.getByLabel('Enabled').check();

		await saveOrUpdateTokenBasedSSOConfiguration(tokenBasedSSOPage);

		await tokenBasedSSOPage.waitForLoadState();

		const token = `test@${DEFAULT_VIRTUAL_INSTANCE_NAME}.com`;
		const url = `${DEFAULT_VIRTUAL_INSTANCE_URL}/web/guest`;

		await verifyTokenBasedSSO(token, url);

		liferayConfig.environment.baseUrl = DEFAULT_BASE_URL;

		await virtualInstancesPage.deleteVirtualInstance(
			DEFAULT_VIRTUAL_INSTANCE_NAME
		);
	});
});

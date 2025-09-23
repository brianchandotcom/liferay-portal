/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';

const test = mergeTests(loginTest());

test(
	'Check error message disappears',
	{tag: ['@LPD-65813']},
	async ({page}) => {
		await page.getByLabel('Open Applications MenuCtrl+').click();

		await page.getByRole('tab', {name: 'Control Panel'}).click();

		await page
			.getByRole('menuitem', {name: 'Server Administration'})
			.click();

		await page.getByRole('link', {name: 'Script'}).click();

		const textVerification = page.locator(
			'[id="_com_liferay_server_admin_web_portlet_ServerAdminPortlet_captchaText"]'
		);

		await textVerification.waitFor({state: 'visible'});

		await textVerification.fill('test');

		await page.getByRole('button', {name: 'Execute'}).click();

		const errorMessage = page.getByText('Close Error:Text verification');

		await errorMessage.waitFor({state: 'visible'});

		const closeButton = page
			.locator(
				'[id="_com_liferay_server_admin_web_portlet_ServerAdminPortlet_fm"]'
			)
			.getByLabel('Close');

		await closeButton.waitFor({state: 'visible'});

		await closeButton.click();

		await expect(errorMessage).not.toBeVisible();
	}
);

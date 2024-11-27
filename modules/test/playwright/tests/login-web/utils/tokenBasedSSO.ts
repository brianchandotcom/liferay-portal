/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, request} from '@playwright/test';

import {SystemSettingsPage} from '../../../pages/configuration-admin-web/SystemSettingsPage';
import {VirtualInstancesPage} from '../../../pages/portal-instances-web/VirtualInstancesPage';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {waitForAlert} from '../../../utils/waitForAlert';

export async function deleteVirtualInstance(name: string, page: Page) {
	const virtualInstancesPage = new VirtualInstancesPage(page);

	await virtualInstancesPage.deleteVirtualInstance(name);
}

export async function enableTokenBasedSSO(
	systemSettingsPage: SystemSettingsPage
) {
	await systemSettingsPage.goToSystemSetting('SSO', 'Token Based SSO');

	await systemSettingsPage.page.waitForLoadState();

	const tokenBasedSSOPage = systemSettingsPage.page;

	const enabled = tokenBasedSSOPage.getByLabel('Enabled');

	const isChecked = await enabled.isChecked();

	if (!isChecked) {
		await enabled.check();
	}

	await expect(enabled).toBeChecked();

	await tokenBasedSSOPage.getByLabel('Token Location').click();
	await tokenBasedSSOPage
		.getByRole('option', {name: 'Request Header'})
		.click();
	await saveOrUpdateTokenBasedSSOConfiguration(tokenBasedSSOPage);
}

export async function resetTokenBasedSSOConfiguration(
	systemSettingsPage: SystemSettingsPage
) {
	await systemSettingsPage.goToSystemSetting('SSO', 'Token Based SSO');

	await systemSettingsPage.page.waitForLoadState();

	const tokenBasedSSOPage = systemSettingsPage.page;

	if (
		await tokenBasedSSOPage
			.getByRole('button', {name: 'Actions'})
			.isVisible()
	) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: tokenBasedSSOPage.getByRole('link', {
				name: 'Reset Default Values',
			}),
			trigger: tokenBasedSSOPage.getByRole('button', {
				name: 'Actions',
			}),
		});

		await waitForAlert(tokenBasedSSOPage);
	}
}

export async function saveOrUpdateTokenBasedSSOConfiguration(page: Page) {
	const saveButton = page.getByRole('button', {
		name: 'Save',
	});

	if (await saveButton.isVisible()) {
		await saveButton.click();
	}
	else {
		await page
			.getByRole('button', {
				name: 'Update',
			})
			.click();
	}

	await waitForAlert(page);
}

export async function verifyTokenBasedSSO(token: string, url: string) {
	const context = await request.newContext({
		extraHTTPHeaders: {
			SM_USER: token,
		},
	});

	const response = await context.get(url);
	expect(response.status()).toBe(200);
	const responseBody = await response.text();
	expect(responseBody).not.toContain('Sign In');
	await context.dispose();
}

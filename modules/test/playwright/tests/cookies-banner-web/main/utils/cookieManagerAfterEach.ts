/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';

export async function clearConsentCookies(systemSettingsPage) {
	await systemSettingsPage.page
		.context()
		.clearCookies({name: /^CONSENT_TYPE_/});
	await systemSettingsPage.page
		.context()
		.clearCookies({name: /^USER_CONSENT_CONFIGURED/});
}

export async function resetAllCookieManagerConfigurations(systemSettingsPage) {
	await systemSettingsPage.goToSystemSetting('Privacy', 'Cookie Manager');

	const menuItems = await systemSettingsPage.page.getByRole('menuitem').all();

	for (const menuItem of menuItems.reverse()) {
		await menuItem.click();

		await systemSettingsPage.page.waitForTimeout(1000);

		await systemSettingsPage.page.waitForLoadState();

		let dialog = false;

		if (await menuItem.getByText('Product Analytics').isVisible()) {
			continue;
		}
		else if (await menuItem.getByText('Cookie Manager').isVisible()) {
			dialog = true;
		}

		await resetConfiguration(dialog, systemSettingsPage);
	}
}

async function resetConfiguration(dialog = false, systemSettingsPage) {
	if (
		await systemSettingsPage.page
			.getByRole('button', {name: 'Actions'})
			.isVisible()
	) {
		if (dialog) {
			systemSettingsPage.page.once('dialog', async (dialogWindow) => {
				await dialogWindow.accept();
			});
		}

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: systemSettingsPage.page.getByRole('menuitem', {
				name: 'Reset Default Values',
			}),
			trigger: systemSettingsPage.page.getByRole('button', {
				name: 'Actions',
			}),
		});
	}
}

export async function resetCookieManagerConfiguration(systemSettingsPage) {
	await systemSettingsPage.goToSystemSetting('Privacy', 'Cookie Manager');

	await systemSettingsPage.page.waitForTimeout(1000);

	await systemSettingsPage.page.waitForLoadState();

	await resetConfiguration(true, systemSettingsPage);
}

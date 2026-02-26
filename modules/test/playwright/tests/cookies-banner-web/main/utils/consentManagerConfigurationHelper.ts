/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SystemSettingsPage} from '../../../../pages/configuration-admin-web/SystemSettingsPage';
import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {reloadUntilVisible} from '../../../../utils/reloadUntilVisible';
import {waitForAlert} from '../../../../utils/waitForAlert';

interface ConsentManagerConfiguration {
	consentRenewalPeriod?: string;
	enabled?: boolean;
	explicitCookieConsentMode?: boolean;
	forceReload?: boolean;
	storeConsent?: boolean;
}

export async function clearConsentCookies(systemSettingsPage) {
	await systemSettingsPage.page
		.context()
		.clearCookies({name: /^CONSENT_TYPE_/});
	await systemSettingsPage.page
		.context()
		.clearCookies({name: /^USER_CONSENT_CONFIGURED/});
}

export async function resetAllConsentManagerConfigurations(systemSettingsPage) {
	await systemSettingsPage.goToSystemSetting('Privacy', 'Consent Manager');

	const menuItems = await systemSettingsPage.page
		.locator('#main-content')
		.getByRole('menuitem')
		.all();

	for (const menuItem of menuItems.reverse()) {
		await menuItem.click();

		await systemSettingsPage.page.waitForTimeout(1000);

		await systemSettingsPage.page.waitForLoadState();

		let dialog = false;

		if (await menuItem.getByText('Product Analytics').isVisible()) {
			continue;
		}
		else if (await menuItem.getByText('Consent Manager').isVisible()) {
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

export async function resetConsentManagerConfiguration(systemSettingsPage) {
	await systemSettingsPage.goToSystemSetting('Privacy', 'Consent Manager');

	await systemSettingsPage.page.waitForTimeout(1000);

	await systemSettingsPage.page.waitForLoadState();

	await resetConfiguration(true, systemSettingsPage);
}

export async function saveOrUpdateConfiguration(dialog: boolean, page) {
	if (dialog) {
		page.once('dialog', async (dialogWindow) => {
			await dialogWindow.accept();
		});
	}

	const saveButton = page.getByRole('button', {
		name: 'Save',
	});

	const updateButton = page.getByRole('button', {
		name: 'Update',
	});

	if (await saveButton.isVisible()) {
		await saveButton.dispatchEvent('click');
	}
	else if (await updateButton.isVisible()) {
		await updateButton.dispatchEvent('click');
	}

	try {
		await waitForAlert(page, undefined, {timeout: 5000});
	}
	catch (error) {
		if (page.getByRole('heading', {name: '500'})) {
			await reloadUntilVisible({
				maxAttempts: 1,
				myLocator: updateButton,
				page,
			});
		}
		else {
			throw error;
		}
	}
}

export async function updateConsentManagerConfiguration(
	page,
	{
		consentRenewalPeriod,
		enabled,
		explicitCookieConsentMode,
		forceReload,
		storeConsent,
	}: ConsentManagerConfiguration
) {
	if (forceReload) {
		const systemSettingsPage = new SystemSettingsPage(page);
		await systemSettingsPage.goToSystemSetting(
			'Privacy',
			'Consent Manager'
		);
	}

	const consentRenewalPeriodField = page
		.getByLabel('Consent Renewal Period')
		.first();
	const enabledCheckbox = page.getByLabel('Enabled', {exact: true});

	await enabledCheckbox.waitFor({state: 'visible'});

	let dialog = false;

	if (enabled === false) {
		await enabledCheckbox.setChecked(false);
	}
	else {
		if (
			enabledCheckbox.isChecked() &&
			consentRenewalPeriod &&
			consentRenewalPeriod !==
				consentRenewalPeriodField.getAttribute('value')
		) {
			dialog = true;
		}

		if (enabled === true) {
			await enabledCheckbox.setChecked(true);
		}
	}

	if (await enabledCheckbox.isChecked()) {
		const explicitCookieConsentModeLocator = await page.getByLabel(
			'Explicit Cookie Consent Mode'
		);

		while (await explicitCookieConsentModeLocator.isDisabled()) {
			await page.waitForTimeout(250);
		}

		if (explicitCookieConsentMode !== undefined) {
			await explicitCookieConsentModeLocator.setChecked(
				explicitCookieConsentMode
			);
		}

		if (consentRenewalPeriod) {
			await consentRenewalPeriodField.fill(consentRenewalPeriod);
		}

		if (storeConsent !== undefined) {
			const systemSettingsPageLocator = await page.locator(
				'[id="_com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet_fm"]'
			);

			await systemSettingsPageLocator
				.getByLabel('Store Consent')
				.setChecked(storeConsent);
		}
	}

	await saveOrUpdateConfiguration(dialog, page);
}

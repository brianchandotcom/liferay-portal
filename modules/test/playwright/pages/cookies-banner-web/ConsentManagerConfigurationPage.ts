/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {SystemSettingsPage} from '../configuration-admin-web/SystemSettingsPage';

export class ConsentManagerConfigurationPage {
	readonly consentRenewalPeriodForDissentInput: Locator;
	readonly consentRenewalPeriodInput: Locator;
	readonly consentRenewalPeriodUnitSelect: Locator;
	readonly dissentRenewalPeriodUnitSelect: Locator;
	readonly enabledCheckbox: Locator;
	readonly explicitCookieConsentModeCheckbox: Locator;
	readonly floatingIconEnabledCheckbox: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly storeConsentCheckbox: Locator;
	readonly systemSettingsPortletForm: Locator;
	readonly updateButton: Locator;

	constructor(page: Page) {
		this.systemSettingsPortletForm = page.locator(
			'[id="_com_liferay_configuration_admin_web_portlet_SystemSettingsPortlet_fm"]'
		);

		this.consentRenewalPeriodForDissentInput =
			this.systemSettingsPortletForm.locator(
				'input[name$="dissentRenewalPeriod"]'
			);
		this.consentRenewalPeriodInput = this.systemSettingsPortletForm.locator(
			'input[name$="consentRenewalPeriod"]'
		);
		this.consentRenewalPeriodUnitSelect =
			this.systemSettingsPortletForm.locator(
				'select[name$="consentRenewalPeriodTimeUnit"]'
			);
		this.dissentRenewalPeriodUnitSelect =
			this.systemSettingsPortletForm.locator(
				'select[name$="dissentRenewalPeriodTimeUnit"]'
			);
		this.enabledCheckbox = this.systemSettingsPortletForm.getByRole(
			'checkbox',
			{
				exact: true,
				name: 'Enabled',
			}
		);
		this.explicitCookieConsentModeCheckbox =
			this.systemSettingsPortletForm.getByRole('checkbox', {
				exact: true,
				name: 'Explicit Cookie Consent Mode',
			});
		this.floatingIconEnabledCheckbox =
			this.systemSettingsPortletForm.getByRole('checkbox', {
				exact: true,
				name: 'Floating Icon Enabled',
			});
		this.page = page;
		this.saveButton = this.systemSettingsPortletForm.getByRole('button', {
			exact: true,
			name: 'Save',
		});
		this.storeConsentCheckbox = this.systemSettingsPortletForm.getByRole(
			'checkbox',
			{
				exact: true,
				name: 'Store Consent',
			}
		);
		this.updateButton = this.systemSettingsPortletForm.getByRole('button', {
			exact: true,
			name: 'Update',
		});
	}

	async goTo() {
		const systemSettingsPage = new SystemSettingsPage(this.page);
		await systemSettingsPage.goToSystemSetting(
			'Privacy',
			'Consent Manager'
		);

		await this.enabledCheckbox.waitFor();
	}
}

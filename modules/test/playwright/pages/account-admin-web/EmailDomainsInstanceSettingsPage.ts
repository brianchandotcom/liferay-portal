/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class EmailDomainsInstanceSettingsPage {
	readonly blockedEmailDomainsInput: Locator;
	readonly enableEmailDomainValidationInput: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.blockedEmailDomainsInput = page.getByLabel(
			'Blocked Email Domains'
		);
		this.enableEmailDomainValidationInput = page.getByLabel(
			'Enable Email Domain Validation'
		);
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.page = page;
		this.saveButton = page
			.getByRole('button', {
				exact: true,
				name: 'Save',
			})
			.or(
				page.getByRole('button', {
					exact: true,
					name: 'Update',
				})
			);
	}

	async goto() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Accounts',
			'Email Domains'
		);
	}

	async enableEmailDomainValidation(enable = true, blockedDomains = '') {
		await this.goto();

		if (
			(enable &&
				!(await this.enableEmailDomainValidationInput.isChecked())) ||
			(!enable &&
				(await this.enableEmailDomainValidationInput.isChecked()))
		) {
			await this.blockedEmailDomainsInput.fill(
				blockedDomains.replace(',', '\n')
			);
			await this.enableEmailDomainValidationInput.click();
			await this.saveButton.click();

			await waitForAlert(this.page);
		}
	}
}

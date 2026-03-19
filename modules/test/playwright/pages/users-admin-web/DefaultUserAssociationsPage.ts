/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';
import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class DefaultUserAssociationsPage {
	readonly applyToExistingUsersCheckbox: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly organizationSitesInput: Locator;
	readonly page: Page;
	readonly regularRolesInput: Locator;
	readonly saveButton: Locator;
	readonly sitesInput: Locator;
	readonly userGroupsInput: Locator;

	constructor(page: Page) {
		this.applyToExistingUsersCheckbox = page.getByLabel(
			'Apply to Existing Users'
		);
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.organizationSitesInput = page.locator(
			'[id$="admin-default-organization-group-names"]'
		);
		this.page = page;
		this.regularRolesInput = page.locator(
			'[id$="admin-default-role-names"]'
		);
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.sitesInput = page.locator('[id$="admin-default-group-names"]');
		this.userGroupsInput = page.locator(
			'[id$="admin-default-user-group-names"]'
		);
	}

	async goto() {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Users',
			'Default User Associations'
		);
	}

	async resetFields() {
		await this.goto();

		await this.organizationSitesInput.clear();
		await this.regularRolesInput.clear();
		await this.sitesInput.clear();
		await this.userGroupsInput.clear();
		await this.saveButton.click();

		await waitForAlert(this.page);
	}
}

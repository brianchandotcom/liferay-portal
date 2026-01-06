/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class AccountNotificationsPage {
	readonly accountNotificationsMenuItem: Locator;
	readonly deleteButton: Locator;
	readonly selectAllItemsCheckbox: Locator;
	readonly userPersonalMenuButton: Locator;

	constructor(page: Page) {
		this.accountNotificationsMenuItem = page.getByRole('menuitem', {
			name: 'Notifications',
		});
		this.deleteButton = page.getByRole('button', {name: 'Delete'});
		this.selectAllItemsCheckbox = page.getByLabel(
			'Select All Items on the Page'
		);
		this.userPersonalMenuButton = page.getByTitle('User Profile Menu');
	}

	async deleteEnabledNotifications() {
		if (
			!(await this.selectAllItemsCheckbox.isVisible()) ||
			!(await this.selectAllItemsCheckbox.isEnabled())
		) {
			return;
		}

		await this.selectAllItemsCheckbox.check();

		await expect(this.deleteButton).toBeVisible();

		await this.deleteButton.click();

		await expect(this.deleteButton).toBeVisible();
	}

	async goToAccountNotifications() {
		await this.userPersonalMenuButton.click();

		await expect(this.accountNotificationsMenuItem).toBeVisible();

		await this.accountNotificationsMenuItem.click();
	}
}

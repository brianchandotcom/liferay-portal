/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';

export class AccessibilityMenuPage {
	readonly closeButton: Locator;
	readonly enableAccessibilityMenuCheckbox: Locator;
	readonly menuTitle: Locator;
	readonly openAccessibilityMenuButton: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly underlinedLinksToggle: Locator;

	constructor(page: Page) {
		this.closeButton = page
			.locator('.modal')
			.getByLabel('Close', {exact: true});
		this.enableAccessibilityMenuCheckbox = page.getByRole('checkbox', {
			name: 'Enable Accessibility Menu',
		});
		this.menuTitle = page
			.locator('.modal')
			.getByLabel('Accessibility Menu');
		this.openAccessibilityMenuButton = page.getByRole('button', {
			name: 'Open Accessibility Menu',
		});
		this.page = page;
		this.saveButton = page
			.getByRole('button', {name: 'Save'})
			.or(page.getByRole('button', {name: 'Update'}));
		this.underlinedLinksToggle = page.getByLabel('Underlined Links');
	}

	async enableAccessibilityMenu() {
		if (!(await this.enableAccessibilityMenuCheckbox.isChecked())) {
			await this.enableAccessibilityMenuCheckbox.check();

			await this.saveButton.click();

			await waitForAlert(this.page);
		}
	}

	async disableAccessibilityMenu() {
		if (await this.enableAccessibilityMenuCheckbox.isChecked()) {
			await this.enableAccessibilityMenuCheckbox.uncheck();

			await this.saveButton.click();

			await waitForAlert(this.page);
		}
	}

	async isAccessibilityMenuAttached() {
		return (await this.openAccessibilityMenuButton.count()) === 1;
	}

	async openAccessibilityMenu() {
		await this.page.waitForLoadState();

		await this.page.focus('body');

		await this.page.keyboard.press('Tab');

		await this.page.keyboard.press('Tab');

		await this.page.keyboard.press('Enter');

		await expect(this.menuTitle).toBeVisible();
	}

	async closeAccessibilityMenu() {
		await this.closeButton.click();

		await expect(this.menuTitle).toBeHidden();
	}

	async toggleUnderlinedLinks(check: boolean) {
		await this.toggle(this.underlinedLinksToggle, check);

		await this.closeAccessibilityMenu();
	}

	async toggle(locator: Locator, check: boolean) {
		await expect(async () => {
			await locator.setChecked(check);

			await expect(locator).toBeChecked({checked: check});
		}).toPass();
	}
}

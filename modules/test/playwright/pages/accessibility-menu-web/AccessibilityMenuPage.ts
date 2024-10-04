/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import { waitForAlert } from '../../utils/waitForAlert';

export class AccessibilityMenuPage {
	readonly closeButton: Locator;
	readonly enableAccessibilityMenuCheckbox: Locator;
	readonly openAccessibilityMenuButton: Locator;
	readonly page: Page;
	readonly underlinedLinksToggle: Locator;
	readonly updateButton: Locator;

	constructor(page: Page) {
		this.closeButton = page.getByLabel('close', {exact: true});
		this.enableAccessibilityMenuCheckbox = page.getByLabel(
			'Enable Accessibility Menu'
		);
		this.openAccessibilityMenuButton = page.getByRole('button', {
			name: 'Open Accessibility Menu',
		});
		this.page = page;
		this.underlinedLinksToggle = page.getByLabel('Underlined Links');
		this.updateButton = page.getByRole('button', {name: 'Update'});
	}

	async openAccessibilityMenu() {
		await this.page.keyboard.press('Tab');
		await this.page.keyboard.press('Tab');

		await this.page.keyboard.press('Enter');

		expect(this.page.getByLabel('Accessibility Menu')).toBeVisible();
	}

	async enableAccessibilityMenu() {
		const isEnabled =
			await this.enableAccessibilityMenuCheckbox.isEnabled();

		if (
			isEnabled &&
			!(await this.enableAccessibilityMenuCheckbox.isChecked())
		) {
			await this.enableAccessibilityMenuCheckbox.check();

			await this.updateButton.click();

			await waitForAlert(this.page);
		}
	}

	async toggleUnderlinedLinks(check: boolean) {
		await this.underlinedLinksToggle.setChecked(check);

		await expect(this.underlinedLinksToggle).toBeChecked({checked: check});

		await this.closeButton.click();
	}
}

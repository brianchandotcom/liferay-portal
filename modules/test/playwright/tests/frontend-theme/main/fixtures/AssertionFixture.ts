/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

export class AssertionFixture {
	constructor(private page: Page) {}

	async expectToContainText(locator: string, text: string) {
		const element = this.page.locator(locator);
		const elementText = await element.textContent();

		expect(elementText).toContain(text);
	}

	async expectNotToContainText(locator: string, text: string) {
		const element = this.page.locator(locator);
		const elementText = await element.textContent();

		expect(elementText).not.toContain(text);
	}
}

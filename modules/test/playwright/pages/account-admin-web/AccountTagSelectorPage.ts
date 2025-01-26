/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

export class AccountTagSelectorPage {
	readonly doneButton: Locator;
	readonly tagCheckbox: (tagName: string) => Locator;
	readonly frame: FrameLocator;
	readonly page: Page;

	constructor(page: Page) {
		this.doneButton = page.getByRole('button', {name: 'Done'});
		this.tagCheckbox = (tagName) => this.frame.getByLabel(tagName);
		this.frame = page.frameLocator(`iframe[title="Tags"]`);
		this.page = page;
	}

	async selectTag(tagNames: Array<string>) {
		for (const tagName of tagNames) {
			await this.tagCheckbox(tagName).check();
		}

		await this.doneButton.click();
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';
import {waitForAlert} from '../../utils/waitForAlert';

export class BundleBlacklistPage {
	readonly blacklistBundleSymbolicInput: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly updateButton: Locator;

	constructor(page: Page) {
		this.blacklistBundleSymbolicInput = page.getByRole('textbox', {
			name: 'Blacklist Bundle Symbolic',
		});
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.updateButton = page.getByRole('button', {name: 'Update'});
	}

	async goto() {
		await this.page.goto(PORTLET_URLS.systemBundleBlacklist);
	}

	async updateBundleBlacklist(content: string) {
		await this.goto();

		await this.blacklistBundleSymbolicInput.fill(content);

		await this.save();
	}

	async save() {
		await this.saveButton.or(this.updateButton).click();

		await waitForAlert(this.page);
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../../utils/waitForAlert';

export class SiteSettingsPage {
	readonly page: Page;
	readonly saveButton: Locator;
	readonly siteConfigurationLink: Locator;
	readonly siteURLLink: Locator;
	readonly virtualHostInput: Locator;

	constructor(page: Page) {
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.siteConfigurationLink = page.getByText('Site Configuration');
		this.siteURLLink = page.getByText('Site URL', {exact: true});
		this.virtualHostInput = page.getByPlaceholder('Virtual Host');
	}

	async goto(friendlyURLPath: string) {
		await this.page.goto(
			`/group/${friendlyURLPath.replace(/^\//, '')}/~/control_panel/manage/-/site/settings`
		);
	}

	async setVirtualHost(friendlyURLPath: string, virtualHost: string) {
		await this.goto(friendlyURLPath);

		await this.siteConfigurationLink.click();
		await this.siteURLLink.click();

		await this.virtualHostInput.fill(virtualHost);
		await this.saveButton.click();

		await waitForAlert(this.page);
	}

	async clearVirtualHost(friendlyURLPath: string) {
		await this.goto(friendlyURLPath);

		await this.siteConfigurationLink.click();
		await this.siteURLLink.click();

		await this.virtualHostInput.clear();
		await this.saveButton.click();

		await waitForAlert(this.page);
	}
}

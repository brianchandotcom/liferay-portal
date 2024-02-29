/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {liferayConfig} from '../../liferay.config';
export class HomePage {
	readonly page: Page;

	private readonly applicationsMenuButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.applicationsMenuButton = page.getByLabel(
			'Open Applications MenuCtrl+'
		);
	}

	async goto() {
		await this.page.goto(liferayConfig.environment.baseUrl);
	}

	async openApplicationMenu() {
		await this.applicationsMenuButton.click();
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../../../utils/portletUrls';

export class ConfigStagingPage {
	readonly page: Page;
	constructor(page: Page) {
		this.page = page;
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.staging}`
		);
	}

	async disableStaging(siteUrl?: Site['friendlyUrlPath']) {
		await this.goto(siteUrl);

		if (this.page.getByLabel('Options', {exact: true}).isVisible) {
			await clickAndExpectToBeVisible({
				autoClick: true,
				target: this.page.getByRole('menuitem', {
					name: 'Staging Configuration',
				}),
				trigger: this.page.getByLabel('Options', {exact: true}),
			});

			await this.page.getByLabel('None').check();
			this.page.once('dialog', async (dialog) => {
				await dialog.accept();
			});
			await this.page.getByRole('button', {name: 'Save'}).click();
		}
	}
}

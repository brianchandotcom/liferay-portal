/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';
import {readFile} from 'fs/promises';
import path from 'path';

import {ProductMenuPage} from '../../pages/product-navigation-control-menu-web/ProductMenuPage';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../utils/portletUrls';
import {getTempDir} from '../../utils/temp';
import {waitForAlert} from '../../utils/waitForAlert';
import {waitForSPAToBeLoaded} from '../../utils/waitForSPAToBeLoaded';

export class SiteSettingsPage {
	readonly page: Page;

	readonly defaultValuesAlert: Locator;
	readonly productMenuPage: ProductMenuPage;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.defaultValuesAlert = page
			.getByRole('alert')
			.first()
			.getByText(
				'Info:This configuration is not saved yet. The values shown are the default.'
			);
		this.productMenuPage = new ProductMenuPage(page);
		this.saveButton = page
			.getByRole('button', {name: 'Save'})
			.or(page.getByRole('button', {name: 'Update'}));
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.siteSettings}`
		);
	}

	async goToSiteSetting(
		categoryKey: string,
		configurationName?: string,
		siteUrl?: Site['friendlyUrlPath']
	) {
		await this.goto(siteUrl);

		await this.page
			.getByRole('link', {
				exact: true,
				name: categoryKey,
			})
			.click();

		if (configurationName) {
			await this.page
				.getByRole('menuitem', {
					exact: true,
					name: configurationName,
				})
				.click();
		}

		await waitForSPAToBeLoaded(this.page);
	}

	async clickOnAction(actionName: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: actionName}),
			trigger: this.page.getByRole('button', {name: 'Actions'}),
		});
	}

	async getExportedConfiguration() {
		const downloadPromise = this.page.waitForEvent('download');

		await this.clickOnAction('Export');

		const download = await downloadPromise;

		const filePath = path.join(getTempDir(), download.suggestedFilename());

		await download.saveAs(filePath);

		return await readFile(filePath, 'utf-8');
	}

	async resetToDefaultValues() {
		await this.clickOnAction('Reset Default Values');
	}

	async saveConfiguration() {
		await this.saveButton.click();

		await waitForAlert(this.page);
	}
}

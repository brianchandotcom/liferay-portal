/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ProductMenuPage} from '../../../pages/product-navigation-control-menu-web/ProductMenuPage';

export class StagingPage {
	readonly localStagingCheckbox: Locator;
	readonly page: Page;
	readonly productMenuPage: ProductMenuPage;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.localStagingCheckbox = page.getByTestId('stagingType_local');
		this.page = page;
		this.productMenuPage = new ProductMenuPage(page);
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}

	async compareCurrentPageVersions() {
		await this.getCurrentPageScreenshot('Live');
		await this.getCurrentPageScreenshot('Staging');
	}

	async enableDefaultLocalStaging() {
		await this.localStagingCheckbox.check();

		this.page.once('dialog', async (dialog) => {
			expect(dialog.message()).toContain(
				'Are you sure you want to activate local staging for'
			);
			await dialog.accept().catch();
		});

		await this.saveButton.click();

		await expect(
			this.page.getByText('Initial Publish Process').first()
		).toBeVisible();

		for await (const processResult of await this.page
			.getByTestId('processResult')
			.all()) {
			await expect(processResult.getByText('Successful')).toBeVisible({
				timeout: 60 * 1000,
			});
		}
	}

	async goToStaging() {
		await this.productMenuPage.openProductMenuIfClosed();
		await this.productMenuPage.goToStaging();
	}

	private async getCurrentPageScreenshot(version: string) {
		await this.page
			.getByLabel('Product Menu', {exact: true})
			.getByRole('link', {name: version})
			.click();

		await expect(
			this.page
				.getByTestId('productMenuSiteAdministrationPanelCategory')
				.getByText(version)
		).toBeVisible();

		const url = this.page.url();

		await this.page.goto(`${url}?p_l_mode=preview`);

		await expect(this.page).toHaveScreenshot({
			fullPage: true,
			mask: [this.page.getByTestId('notificationsCount')],
			maskColor: '#FFFFFF',
			omitBackground: true,
		});
		await this.page.goto(url);
	}
}

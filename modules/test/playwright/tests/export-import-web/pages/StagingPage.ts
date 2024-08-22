/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, Locator, Page} from '@playwright/test';
import {getComparator} from 'playwright-core/lib/utils';

import {ProductMenuPage} from '../../../pages/product-navigation-control-menu-web/ProductMenuPage';
import {getTempDir} from '../../../utils/temp';

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

    async goToStaging() {
		await this.productMenuPage.openProductMenuIfClosed();
		await this.productMenuPage.goToStaging();
	}
    
    async enableDefaultLocalStaging() {
		await this.localStagingCheckbox.check();

        await this.page.once('dialog', async (dialog) => {
			expect(dialog.message()).toContain(
				'Are you sure you want to activate local staging for'
			);
			await dialog.accept().catch();
		});

		await this.saveButton.click();

		await expect(this.page.getByText('Initial Publish Process')).toHaveCount(2);

		for await (const processResult of await this.page
			.getByTestId('processResult')
			.all())  {
			await expect(processResult.getByText('Successful')).toBeVisible({
				timeout: 60 * 1000,
			});
		}
	}

	async compareCurrentPageVersions() {
		const comparator = getComparator('image/png');

		expect(
			comparator(
				await this.getCurrentPageScreenshot('Live'),
				await this.getCurrentPageScreenshot('Staging')
			)
		).toBeNull();
	}

	private async getCurrentPageScreenshot(version: string) {
		await this.page.getByLabel('Product Menu', { exact: true }).getByRole('link', { name: version }).click();

		await expect(
			this.page.getByTestId('productMenuSiteAdministrationPanelCategory').getByText(version)
		).toBeVisible();

		return await this.page.screenshot({
			path: getTempDir() + './screenshots/' + version + 'Image.png',		
			mask: [
				await this.page.locator('.control-menu-container').first(),
				await this.page.locator('.sidenav-menu').first()
			],
			maskColor: '#FFFFFF',
			omitBackground: true,
			fullPage: true
		});
	}

}
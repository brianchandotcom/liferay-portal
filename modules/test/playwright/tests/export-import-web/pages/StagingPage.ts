/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, Locator, Page} from '@playwright/test';

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
	}

}
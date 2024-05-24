/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {ProductMenuPage} from '../../../pages/product-navigation-control-menu-web/ProductMenuPage';
import {zipFolder} from '../../../utils/zip';

export class ExportImportPage {
	readonly continueButton: Locator;
	readonly fileSelector: Locator;
	readonly importButton: Locator;
	readonly newImportButton: Locator;
	readonly newImportProcess: Locator;
	readonly page: Page;
	readonly productMenuPage: ProductMenuPage;

	constructor(page: Page) {
		this.continueButton = page.getByRole('button', {name: 'Continue'});
		this.fileSelector = page.getByRole('button', {name: 'Select File'});
		this.importButton = page.getByRole('button', {name: 'Import'});
		this.newImportButton = page.getByRole('link', {name: 'Import'});
		this.newImportProcess = page.getByRole('button', {
			name: 'New',
		});
		this.page = page;
		this.productMenuPage = new ProductMenuPage(page);
	}

	async createNewImportProcess(folderPath: string) {
		await this.newImportButton.click();

		const fileChooserPromise = this.page.waitForEvent('filechooser');

		await this.fileSelector.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(await zipFolder(folderPath));

		await this.continueButton.click();

		await this.page.waitForLoadState('domcontentloaded');
		await this.page.waitForTimeout(1000);

		await this.page
			.locator(
				'[id="_com_liferay_exportimport_web_portlet_ImportPortlet_contentLink_com_liferay_layout_admin_web_portlet_GroupPagesPortlet"]'
			)
			.click();

		await this.page
			.locator('#PagesContent')
			.getByText('Utility Pages (2)')
			.click();

		await this.page
			.locator(
				'[id="_com_liferay_exportimport_web_portlet_ImportPortlet_contentOptionsLink"]'
			)
			.click();

		await this.page.getByText('Comments', {exact: true}).click();

		await this.page
			.locator(
				'[id="_com_liferay_exportimport_web_portlet_ImportPortlet_contentOptions"]'
			)
			.getByText('Ratings')
			.click();

		await this.importButton.click();
	}

	async goToImport() {
		await this.productMenuPage.goToPublishingImport();
	}
}

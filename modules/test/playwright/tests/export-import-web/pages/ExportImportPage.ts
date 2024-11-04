/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {ProductMenuPage} from '../../../pages/product-navigation-control-menu-web/ProductMenuPage';
import {getTempDir} from '../../../utils/temp';

export class ExportImportPage {
	readonly continueButton: Locator;
	readonly downloadButton: Locator;
	readonly exportButton: Locator;
	readonly fileSelector: Locator;
	readonly importButton: Locator;
	readonly newExportButton: Locator;
	readonly newImportButton: Locator;
	readonly newImportProcess: Locator;
	readonly page: Page;
	readonly productMenuPage: ProductMenuPage;
	readonly title: Locator;

	constructor(page: Page) {
		this.continueButton = page.getByRole('button', {name: 'Continue'});
		this.downloadButton = page.getByRole('button', {name: 'Download'});
		this.exportButton = page.getByRole('button', {name: 'Export'});
		this.fileSelector = page.getByRole('button', {name: 'Select File'});
		this.importButton = page.getByRole('button', {name: 'Import'});
		this.newExportButton = page.getByRole('link', {name: 'Custom Export'});
		this.newImportButton = page.getByRole('link', {name: 'Import'});
		this.newImportProcess = page.getByRole('button', {
			name: 'New',
		});
		this.page = page;
		this.productMenuPage = new ProductMenuPage(page);
		this.title = page.getByPlaceholder('Enter the name of the process');
	}

	async createNewExportProcess(title: string) {
		await this.newExportButton.click();

		await this.title.fill(title);

		await this.exportButton.click();
	}

	async checkItemInNewlyCreatedImportProcess(
		folderPath: string,
		itemToCheck: string
	) {
		await this.newImportButton.click();

		const fileChooserPromise = this.page.waitForEvent('filechooser');

		await this.fileSelector.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(folderPath);

		await this.continueButton.click();

		await this.page.waitForLoadState('domcontentloaded');
		await this.page.waitForTimeout(1000);

		const wikiLabelCount = await this.page.getByLabel(itemToCheck).count();
		expect(wikiLabelCount).toBe(0);
	}

	async createNewImportProcess(folderPath: string) {
		await this.newImportButton.click();

		const fileChooserPromise = this.page.waitForEvent('filechooser');

		await this.fileSelector.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(folderPath);

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
			.getByText('Utility Pages')
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

	async downloadExportProcess(name: string) {
		const downloadPromise = this.page.waitForEvent('download');

		await this.page.locator('//*[contains(@href, "' + name + '")]').click();

		const download = await downloadPromise;
		const filePath = getTempDir() + download.suggestedFilename();

		await download.saveAs(filePath);

		return filePath;
	}

	async goToExport() {
		await this.productMenuPage.openProductMenuIfClosed();
		await this.productMenuPage.goToPublishingExport();
	}

	async goToImport() {
		await this.productMenuPage.openProductMenuIfClosed();
		await this.productMenuPage.goToPublishingImport();
	}
}

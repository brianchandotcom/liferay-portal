/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';
import path from 'path';

import {ApplicationsMenuPage} from '../../../pages/product-navigation-applications-menu/ApplicationsMenuPage';
import getRandomString from '../../../utils/getRandomString';
import {DateOptions} from '../types/dateOptions';
import {ExportImportPage} from './ExportImportPage';

export class CompanyExportImportPage {
	readonly page: Page;
	readonly applicationsMenuPage: ApplicationsMenuPage;
	readonly exportImportPage: ExportImportPage;
	readonly rangeDateRangeEndDate: Locator;
	readonly rangeDateRangeEndTime: Locator;
	readonly rangeDateRangeRadioButton: Locator;
	readonly rangeDateRangeStartDate: Locator;
	readonly rangeDateRangeStartTime: Locator;
	readonly rangeLast: Locator;
	readonly rangeLastRadioButton: Locator;

	constructor(page: Page) {
		this.page = page;
		this.applicationsMenuPage = new ApplicationsMenuPage(page);
		this.exportImportPage = new ExportImportPage(page);
		this.rangeDateRangeEndDate = page.locator(
			'[id="_com_liferay_exportimport_web_portlet_CompanyExportPortlet_endDate"]'
		);
		this.rangeDateRangeEndTime = page.locator(
			'[id="_com_liferay_exportimport_web_portlet_CompanyExportPortlet_endTime"]'
		);
		this.rangeDateRangeStartDate = page.locator(
			'[id="_com_liferay_exportimport_web_portlet_CompanyExportPortlet_startDate"]'
		);
		this.rangeDateRangeStartTime = page.locator(
			'[id="_com_liferay_exportimport_web_portlet_CompanyExportPortlet_startTime"]'
		);
		this.rangeDateRangeRadioButton = page.getByRole('radio', {
			name: 'Date Range',
		});
		this.rangeLast = page.locator(
			'[id="_com_liferay_exportimport_web_portlet_CompanyExportPortlet_last"]'
		);
		this.rangeLastRadioButton = page.getByRole('radio', {name: 'Last'});
	}

	async export(
		itemLabel: string,
		includePermissions: boolean = false,
		dateOptions?: DateOptions
	): Promise<string> {
		await this.applicationsMenuPage.goToExport();

		await this.page.getByTestId('creationMenuNewButton').nth(1).click();

		await this.page.getByLabel(itemLabel).click();

		const exportName = 'MyExport-' + getRandomString();

		await this.exportImportPage.title.fill(exportName);

		if (includePermissions) {
			await this.exportImportPage.exportPermissionsButton.click();
		}

		if (dateOptions?.endDate || dateOptions?.startDate) {
			await this.rangeDateRangeRadioButton.check();

			if (dateOptions.endDate) {
				await this.rangeDateRangeEndDate.fill(dateOptions.endDate);
			}

			if (dateOptions.endTime) {
				await this.rangeDateRangeEndTime.fill(dateOptions.endTime);
			}

			if (dateOptions.startDate) {
				await this.rangeDateRangeStartDate.fill(dateOptions.startDate);
			}

			if (dateOptions.startTime) {
				await this.rangeDateRangeStartTime.fill(dateOptions.startTime);
			}
		}
		else if (dateOptions?.rangeLast) {
			await this.rangeLastRadioButton.check();

			await this.rangeLast.selectOption(dateOptions.rangeLast);
		}

		await this.exportImportPage.exportButton.click();

		await this.page
			.getByText(exportName)
			.locator('../../..')
			.getByText('Successful')
			.waitFor();

		return await this.exportImportPage.downloadExportProcess(exportName);
	}

	async import(
		filePath: string,
		includePermissions: boolean = false
	): Promise<void> {
		await this.applicationsMenuPage.goToImport();

		await this.exportImportPage.newImportButton.click();

		await this.page.locator('input[type="file"]').setInputFiles(filePath);

		await this.exportImportPage.continueButton.click();

		if (includePermissions) {
			await this.exportImportPage.importPermissionsButton.click();
		}

		await this.exportImportPage.importButton.click();

		const fileName = path.basename(filePath);
		await this.page
			.getByText(fileName)
			.locator('../../..')
			.getByText('Successful')
			.waitFor();
	}
}

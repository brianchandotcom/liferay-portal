/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {expandSection} from '../../utils/expandSection';
import {waitForSuccessAlert} from '../../utils/waitForSuccessAlert';
import {DocumentLibraryPage} from './DocumentLibraryPage';

export class DocumentLibraryEditFilePage {
	readonly documentLibraryPage: DocumentLibraryPage;
	readonly page: Page;
	readonly backButton: Locator;
	readonly publishDateSelector: Locator;
	readonly saveButton: Locator;
	readonly selectForUpdateButton: Locator;
	readonly publishButton: Locator;
	readonly scheduleButton: Locator;
	readonly titleSelector: Locator;
	readonly permissionViewSelector: Locator;

	constructor(page: Page) {
		this.documentLibraryPage = new DocumentLibraryPage(page);
		this.page = page;
		this.backButton = page.getByRole('link', {name: 'Back'});
		this.publishButton = page.getByRole('button', {
			exact: true,
			name: 'Publish',
		});
		this.publishDateSelector = page.getByLabel('Publish Date');
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
		this.selectForUpdateButton = page.getByLabel('Upload', {exact: true});
		this.scheduleButton = page.getByRole('button', {name: 'Schedule'});
		this.titleSelector = page.getByLabel('Title');
		this.permissionViewSelector = page.getByLabel('Viewable by');
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.documentLibraryPage.goto(siteUrl);
		await this.documentLibraryPage.goToCreateNewFile();
	}

	async assertPrivateFileIconInSelectPopUp(assetType: string) {
		await this.documentLibraryPage.assertPrivateFileIcon(
			this.page.frameLocator(`iframe[title="Select ${assetType}"]`)
		);
	}

	async changeViewInItemSelector(assetType: string, viewType: string) {
		const modalIframe = this.page.frameLocator(
			`iframe[title="Select ${assetType}"]`
		);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: modalIframe.getByRole('menuitem', {name: viewType}),
			trigger: modalIframe.getByLabel(
				'Select View, Currently Selected: '
			),
		});
	}

	async publishFileEntry() {
		if (await this.saveButton.isVisible()) {
			await this.saveButton.click();
		}
		else {
			await this.publishButton.click();
		}

		await waitForSuccessAlert(this.page);
	}

	async goBack() {
		await this.backButton.click();
	}

	async goToNewFileDifferentType(type: string) {
		await this.documentLibraryPage.goto();

		await this.documentLibraryPage.goToCreateNewFileWithDifferentType(type);
	}

	async publishNewBasicFileEntry(title: string) {
		await this.goto();

		await this.titleSelector.fill(title);

		if (await this.saveButton.isVisible()) {
			await this.saveButton.click();
		}
		else {
			await this.publishButton.click();
		}
	}

	async publishNewFileWithScheduleDate(scheduleDate: string, title: string) {
		await this.goto();

		await this.titleSelector.fill(title);

		const isClosed =
			!(await this.scheduleButton.getAttribute('aria-expanded')) ||
			(await this.scheduleButton.getAttribute('aria-expanded')) ===
				'false';

		if (isClosed) {
			await this.scheduleButton.click();
		}

		await this.publishDateSelector.click();
		await this.publishDateSelector.fill(scheduleDate);
		await this.publishDateSelector.click();
		await this.publishDateSelector.press('Escape');
		await this.page
			.locator(
				'[id="_com_liferay_document_library_web_portlet_DLAdminPortlet_displayDateTime"]'
			)
			.fill('00:00');

		if (await this.saveButton.isVisible()) {
			await this.saveButton.click();
		}
		else {
			await this.publishButton.click();
		}
	}

	async publishNewFileWithoutGuestViewPermission(title: string) {
		await this.goto();

		await this.titleSelector.fill(title);
		if (await this.permissionViewSelector.isVisible()) {
			await this.permissionViewSelector.selectOption('Site Member');
		}
		else {
			await this.page.getByRole('button', {name: 'Permissions'}).click();
			await this.permissionViewSelector.selectOption('Site Member');
		}

		await this.publishButton.click();
	}

	async publishMultipleFiles(dTypeTitle: string, filePaths: string[]) {
		await this.page.getByRole('button', {name: 'Select Files'}).waitFor();
		await this.page.locator('input[type="file"]').setInputFiles(filePaths);
		await this.page.getByRole('button', {name: 'Document Type'}).click();
		await this.page.getByRole('button', {name: 'Basic Document'}).click();
		await this.page.getByRole('menuitem', {name: dTypeTitle}).click();
		await this.page.getByRole('button', {name: 'Publish'}).click();
	}

	async selectSpecificDisplayPage(displayPageName: string) {
		const displayPageFieldSet = this.page.locator(
			'#_com_liferay_document_library_web_portlet_DLAdminPortlet_display-page'
		);

		await expandSection(displayPageFieldSet);
		await displayPageFieldSet
			.getByTitle('Display Page Template Type')
			.selectOption('Specific');
		await displayPageFieldSet.getByRole('button', {name: 'Select'}).click();
		const selectDisplayPageModal = await this.page.frameLocator(
			'iframe[title*="Select Page"]'
		);
		await selectDisplayPageModal
			.getByLabel('Select ' + displayPageName)
			.click();
	}
}

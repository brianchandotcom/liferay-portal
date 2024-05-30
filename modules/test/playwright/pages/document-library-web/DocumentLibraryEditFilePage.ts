/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
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

	async goto() {
		await this.documentLibraryPage.goto();

		await this.documentLibraryPage.goToCreateNewFile();
	}

	async assertPrivateFileIconInSelectPopUp(assetType: string) {
		await this.documentLibraryPage.assertPrivateFileIcon(
			this.page.frameLocator(`iframe[title="Select ${assetType}"]`)
		);
	}

	async changeViewInItemSelctor(assetType: string, viewType: string) {
		await this.page
			.frameLocator(`iframe[title="Select ${assetType}"]`)
			.getByLabel('Select View, Currently Selected: ')
			.waitFor();
		await this.page
			.frameLocator(`iframe[title="Select ${assetType}"]`)
			.getByLabel('Select View, Currently Selected: ')
			.click();
		await this.page
			.frameLocator(`iframe[title="Select ${assetType}"]`)
			.getByRole('menuitem', {name: viewType})
			.click();
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
}

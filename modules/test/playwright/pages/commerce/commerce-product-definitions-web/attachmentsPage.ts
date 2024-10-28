/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CommerceLayoutsPage} from '../commerce-order-content-web/commerceLayoutsPage';

export class AttachmentsPage {
	readonly addFileEntryButton: Locator;
	readonly addFileEntrySubmitButton: Locator;
	readonly contentAndDataMenuItem: Locator;
	readonly documentsAndMediaMenuItem: Locator;
	readonly externalVideoShortcutMenuItem: Locator;
	readonly externalVideoShortcutURLInput: Locator;
	readonly fileUploadMenuItem: Locator;
	readonly layoutsPage: CommerceLayoutsPage;
	readonly page: Page;
	readonly renderedVideoPreviewIframe: Locator;
	readonly selectFileButton: Locator;
	readonly titleInput: Locator;
	readonly uploadedFileName: Locator;

	constructor(page: Page) {
		this.addFileEntryButton = page
			.getByTestId('creationMenuNewButton')
			.locator('visible=true');
		this.addFileEntrySubmitButton = page
			.getByTestId('addFileEntryFooter')
			.getByRole('button', {exact: true, name: 'Publish'});
		this.contentAndDataMenuItem = page
			.getByTestId('appGroup')
			.filter({hasText: 'Content & Data'});
		this.documentsAndMediaMenuItem = page
			.getByTestId('app')
			.filter({hasText: 'Documents and Media'});
		this.externalVideoShortcutMenuItem = page
			.getByTestId('dropdownMenu')
			.getByRole('menuitem', {
				exact: true,
				name: 'External Video Shortcut',
			});
		this.externalVideoShortcutURLInput = page.locator(
			'#dlVideoExternalShortcutURLInput'
		);
		this.fileUploadMenuItem = page
			.getByTestId('dropdownMenu')
			.getByRole('menuitem', {
				exact: true,
				name: 'File Upload',
			});
		this.layoutsPage = new CommerceLayoutsPage(page);
		this.page = page;
		this.renderedVideoPreviewIframe = page.getByTestId('renderedVideo');
		this.selectFileButton = page.getByTestId('selectFileButton');
		this.titleInput = page.locator(
			'#_com_liferay_document_library_web_portlet_DLAdminPortlet_title'
		);
		this.uploadedFileName = page.getByTestId('uploadedFileName');
	}

	async createExternalVideoShortcut(url: string, title: string) {
		await this.addFileEntryButton.click();
		await this.externalVideoShortcutMenuItem.click();
		await this.externalVideoShortcutURLInput.waitFor({
			state: 'attached',
		});
		await this.externalVideoShortcutURLInput.click();
		await this.externalVideoShortcutURLInput.fill(url);
		await this.titleInput.click();
		await this.titleInput.fill(title);
		await this.renderedVideoPreviewIframe.waitFor({
			state: 'attached',
		});
		await Promise.all([
			this.addFileEntrySubmitButton.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'p_p_id=com_liferay_document_library_web_portlet_DLAdminPortlet'
						)
			),
		]);
	}

	async createFileEntry(filePath: string, title: string) {
		await this.addFileEntryButton.click();
		await this.fileUploadMenuItem.click();
		await this.selectFileButton.waitFor({
			state: 'attached',
		});
		await this.selectFile(filePath);
		await this.titleInput.click();
		await this.titleInput.fill(title);
		await this.uploadedFileName.waitFor({
			state: 'attached',
		});
		await Promise.all([
			this.addFileEntrySubmitButton.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'p_p_id=com_liferay_document_library_web_portlet_DLAdminPortlet'
						)
			),
		]);
	}

	async goto() {
		await this.page.goto('/');
	}

	async goToDocumentsAndMedia() {
		await this.goto();

		if (await this.layoutsPage.closeProductMenuButton.isVisible()) {
			await this.contentAndDataMenuItem.click();
		}
		else if (await this.layoutsPage.openProductMenuButton.isVisible()) {
			await this.layoutsPage.openProductMenuButton.click();
			await this.contentAndDataMenuItem.click();
		}

		await Promise.all([
			this.documentsAndMediaMenuItem.click(),
			this.page.waitForResponse(
				(resp) =>
					resp.status() === 200 &&
					resp
						.url()
						.includes(
							'p_p_id=com_liferay_document_library_web_portlet_DLAdminPortlet'
						)
			),
		]);
	}

	async selectFile(filePath: string) {
		const fileChooserPromise = this.page.waitForEvent('filechooser');
		await this.selectFileButton.click();
		const fileChooser = await fileChooserPromise;
		await fileChooser.setFiles(filePath);
	}
}

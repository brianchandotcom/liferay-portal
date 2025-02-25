/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class UserAssociatedDataDocumentLibraryPage {
	readonly infoPanelSideBarCreatedByText: Locator;
	readonly infoPanelSideBarModifiedByText: Locator;
	readonly infoPanelSideBarOwnerText: Locator;
	readonly mediaLink: (name: string) => Locator;
	readonly page: Page;
	readonly toogleInfoPanelButtonForFiles: Locator;
	readonly toogleInfoPanelButtonForFolder: Locator;

	constructor(page: Page) {
		this.infoPanelSideBarCreatedByText = page.locator(
			'dt.sidebar-dt:has-text("Created") + dd.sidebar-dd'
		);
		this.infoPanelSideBarModifiedByText = page.locator(
			'dt.sidebar-dt:has-text("Modified") + dd.sidebar-dd'
		);
		this.infoPanelSideBarOwnerText = page
			.locator(
				'.collaborators .component-title.username, div.component-title.username a'
			)
			.first();
		this.mediaLink = (name: string) => page.getByRole('link', {name});
		this.page = page;
		this.toogleInfoPanelButtonForFiles = page.getByTestId('infoButton');
		this.toogleInfoPanelButtonForFolder = page.getByRole('button', {
			name: 'Toggle Info Panel',
		});
	}

	async checkDocumentCreator(attachment, userName: string) {
		await this.mediaLink(attachment.title).click();

		await expect(this.toogleInfoPanelButtonForFiles).toBeVisible();

		await expect(async () => {
			await this.toogleInfoPanelButtonForFiles.click();

			await expect(this.infoPanelSideBarOwnerText).toBeVisible();
		}).toPass();

		await expect(this.infoPanelSideBarOwnerText).toContainText(userName);
		await expect(this.infoPanelSideBarCreatedByText).toContainText(
			userName
		);
		await expect(this.infoPanelSideBarModifiedByText).toContainText(
			userName
		);
	}

	async checkFolderCreator(folder, userName: string) {
		await this.mediaLink(folder.name).click();

		await expect(this.toogleInfoPanelButtonForFolder).toBeVisible();

		await this.toogleInfoPanelButtonForFolder.click();

		await expect(this.infoPanelSideBarCreatedByText).toContainText(
			userName
		);
	}
}

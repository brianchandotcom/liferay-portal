/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CopyFolderModalPage {
	readonly continueButton: Locator;
	readonly duplicateDialog: Locator;
	readonly folderRadio: (folderName: string) => Locator;
	readonly keepBothOption: Locator;
	readonly modal: Locator;
	readonly navigableFolder: (folderName: string) => Locator;
	readonly page: Page;
	readonly selectButton: Locator;
	readonly space: (spaceName: string) => Locator;
	readonly spaceInModal: (spaceName: string) => Locator;

	constructor(page: Page) {
		this.page = page;
		this.modal = page.locator('.modal-content');
		this.duplicateDialog = page.getByRole('dialog', {name: 'Copy'});
		this.continueButton = this.duplicateDialog.getByRole('button', {
			name: 'Continue',
		});
		this.folderRadio = (folderName: string) => {
			return page.getByRole('radio', {
				name: `Select ${folderName}`,
			});
		};
		this.keepBothOption = this.duplicateDialog.getByLabel('Keep Both');
		this.navigableFolder = (folderName: string) => {
			return this.modal.getByText(folderName, {exact: true});
		};
		this.selectButton = page.getByRole('button', {name: 'Select'});
		this.space = (spaceName: string) => {
			return page.getByLabel(spaceName);
		};
		this.spaceInModal = (spaceName: string) => {
			return this.modal.getByLabel(spaceName);
		};
	}
}

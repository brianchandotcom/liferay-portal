/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class ModalEditObjectFolderPage {
	readonly objectFolderERCInput: Locator;
	readonly objectFolderLabelInput: Locator;
	readonly objectFolderSaveButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.objectFolderERCInput = page.locator(
			'input[name="externalReferenceCode"]'
		);
		this.objectFolderLabelInput = page.locator(
			'input[name="objectFolderLabel"]'
		);
		this.objectFolderSaveButton = page.getByText('Save');
		this.page = page;
	}

	async editObjectFolderDetails(
		objectFolderERC: string,
		objectFolderLabel: string
	) {
		await this.objectFolderLabelInput.click();
		await this.objectFolderLabelInput.fill(objectFolderLabel);
		await this.objectFolderERCInput.click();
		await this.objectFolderERCInput.fill(objectFolderERC);

		const responsePromise = this.page.waitForResponse(
			'**/object-folders/**'
		);

		await this.objectFolderSaveButton.click();

		await responsePromise;
	}
}

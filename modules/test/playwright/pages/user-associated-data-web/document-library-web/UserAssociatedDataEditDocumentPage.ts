/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class UserAssociatedDataEditDocumentPage {
	readonly documentDescription: Locator;
	readonly documentFileName: Locator;
	readonly page: Page;
	readonly publishButton: Locator;
	readonly selectFileButton: Locator;

	constructor(page: Page) {
		this.documentFileName = page.getByLabel('File Name', {exact: true});
		this.documentDescription = page.getByLabel('Description');
		this.page = page;
		this.publishButton = page.getByRole('button', {name: 'Publish'});
		this.selectFileButton = page.getByTestId('selectFileButton');
	}
}

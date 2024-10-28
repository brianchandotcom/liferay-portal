/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CommerceDNDTablePage} from '../commerceDNDTablePage';

export class CommerceAdminProductDetailsDiagramPage extends CommerceDNDTablePage {
	readonly dragAndDropImages: Locator;
	readonly page: Page;
	readonly selectFileButton: Locator;
	readonly selectFileModal: Locator;

	constructor(page: Page) {
		super(
			page,
			'#_com_liferay_commerce_product_definitions_web_internal_portlet_CPDefinitionsPortlet_fm .dnd-table'
		);
		this.dragAndDropImages = page
			.frameLocator('iframe[title="Select File"]')
			.getByText('Drag & Drop Your Images or Browse to Upload');
		this.page = page;
		this.selectFileButton = page.getByRole('button', {name: 'Select File'});
		this.selectFileModal = page.locator('.modal-content');
	}

	async goToDragAndDropImages() {
		await this.selectFileButton.click();
		await this.selectFileModal.isVisible();
	}
}

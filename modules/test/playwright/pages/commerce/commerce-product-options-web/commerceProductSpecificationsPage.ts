/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceProductSpecificationsPage {
	readonly addNewProductSpecifications: Locator;
	readonly addNewProductSpecificationsGroup: Locator;
	readonly addDescriptionSpecifications: Locator;
	readonly addDescriptionSpecificationsGroup: Locator;
	readonly createNewSpecificationsProduct: Locator;
	readonly createNewSpecificationsProductGroup: Locator;
	readonly goBack: Locator;
	readonly goToSpecificationGroup: Locator;
	readonly keyContent: Locator;
	readonly page: Page;
	readonly saveButton: Locator;
	readonly successMessagge: Locator;

	constructor(page: Page) {
		this.page = page;

		this.addNewProductSpecifications = page.getByLabel(
			'Label\n\n\t\t\t\n\t\t\t\t\n\n\t\t\t\tRequired'
		);
		this.addNewProductSpecificationsGroup =
			page.getByText('Title required');
		this.addDescriptionSpecifications = page.getByLabel(
			'Characters Maximum: 4000'
		);
		this.addDescriptionSpecificationsGroup = page.getByLabel('Description');
		this.createNewSpecificationsProduct = page.getByRole('link', {
			name: 'Add Specification Label',
		});
		this.createNewSpecificationsProductGroup = page.getByRole('link', {
			name: 'Add Specification Group',
		});
		this.goBack = page.getByRole('link', {name: 'Back'});
		this.goToSpecificationGroup = page.getByRole('link', {
			name: 'Specification Groups',
		});
		this.keyContent = page.getByLabel('Key Required');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.successMessagge = page.getByText(
			'Success:Your request completed successfully.'
		);
	}

	async waitForKey(specificationName) {
		await this.addNewProductSpecifications.fill(specificationName);
		await this.addNewProductSpecifications.waitFor();
	}
}

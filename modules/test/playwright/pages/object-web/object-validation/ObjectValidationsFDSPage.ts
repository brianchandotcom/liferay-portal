/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class ObjectValidationsFDSPage {
	readonly addObjectValidationButton: Locator;
	readonly validationTabItem: Locator;

	constructor(page: Page) {
		this.addObjectValidationButton = page.getByTitle(
			'Add Object Validation'
		);
		this.validationTabItem = page
			.getByRole('listitem')
			.filter({hasText: 'Validations'});
	}

	async goto() {
		await this.validationTabItem.click();
	}
}

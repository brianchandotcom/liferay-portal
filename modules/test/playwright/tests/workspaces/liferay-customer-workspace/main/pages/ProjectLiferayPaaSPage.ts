/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CUSTOMER_SITE_FRIENLY_URL_PATH} from '../utils/constants';

export class ProjectLiferayPaaSPage {
	readonly finishActivationButton: Locator;
	readonly heading: Locator;
	readonly page: Page;
	readonly projectNotActivatedTag: Locator;

	constructor(page: Page) {
		this.finishActivationButton = page.getByRole('button', {
			name: 'Finish Activation Icon order-',
		});
		this.heading = page.getByRole('heading', {name: 'Activation Status'});
		this.page = page;
		this.projectNotActivatedTag = page
			.locator('span')
			.filter({hasText: 'Not Activated'})
			.first();
	}

	async goto(accountExternalReferenceCode: String) {
		await this.page.goto(
			`${CUSTOMER_SITE_FRIENLY_URL_PATH}/project/#/${accountExternalReferenceCode}/liferay-paa-s`
		);
	}
}

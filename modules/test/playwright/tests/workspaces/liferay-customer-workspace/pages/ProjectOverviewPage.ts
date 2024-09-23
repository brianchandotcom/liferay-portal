/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CUSTOMER_SITE_FRIENLY_URL_PATH} from '../utils/constants';

export class ProjectOverviewPage {
	readonly heading: Locator;
	readonly paasHeading: Locator;
	readonly page: Page;
	readonly subscriptionCard: Locator;

	constructor(page: Page) {
		this.heading = page.getByRole('heading', {name: 'Subscriptions'});
		this.paasHeading = page.getByRole('heading', {name: 'Liferay PaaS'});
		this.page = page;
		this.subscriptionCard = page
			.getByText('ProductionActiveStart DateOct')
			.first();
	}

	async goto(accountExternalReferenceCode: String) {
		await this.page.goto(
			`${CUSTOMER_SITE_FRIENLY_URL_PATH}/project/#/${accountExternalReferenceCode}`
		);
	}
}

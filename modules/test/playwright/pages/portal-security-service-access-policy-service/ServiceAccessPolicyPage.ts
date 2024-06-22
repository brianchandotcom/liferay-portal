/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../utils/portletUrls';

export class ServiceAccessPolicyPage {
	readonly newButton: Locator;
	readonly successMessage: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.newButton = page.getByRole('button').filter({
			hasText: 'New',
		});
		this.successMessage = page.getByText(
			'Your request completed successfully'
		);
		this.page = page;
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.serviceAccessPolicy}`,
			{waitUntil: 'load'}
		);
	}

	async newConfiguration() {
		await this.newButton.click();
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../../utils/portletUrls';

export class SpaceSummaryPage {
	readonly page: Page;
	readonly viewAllContentLink: Locator;
	readonly viewAllFilesLink: Locator;

	constructor(page: Page) {
		this.page = page;

		this.viewAllContentLink = this.page.getByRole('link', {
			name: 'View All Content',
		});

		this.viewAllFilesLink = this.page.getByRole('link', {
			name: 'View All Files',
		});
	}

	async goto(spaceName: string) {
		await this.page.goto(PORTLET_URLS.cms);
		await this.page.getByRole('menuitem', {name: spaceName}).click();
		await this.viewAllContentLink.waitFor();
	}
}

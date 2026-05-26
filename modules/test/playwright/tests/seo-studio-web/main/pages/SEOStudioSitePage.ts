/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class SEOStudioSitePage {
	readonly page: Page;

	readonly aeoGeoLink: Locator;
	readonly contentSEOLink: Locator;
	readonly overviewLink: Locator;
	readonly settingsLink: Locator;
	readonly siteSEOLink: Locator;
	readonly toolsLink: Locator;

	constructor(page: Page) {
		this.page = page;

		this.aeoGeoLink = this.page.getByRole('link', {name: 'AEO & GEO'});
		this.contentSEOLink = this.page.getByRole('link', {
			name: 'Content SEO',
		});
		this.overviewLink = this.page.getByRole('link', {name: 'Overview'});
		this.settingsLink = this.page.getByRole('link', {name: 'Settings'});
		this.siteSEOLink = this.page.getByRole('link', {name: 'Site SEO'});
		this.toolsLink = this.page.getByRole('link', {name: 'Tools'});
	}
}

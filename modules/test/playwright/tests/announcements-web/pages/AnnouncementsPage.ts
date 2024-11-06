/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PORTLET_URLS} from '../../../utils/portletUrls';

export class AnnouncementsPage {
	readonly page: Page;

	readonly newButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.newButton = page.getByRole('link', {name: 'Add Announcement'});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.announcements}`
		);
	}

	async goToCreateNewAnnouncement() {
		await this.goto();
		await this.newButton.click();
	}
}

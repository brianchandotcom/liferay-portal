/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceThemeMiniumPage {
	readonly goToMiniumLink: (siteName: string) => Promise<Locator>;
	readonly myProfileItemMenu: Locator;
	readonly page: Page;
	readonly stickerUserNav: Locator;

	constructor(page: Page) {
		this.goToMiniumLink = async (siteName: string) => {
			return page.getByRole('link', {name: `Go to ${siteName}`});
		};
		this.page = page;
		this.myProfileItemMenu = page.getByRole('link', {name: 'My Profile'});
		this.stickerUserNav = page.locator('.sticker').first();
	}
}

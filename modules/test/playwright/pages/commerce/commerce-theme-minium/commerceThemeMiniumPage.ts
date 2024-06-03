/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceThemeMiniumPage {
	readonly page: Page;
	readonly stickerUserNav: Locator;
	readonly myProfileItemMenu: Locator;

	constructor(page: Page) {
		this.page = page;

		this.stickerUserNav = page.locator('.sticker').first();
		this.myProfileItemMenu = page.getByRole('link', {name: 'My Profile'});
	}
}

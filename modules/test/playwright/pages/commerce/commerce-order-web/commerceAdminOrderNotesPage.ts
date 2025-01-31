/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceAdminOrderNotesPage {
	readonly orderNoteContent: (content: string) => Promise<Locator>;
	readonly page: Page;

	constructor(page: Page) {
		this.orderNoteContent = async (content: string) => {
			return page.getByText(content);
		};
		this.page = page;
	}
}

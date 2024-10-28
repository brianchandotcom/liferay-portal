/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class CommerceAccountManagementPage {
	readonly accountsTableRowLink: (accountId: number | string) => Locator;
	readonly channelDefaultsLink: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.accountsTableRowLink = (accountId: number | string) =>
			page.getByRole('link', {exact: true, name: String(accountId)});
		this.channelDefaultsLink = page.getByRole('link', {
			exact: true,
			name: 'Channel Defaults',
		});
		this.page = page;
	}
}

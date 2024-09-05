/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrameLocator, Locator, Page} from '@playwright/test';

export class CommerceAdminProductDetailsVisibilityPage {
	readonly page: Page;
	readonly selectAccountGroupsButton: Locator;
	readonly selectAccountGroupsFrame: FrameLocator;
	readonly selectAccountGroupsRow: (
		accountGroupName: string
	) => Promise<Locator>;
	readonly selectAccountGroupsTitle: Locator;

	constructor(page: Page) {
		this.page = page;
		this.selectAccountGroupsButton = page.getByLabel(
			'Add Account Group Relation to'
		);
		this.selectAccountGroupsFrame = page.frameLocator(
			'iframe[title="Select Account Groups"]'
		);
		this.selectAccountGroupsRow = async (accountGroupName: string) => {
			return this.selectAccountGroupsFrame.getByRole('cell', {
				exact: true,
				name: accountGroupName,
			});
		};
		this.selectAccountGroupsTitle = page.getByRole('heading', {
			name: 'Select Account Groups',
		});
	}
}

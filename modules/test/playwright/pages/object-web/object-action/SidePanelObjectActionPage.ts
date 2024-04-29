/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class SidePanelObjectActionPage {
	readonly actionBuilderTab: Locator;

	constructor(page: Page) {
		this.actionBuilderTab = page
			.frameLocator('iframe')
			.getByRole('tab', {name: 'Action Builder'});
	}

	async openActionBuilderTab() {
		await this.actionBuilderTab.click();
	}
}

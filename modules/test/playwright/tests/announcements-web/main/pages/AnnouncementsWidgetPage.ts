/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

type DistributionScope = 'Organizations' | 'Roles' | 'Sites' | 'User Groups';

export class AnnouncementsWidgetPage {
	readonly page: Page;

	readonly addAnnouncementButton: Locator;
	readonly distributionScopeSelect: Locator;
	readonly distributionScopeSelectOptions: Locator;

	constructor(page: Page) {
		this.page = page;

		this.addAnnouncementButton = this.page.getByRole('button', {
			name: 'Add Announcement',
		});
		this.distributionScopeSelect =
			this.page.getByLabel('Distribution Scope');
		this.distributionScopeSelectOptions = this.page.locator(
			'#_com_liferay_announcements_web_portlet_AnnouncementsPortlet_distributionScope > option'
		);
	}

	getDistributionScopeSelectOptions(distributionScope: DistributionScope) {
		return this.page.locator(
			`#_com_liferay_announcements_web_portlet_AnnouncementsPortlet_distributionScope > optgroup[label='${distributionScope}'] > option`
		);
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class SiteConfigurationDetailsPage {
	readonly allowManualMembershipManagementToggle: Locator;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.allowManualMembershipManagementToggle = page.getByLabel(
			'Allow Manual Membership Management'
		);
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class ChangeTrackingInstanceSettingsPage {
	readonly instanceSettingsPage: InstanceSettingsPage;

	constructor(page: Page) {
		this.instanceSettingsPage = new InstanceSettingsPage(page);
	}

	async goto(configuration) {
		await this.instanceSettingsPage.goToInstanceSetting(
			'Publications',
			configuration
		);
	}
}

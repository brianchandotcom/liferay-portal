/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class SiteStagingPage {
	readonly blogsCheckbox: Locator;
	readonly localStagingCheckbox: Locator;
	readonly page: Page;
	readonly publishToLiveButton: Locator;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.blogsCheckbox = page.getByTestId(
			'staged--staged-portlet_com_liferay_blogs_web_portlet_BlogsAdminPortlet--'
		);
		this.localStagingCheckbox = page.getByTestId('stagingType_local');
		this.page = page;
		this.publishToLiveButton = page.getByRole('button', {
			name: 'Publish to Live',
		});
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}
}

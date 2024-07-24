/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {liferayConfig} from '../../../../../liferay.config';

export class MDFRequestListPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`${liferayConfig.environment.baseUrl}/web${siteUrl}/marketing/mdf-requests`,
			{
				waitUntil: 'networkidle',
			}
		);
	}
}

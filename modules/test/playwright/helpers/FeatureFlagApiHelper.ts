/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {liferayConfig} from '../liferay.config';

export class FeatureFlagApiHelper {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async updateFeatureFlag(key: string, enabled: boolean) {
		await this.page.goto(liferayConfig.environment.baseUrl);
		await this.page.evaluate(
			({enabled, key}) =>
				Liferay.Util.fetch(
					'/o/com-liferay-feature-flag-web/set-enabled',
					{
						body: Liferay.Util.objectToFormData({
							companyId: Liferay.ThemeDisplay.getCompanyId(),
							enabled: enabled.toString(),
							key,
						}),
						method: 'POST',
					}
				),
			{
				enabled,
				key,
			}
		);
	}
}

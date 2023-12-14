/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export class FeatureFlagApiHelper {
	constructor(page) {
		this.page = page;
	}

	async updateFeatureFlag(key, enabled) {
		await this.page.goto('/');
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

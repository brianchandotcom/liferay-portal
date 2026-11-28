/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {BaseApiHelpers} from './ApiHelpers';

type TIsEnabledResult = {
	dependentFeatureFlags: TFeatureFlagResult[];
	featureFlag: TFeatureFlagResult;
};

type TFeatureFlagResult = {
	companyId: number;
	dependenciesFulfilled: boolean;
	dependencyKeys: string[];
	description: string;
	enabled: boolean;
	featureFlagType: 'BETA' | 'DEPRECATION' | 'DEV' | 'RELEASE';
	key: string;
	title: string;
};

export class FeatureFlagApiHelper {
	readonly apiHelpers: BaseApiHelpers;

	constructor(apiHelpers: BaseApiHelpers) {
		this.apiHelpers = apiHelpers;
	}

	async isFeatureFlagEnabled(key: string): Promise<TIsEnabledResult> {
		return await this.apiHelpers.page.evaluate(
			async ({key}) =>
				new Promise((resolve, reject) => {
					Liferay.Util.fetch(
						'/o/com-liferay-feature-flag-web/is-enabled',
						{
							body: Liferay.Util.objectToFormData({
								companyId: Liferay.ThemeDisplay.getCompanyId(),
								key,
							}),
							method: 'POST',
						}
					)
						.then(async (response) => {
							if (!response?.ok) {
								if (response?.status === 404) {
									reject(
										`Feature flag "${key}" is not available`
									);
								}
								else if (response?.status === 500) {
									reject('An unexpected error has occurred');
								}
								else {
									reject(
										`Unable to fetch feature flag "${key}"`
									);
								}
							}

							const json = JSON.parse(await response.text());

							resolve({
								dependentFeatureFlags:
									json.dependenciesFulfilled,
								featureFlag: json.featureFlag,
							});
						})
						.catch(reject);
				}),
			{
				key,
			}
		);
	}

	async updateFeatureFlag(key: string, enabled: boolean) {
		await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}com-liferay-feature-flag-web/set-enabled`,
			{
				headers: {
					Authorization: this.apiHelpers.getAuthorizationHeader(),
				},
				multipart: {enabled: String(enabled), key},
			}
		);
	}
}

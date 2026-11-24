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
		await this._openPortalPage();

		const response = await this.apiHelpers.postResponse(
			`${this.apiHelpers.baseUrl}com-liferay-feature-flag-web/is-enabled`,
			{
				headers: await this.apiHelpers.getCSRFTokenHeader(),
				multipart: {key},
			}
		);

		if (response.status() === 404) {
			throw new Error(`Feature flag "${key}" is not available`);
		}
		else if (response.status() === 500) {
			throw new Error('An unexpected error has occurred');
		}
		else if (!response.ok()) {
			throw new Error(`Unable to fetch feature flag "${key}"`);
		}

		const json = await response.json();

		return {
			dependentFeatureFlags: json.dependenciesFulfilled,
			featureFlag: json.featureFlag,
		};
	}

	async updateFeatureFlag(key: string, enabled: boolean) {
		await this._openPortalPage();

		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}com-liferay-feature-flag-web/set-enabled`,
			{
				headers: await this.apiHelpers.getCSRFTokenHeader(),
				multipart: {enabled: String(enabled), key},
			}
		);
	}

	private async _openPortalPage() {
		const page = this.apiHelpers.page;

		if (!page) {
			return;
		}

		const portalURL = this.apiHelpers.baseUrl.replace(/\/o\/$/, '');

		if (!page.url().startsWith(portalURL)) {
			await page.goto(portalURL);
		}
	}
}

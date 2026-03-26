/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

export class CookiesApiHelper {
	readonly apiHelpers: ApiHelpers | DataApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'cookies/v1.0/';
	}

	async deleteCookiesConsentPreferenceByName(name: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}cookies-consent-preferences/by-name/${name}`
		);
	}

	async deleteCookiesConsentPreferences() {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}cookies-consent-preferences`
		);
	}

	async getCookiesConsentPreferenceByName(name: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}cookies-consent-preferences/by-name/${name}`
		);
	}

	async putCookiesConsentPreference(cookiesConsentPreference) {
		return this.apiHelpers.put(
			`${this.apiHelpers.baseUrl}${this.basePath}cookies-consent-preferences`,
			{data: cookiesConsentPreference}
		);
	}
}

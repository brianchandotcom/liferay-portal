/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

export class HeadlessAdminUserApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-admin-user/v1.0/';
	}

	async getSiteByFriendlyUrlPath(friendlyUrlPath: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/by-friendly-url-path/${friendlyUrlPath}`
		);
	}
}

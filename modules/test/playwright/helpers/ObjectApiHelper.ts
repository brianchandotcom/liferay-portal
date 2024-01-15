/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

export class ObjectApiHelper {
	readonly apiHelpers: ApiHelpers;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
	}

	async getObjectDefinitionObjectEntries(applicationName: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${applicationName}/`
		);
	}

	async getObjectDefinitionObjectEntriesByScope(
		applicationName: string,
		scopeKey: string
	) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${applicationName}/scopes/${scopeKey}`
		);
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

export class HeadlessAssetLibraryApiHelper {
	apiHelpers: ApiHelpers;
	basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-asset-library/v1.0';
	}

	async getAssetLibrariesPage() {
		const response = await this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/asset-libraries`
		);

		return response?.items;
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers} from './ApiHelpers';

export class DynamicDataMappingApiHelper {
	readonly apiHelpers: ApiHelpers;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
	}

	/**
	 * Verify when the evaluate on DDM concludes.
	 *
	 * @param page the page where the evaluate call happens.
	 */

	async waitForDDMEvaluate(page: Page) {
		await page.waitForResponse(
			`${this.apiHelpers.baseUrl}dynamic-data-mapping-form-context-provider/`
		);
	}
}

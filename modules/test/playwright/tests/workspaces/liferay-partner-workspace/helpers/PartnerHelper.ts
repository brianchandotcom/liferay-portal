/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers} from '../../../../helpers/ApiHelpers';

export class PartnerHelper {
	readonly apiHelpers: ApiHelpers;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
	}

	async createMDFRequest(data) {
		try {
			const mdfRequest = await this.apiHelpers.post('/o/c/mdfrequests', {
				data,
			});

			return mdfRequest;
		}
		catch (error) {
			console.error('Error when trying to create an MDF Request', error);
			throw error;
		}
	}
}

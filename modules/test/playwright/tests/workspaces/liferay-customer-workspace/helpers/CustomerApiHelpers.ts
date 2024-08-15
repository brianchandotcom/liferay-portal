/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers} from '../../../../helpers/ApiHelpers';

export class CustomerApiHelpers {
	readonly apiHelpers: ApiHelpers;
	readonly page: Page;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
		this.page = page;
	}

	async getAccountFlag(accountExternalReferenceCode: string) {
		const accountFlagsResponse = await this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}/c/accountflags?filter=accountKey eq '${accountExternalReferenceCode}'`
		);

		return accountFlagsResponse?.items?.at(0);
	}
}

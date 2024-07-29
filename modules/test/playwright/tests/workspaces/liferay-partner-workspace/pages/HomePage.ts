/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {PartnerHelper} from '../helpers/PartnerHelper';

export class HomePage {
	readonly page: Page;
	readonly partnerHelper: PartnerHelper;
	readonly site: Site;

	constructor(partnerHelper) {
		this.page = partnerHelper.page;
		this.partnerHelper = partnerHelper;
		this.site = partnerHelper.site;
	}

	async goto() {
		await this.page.goto(
			`/web${this.partnerHelper.site.friendlyUrlPath}/home`
		);
	}
}

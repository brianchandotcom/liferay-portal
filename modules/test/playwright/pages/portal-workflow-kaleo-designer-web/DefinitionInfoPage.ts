/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class DefinitionInfoPage {
	readonly revisionHistoryTabButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.revisionHistoryTabButton = page.getByText('Revision History');
		this.page = page;
	}

	getDateAndUserFromVersion(date: string, user: string) {
		return this.page.getByText(`${date} by ${user}`);
	}

	getVersionLabel(versionNumber: string) {
		return this.page.getByText(`Version ${versionNumber}`);
	}
}

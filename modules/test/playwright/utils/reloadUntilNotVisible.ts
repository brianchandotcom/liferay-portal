/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {reloadUntil} from './reloadUntil';

interface Parameters {
	beforeReload?: () => Promise<void>;

	maxAttempts?: number;
	myLocator: Locator;
	page: Page;
}

export async function reloadUntilNotVisible({
	beforeReload,
	maxAttempts = 5,
	myLocator,
	page,
}: Parameters) {
	await reloadUntil({
		beforeReload,
		condition: async (element) => await element.isHidden(),

		maxAttempts,
		myLocator,
		page,
	});
}

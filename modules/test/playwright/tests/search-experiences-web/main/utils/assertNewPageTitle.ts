/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect} from '@playwright/test';

export async function assertNewPageTitle(context: any, title: string) {
	const pagePromise = context.waitForEvent('page');

	const newTab = await pagePromise;

	await newTab.waitForLoadState();

	await expect(newTab).toHaveTitle(new RegExp(title));

	await newTab.close();
}

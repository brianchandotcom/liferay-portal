/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {ApiHelpers} from '../../../helpers/ApiHelpers';

export async function waitForHeadlessBuilderReady(
	apiHelpers: ApiHelpers,
	page: Page
) {
	for (const endpoint of [
		'applications',
		'endpoints',
		'filters',
		'properties',
		'schemas',
		'sorts',
	]) {
		await expect
			.poll(async () =>
				(
					await page.request.get(`/o/headless-builder/${endpoint}`, {
						headers: await apiHelpers.getHeader(),
					})
				).status()
			)
			.toBe(200);
	}
}

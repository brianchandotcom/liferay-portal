/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect} from '@playwright/test';

export async function showUI({
	autoClick,
	target,
	trigger,
}: {
	autoClick?: Boolean;
	target: Locator;
	trigger: Locator;
}) {
	const timeout = 100;

	await expect(async () => {
		await trigger.click();
		await expect(target).toBeVisible({timeout});

		if (autoClick && (await target.isVisible({timeout}))) {
			await target.click();
		}
	}).toPass();
}

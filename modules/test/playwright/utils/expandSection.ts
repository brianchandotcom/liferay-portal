/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, expect} from '@playwright/test';

export async function expandSection(trigger: Locator) {
	const isExpanded = await trigger.evaluate(
		(element) => element.getAttribute('aria-expanded') === 'true'
	);

	if (!isExpanded) {
		await trigger.click();
	}

	await expect(trigger).toHaveAttribute('aria-expanded', 'true');
}

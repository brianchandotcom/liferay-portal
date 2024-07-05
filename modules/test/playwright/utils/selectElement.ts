/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator} from '@playwright/test';

export async function selectElement(target: Locator) {
	const isSelected = await target.evaluate(
		(element) => element.getAttribute('aria-selected') === 'true'
	);

	if (!isSelected) {
		await target.click();
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator} from '@playwright/test';

interface asyncFilter {
	array: Locator[];
	predicate: (value: Locator) => Promise<boolean>;
}

export async function asyncFilter({array, predicate}: asyncFilter) {
	const results = await Promise.all(array.map(predicate));

	return array.filter((_, index) => results[index]);
}

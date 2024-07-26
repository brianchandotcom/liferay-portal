/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator} from '@playwright/test';

export default async function getSelectOptionLabels(
	select: Locator
): Promise<Array<string>> {
	return await select.evaluate((element: HTMLSelectElement) =>
		Array.from(element.options).map((options) => options.label)
	);
}

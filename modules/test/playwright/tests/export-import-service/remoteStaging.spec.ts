/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

export const test = mergeTests(

);

test(
	'Check Web contents can be published via their portlet using remote staging',
	{tag: '@LPS-81950'},
	async ({

	}) => {

	}
);

/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {checkTypes} from '../util';

export function lte(value: any, expected: any): boolean {
	checkTypes(value, ['number', 'string'], `Operator 'lte' value`);

	return value <= expected;
}

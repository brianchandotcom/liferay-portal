/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {checkTypes} from '../util';

export function gt<T extends number | string>(value: T, expected: T): boolean {
	checkTypes(value, ['number', 'string'], `Operator 'gt' value`);

	return value > expected;
}

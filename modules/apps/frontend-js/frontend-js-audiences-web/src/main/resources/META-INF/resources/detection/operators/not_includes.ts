/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {checkTypes} from '../util';

export function notIncludes(
	value: Set<string> | string,
	expected: any
): boolean {
	checkTypes(value, ['string', 'Set'], `Operator 'not_includes' value`);

	if (value instanceof Set) {
		return !value.has(expected);
	}

	return !value.includes(expected);
}

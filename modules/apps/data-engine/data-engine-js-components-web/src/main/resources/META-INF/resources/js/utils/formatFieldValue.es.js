/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {formatDate, parseDate} from './date.es';

export default function formatFieldValue(
	{dataType, symbols, value},
	preserveValue,
	nextEditingLanguageId,
	prevEditingLanguageId
) {
	if (dataType === 'double') {
		return String(value).replace(symbols.decimalSymbol, '.');
	}

	if (
		dataType === 'date' &&
		value &&
		value.indexOf('_') === -1 &&
		nextEditingLanguageId !== prevEditingLanguageId &&
		preserveValue
	) {
		const date = parseDate(prevEditingLanguageId, value);

		return formatDate(date, nextEditingLanguageId);
	}

	return value;
}

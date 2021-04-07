/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {formatDate, parseDate} from './date.es';

export default (
	{dataType, symbols, value},
	preserveValue,
	nextEditingLanguageId,
	prevEditingLanguageId
) => {
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
};

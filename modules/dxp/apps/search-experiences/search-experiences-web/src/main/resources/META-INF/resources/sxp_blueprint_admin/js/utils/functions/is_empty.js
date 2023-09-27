/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {INPUT_TYPES} from '../types/inputTypes';
import isDefined from './is_defined';

/**
 * Checks if a value is blank. For example: `''` or `{}` or `[]`.
 * For fieldMapping and fieldMappingList, checks if fields are blank.
 * @param {*} value The value to check.
 * @param {*} type Input type (optional).
 * @return {boolean}
 */
export default function isEmpty(value, type = '') {
	if (typeof value === 'string' && value === '') {
		return true;
	}

	if (typeof value === 'object' && !Object.keys(value).length) {
		return true;
	}

	if (type === INPUT_TYPES.FIELD_MAPPING) {
		return !value.field;
	}

	if (type === INPUT_TYPES.FIELD_MAPPING_LIST) {
		return value.every(({field}) => !field);
	}

	return !isDefined(value);
}

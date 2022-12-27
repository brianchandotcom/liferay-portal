/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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

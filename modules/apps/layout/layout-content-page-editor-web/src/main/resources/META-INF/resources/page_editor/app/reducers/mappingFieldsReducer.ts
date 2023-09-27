/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import addMappingFields, {
	MappingFieldFieldSet,
} from '../actions/addMappingFields';
import {ADD_MAPPING_FIELDS} from '../actions/types';

export const INITIAL_STATE: Record<string, MappingFieldFieldSet[]> = {};

export default function mappedInfoItemsReducer(
	mappingFields = INITIAL_STATE,
	action: ReturnType<typeof addMappingFields>
) {
	switch (action.type) {
		case ADD_MAPPING_FIELDS:
			return {...mappingFields, [action.key]: action.fields};

		default:
			return mappingFields;
	}
}

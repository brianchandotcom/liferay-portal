/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MappingFieldSet} from '../../types/MappingField';
import {ADD_MAPPING_FIELDS} from './types';

export default function addMappingFields({
	fields,
	key,
}: {
	fields: MappingFieldSet[];
	key: string;
}) {
	return {
		fields,
		key,
		type: ADD_MAPPING_FIELDS,
	} as const;
}

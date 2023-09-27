/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ADD_MAPPING_FIELDS} from './types';

export interface MappingFieldFieldSet {
	fields: MappingField[];
	label?: string;
}

export interface MappingField {
	key: string;
	label: string;
	name: string;
	required: boolean;
	type: string;
	typeLabel: string;
}

export default function addMappingFields({
	fields,
	key,
}: {
	fields: MappingFieldFieldSet[];
	key: string;
}) {
	return {
		fields,
		key,
		type: ADD_MAPPING_FIELDS,
	} as const;
}

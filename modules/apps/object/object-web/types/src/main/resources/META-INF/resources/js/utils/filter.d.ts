/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MultiSelectItem} from '@liferay/object-js-components-web';
export declare function getCheckedWorkflowStatusItems(
	itemValues: LabelValueObject[],
	setEditingFilterType: () => number[] | string[] | null
): MultiSelectItem[];
export declare function getCheckedListTypeEntries(
	itemValues: ListTypeEntry[],
	setEditingFilterType: () => number[] | string[] | null
): MultiSelectItem[];
export declare function getSystemObjectFieldLabelFromObjectEntry(
	titleFieldName: string,
	entry: ObjectEntry,
	itemObject: {
		['value']: string;
	}
): {
	label: string;
	value: string;
};
export declare function getCheckedObjectRelationshipItems(
	relatedEntries: ObjectEntry[],
	titleFieldName: string,
	systemField: boolean,
	systemObject: boolean,
	setEditingFilterType: () => number[] | string[] | null
): MultiSelectItem[];

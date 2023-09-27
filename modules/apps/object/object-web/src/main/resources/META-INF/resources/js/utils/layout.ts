/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	TObjectField,
	TObjectLayoutRow,
	TObjectRelationship,
} from '../components/Layout/types';

export function findObjectLayoutRowIndex(
	objectLayoutRows: TObjectLayoutRow[],
	fieldSize: number
): number {
	let objectLayoutRowIndex = -1;

	objectLayoutRows.some(({objectLayoutColumns}, rowIndex) => {
		const totalSize = objectLayoutColumns.reduce((sum, cur) => {
			return sum + cur.size;
		}, 0);

		// Find the index where the field can be dropped based on its size

		if (fieldSize + totalSize <= 12) {
			objectLayoutRowIndex = rowIndex;

			return true;
		}

		return false;
	});

	return objectLayoutRowIndex;
}

export function findObjectFieldIndexById(
	objectFields: TObjectField[] | TObjectRelationship[],
	objectFieldId: number
): number {
	const objIds = objectFields.map(({id}) => id);
	const objectIndex = objIds.indexOf(objectFieldId);

	return objectIndex;
}

export function findObjectFieldIndexByName(
	objectFields: TObjectField[] | TObjectRelationship[],
	objectFieldName: string
): number {
	const objIds = objectFields.map(({name}) => name);
	const objectIndex = objIds.indexOf(objectFieldName);

	return objectIndex;
}

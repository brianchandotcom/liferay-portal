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

import {TObjectLayoutRow} from '../components/layout/types';

type TFindObjectLayoutRowIndex = (
	objectLayoutRows: TObjectLayoutRow[],
	size: number
) => number;

export const findObjectLayoutRowIndex: TFindObjectLayoutRowIndex = (
	objectLayoutRows,
	fieldSize
) => {
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
};

type TFindObjectFieldIndex = (object: any[], objectId: number) => number;

export const findObjectFieldIndex: TFindObjectFieldIndex = (
	objectFields,
	objectFieldId
) => {
	const objIds = objectFields.map(({id}) => id);
	const objectIndex = objIds.indexOf(objectFieldId);

	return objectIndex;
};

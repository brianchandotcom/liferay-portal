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

import {
	TObjectField,
	TObjectLayoutRow,
	TObjectRelationship,
} from '../components/Layout/types';
export declare function findObjectLayoutRowIndex(
	objectLayoutRows: TObjectLayoutRow[],
	fieldSize: number
): number;
export declare function findObjectFieldIndexById(
	objectFields: TObjectField[] | TObjectRelationship[],
	objectFieldId: number
): number;
export declare function findObjectFieldIndexByName(
	objectFields: TObjectField[] | TObjectRelationship[],
	objectFieldName: string
): number;

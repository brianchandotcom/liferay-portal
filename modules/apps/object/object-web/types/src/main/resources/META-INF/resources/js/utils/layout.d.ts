/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IItemsActions} from '../..';
interface IField {
	fieldName: string | [];
	label: string;
	mapData: Function;
}
interface ISchema {
	fields: Array<IField>;
}
declare const Table: ({
	items,
	itemsActions,
	schema,
	style,
}: {
	items: Array<any>;
	itemsActions: Array<IItemsActions>;
	schema: ISchema;
	style: string;
}) => JSX.Element;
export default Table;

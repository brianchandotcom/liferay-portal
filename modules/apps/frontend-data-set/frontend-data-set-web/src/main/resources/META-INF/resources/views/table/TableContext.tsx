/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createContext} from 'react';

interface ITableContext {
	columnNames: Array<string>;
	draggingAllowed: boolean;
	draggingColumnName: null | string;
	isFixed: boolean;
	resizeColumn: (name: string, width: number) => void;
	tableWidth: number;
	updateDraggingAllowed: (value: boolean) => void;
	updateDraggingColumnName: (value: null | string) => void;
	updateTableWidth: (value: number) => void;
}

const TableContext = createContext({
	columnNames: [],
	draggingAllowed: true,
	draggingColumnName: null,
	isFixed: false,
	resizeColumn: () => {},
	tableWidth: 0,
	updateDraggingAllowed: () => {},
	updateDraggingColumnName: () => {},
	updateTableWidth: () => {},
} as ITableContext);

export default TableContext;

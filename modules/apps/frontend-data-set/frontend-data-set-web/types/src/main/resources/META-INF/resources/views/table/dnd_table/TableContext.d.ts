/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

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
declare const TableContext: import('react').Context<ITableContext>;
export default TableContext;

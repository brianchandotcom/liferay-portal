/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useMemo, useState} from 'react';

import ViewsContext, {
	IViewsContext,
	TViewsContextDispatch,
} from '../../ViewsContext';

// @ts-ignore

import {VIEWS_ACTION_TYPES} from '../../viewsReducer';
import TableContext from './TableContext';

function TableContextProvider({
	children,
	columnNames,
}: {
	children: React.ReactNode;
	columnNames: Array<string>;
}) {
	const [{modifiedFields}, viewsDispatch]: [
		IViewsContext,
		TViewsContextDispatch,
	] = useContext(ViewsContext);

	const [tableWidth, setTableWidth] = useState<number>(0);
	const [draggingColumnName, setDraggingColumnName] = useState<null | string>(
		null
	);
	const [draggingAllowed, setDraggingAllowed] = useState(true);

	const isFixed = useMemo(() => {
		const allRegistered = columnNames.every(
			(name) => !!modifiedFields[name]
		);

		return allRegistered;
	}, [modifiedFields, columnNames]);

	const resizeColumn = (name: string, width: number) => {
		if (!isFixed) {
			return;
		}

		const resizedColumn = modifiedFields[name];

		const isColumnReducing = resizedColumn.width > width;

		let totalWidth = 0;

		Object.values(modifiedFields).forEach((modifiedField: any) => {
			totalWidth += modifiedField.width;
		});

		const nextColumnName = columnNames[columnNames.indexOf(name) + 1];

		const nextColumn = modifiedFields[nextColumnName];

		const columnsAreShorterThanContainer = totalWidth < tableWidth;

		if (
			(isColumnReducing &&
				columnsAreShorterThanContainer &&
				!nextColumn?.resizable) ||
			width < 40
		) {
			setDraggingAllowed(false);

			return;
		}

		setDraggingAllowed(true);

		if (isColumnReducing && columnsAreShorterThanContainer) {
			viewsDispatch({
				type: VIEWS_ACTION_TYPES.UPDATE_FIELD,
				value: {
					name: nextColumnName,
					width: nextColumn.width + resizedColumn.width - width,
				},
			});
		}

		viewsDispatch({
			type: VIEWS_ACTION_TYPES.UPDATE_FIELD,
			value: {
				name,
				width,
			},
		});
	};

	return (
		<TableContext.Provider
			value={{
				columnNames,
				draggingAllowed,
				draggingColumnName,
				isFixed,
				resizeColumn,
				tableWidth,
				updateDraggingAllowed: setDraggingAllowed,
				updateDraggingColumnName: setDraggingColumnName,
				updateTableWidth: setTableWidth,
			}}
		>
			{children}
		</TableContext.Provider>
	);
}

export default TableContextProvider;

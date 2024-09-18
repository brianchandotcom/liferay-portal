/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {throttle} from 'frontend-js-web';
import React, {useContext, useEffect, useMemo, useRef} from 'react';

import ViewsContext, {
	IViewsContext,
	TViewsContextDispatch,
} from '../../ViewsContext';
import getCellColumnClassName from '../../utils/getCellColumnClassName';

// @ts-ignore

import {VIEWS_ACTION_TYPES} from '../../viewsReducer';
import TableContext from './TableContext';

const Cell = ({
	children,
	className,
	columnName,
	defaultWidth = 'auto',
	heading = false,
	resizable = false,
}: {
	children?: React.ReactNode;
	className?: string;
	columnName: string;
	defaultWidth?: string | number;
	heading?: boolean;
	resizable?: boolean;
}) => {
	const {
		draggingAllowed,
		draggingColumnName,
		isFixed,
		resizeColumn,
		updateDraggingAllowed,
		updateDraggingColumnName,
	} = useContext(TableContext);

	const [{modifiedFields}, viewsDispatch]: [
		IViewsContext,
		TViewsContextDispatch,
	] = useContext(ViewsContext);

	const cellRef = useRef<HTMLTableCellElement>(null);
	const clientXRef = useRef({current: null});

	useEffect(() => {
		if (columnName && heading && !isFixed && cellRef.current) {
			const boundingClientRect = cellRef.current.getBoundingClientRect();

			viewsDispatch({
				type: VIEWS_ACTION_TYPES.UPDATE_FIELD,
				value: {
					name: columnName,
					resizable,
					width: boundingClientRect.width,
				},
			});
		}
	}, [columnName, isFixed, heading, resizable, viewsDispatch]);

	const handleDrag = useMemo(() => {
		return throttle((event) => {
			if (event.clientX === clientXRef.current || !cellRef.current) {
				return;
			}

			updateDraggingColumnName(columnName);

			clientXRef.current = event.clientX;

			const {x: headerCellX} = cellRef.current.getClientRects()[0];
			const newWidth = event.clientX - headerCellX;

			resizeColumn(columnName, newWidth);
		}, 20);
	}, [columnName, resizeColumn, updateDraggingColumnName]);

	function initializeDrag() {
		window.addEventListener('mousemove', handleDrag);
		window.addEventListener(
			'mouseup',
			() => {
				updateDraggingAllowed(true);
				updateDraggingColumnName(null);
				window.removeEventListener('mousemove', handleDrag);
			},
			{once: true}
		);
	}

	const width = useMemo(() => {
		const columnDetails = modifiedFields[columnName];

		return columnDetails && isFixed && columnDetails.width;
	}, [isFixed, modifiedFields, columnName]);

	const content = (
		<>
			{children}

			{resizable && (
				<span
					className={classNames('dnd-th-resizer', {
						'is-active': columnName === draggingColumnName,
						'is-allowed': draggingAllowed,
					})}
					onMouseDown={initializeDrag}
				/>
			)}
		</>
	);

	return (
		<div
			className={classNames(
				heading ? 'dnd-th' : 'dnd-td',
				getCellColumnClassName(columnName),
				className
			)}
			ref={cellRef}
			style={{
				width: width ?? defaultWidth,
			}}
		>
			{content}
		</div>
	);
};

export default Cell;

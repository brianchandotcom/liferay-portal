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

import ClayButton from '@clayui/button';
import classNames from 'classnames';
import React, {useContext, useMemo} from 'react';

import {LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS} from '../../config/constants/layoutDataItemDefaultConfigurations';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {useSelector} from '../../store/index';
import {ResizingContext} from './RowWithControls';

/**
 * Retrieves necessary data from the current and next column.
 *
 * @param {!Object} options
 * @param {!Object} options.item
 * @param {!Object} options.layoutData
 *
 * @returns {!Object}
 */
function getColumnInfo({item, layoutData}) {
	const rowColumns = layoutData.items[item.parentId].children;
	const colIndex = rowColumns.indexOf(item.itemId);
	const nextColumnIndex = colIndex + 1;
	const currentColumn = item;
	const currentColumnConfig = currentColumn.config;
	const nextColumn = {...layoutData.items[rowColumns[nextColumnIndex]]};
	const nextColumnConfig =
		typeof nextColumn === 'object' && Object.keys(nextColumn).length
			? nextColumn.config
			: {};

	return {
		colIndex,
		currentColumn,
		currentColumnConfig,
		isLastColumn: rowColumns.indexOf(item.itemId) === rowColumns.length - 1,
		nextColumn: nextColumn ? nextColumn : {},
		nextColumnConfig: nextColumn ? nextColumnConfig : {},
		nextColumnIndex: colIndex + 1,
		rowColumns
	};
}

const Column = React.forwardRef(
	({children, className, item, ...props}, ref) => {
		const {
			config: {
				size = LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS[
					LAYOUT_DATA_ITEM_TYPES.column
				].size
			}
		} = item;

		const layoutData = useSelector(state => state.layoutData);

		const {onResizeEnd, onResizeStart, onResizing} = useContext(
			ResizingContext
		);

		const columnInfo = useMemo(() => getColumnInfo({item, layoutData}), [
			item,
			layoutData
		]);

		const onMouseMoveInWindow = event => {
			event.preventDefault();

			onResizing(event, columnInfo);
		};

		const onMouseDownInWindow = event => {
			onResizeStart(event);

			window.addEventListener('mouseup', onMouseUpInWindow);
			window.addEventListener('mousemove', onMouseMoveInWindow);
		};

		const onMouseUpInWindow = event => {
			onResizeEnd(event);

			window.removeEventListener('mouseup', onMouseUpInWindow);
			window.removeEventListener('mousemove', onMouseMoveInWindow);
		};

		const resizeHandler = (
			<div>
				{children}
				<ClayButton
					className="page-editor__col-resizer"
					onMouseDown={onMouseDownInWindow}
				/>
			</div>
		);

		return (
			<>
				<div
					className={classNames(className, 'col', {
						[`col-${size}`]: size
					})}
					ref={ref}
					{...props}
				>
					{!columnInfo.isLastColumn ? resizeHandler : children}
				</div>
			</>
		);
	}
);

export default Column;

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

import classNames from 'classnames';
import React, {useContext, useRef, useState} from 'react';

import updateColSize from '../../actions/updateColSize';
import {LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS} from '../../config/constants/layoutDataItemDefaultConfigurations';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {ConfigContext} from '../../config/index';
import {useDispatch, useSelector} from '../../store/index';
import resizeColumns from '../../thunks/resizeColumns';
import ColumnOverlayGrid from './ColumnOverlayGrid';
import ResizingContext from './ResizingContext';

const TOTAL_NUMBER_OF_COLUMNS = 12;

/**
 * Generates an array containing the current width of each column on the grid of 12.
 * For example, for a column width of 100, the result will be:
 *
 * > [100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200]
 *
 * @param {!Number} The starting colWidth.
 *
 * @returns{!Array<number>}
 */
function getGridRanges(colWidth) {
	return [...Array(TOTAL_NUMBER_OF_COLUMNS).keys()].reduce(
		(acc, current, index) => {
			if (index === 0) {
				return acc;
			}

			return [...acc, acc[current - 1] + colWidth];
		},
		[colWidth]
	);
}

/**
 * Returns an accumulative array of sizes.
 * For example, for a Row containing 3 columns sizing `4`:
 * The result is [4, 8, 12]. This result indicates the grid position of each column end.
 *
 * @param {!Array<String>} A rowChildren array, that contains the related itemId of each Row child.
 * @param {!Object} The current value of layoutData from store.
 *
 * @returns {!Array<number>}
 */
function getColumnAccumulationSizes(rowChildren, layoutData) {
	return rowChildren.reduce(
		(acc, currentId, index) => {
			if (index === 0) {
				return acc;
			}

			return [
				...acc,
				acc[index - 1] + layoutData.items[currentId].config.size
			];
		},
		[layoutData.items[rowChildren[0]].config.size]
	);
}

/**
 * Calculates the current Grid position when receiving a mousePosition.
 *
 * @param {!Number} Current mousePosition from mousemove event.
 * @param {!Number} The index of the current hovered grid.
 */
function getClosestGridIndexPosition(mousePosition, gridSizes) {
	const closest = gridSizes.reduce((prev, curr) =>
		Math.abs(curr - mousePosition) < Math.abs(prev - mousePosition)
			? curr
			: prev
	);

	return gridSizes.indexOf(closest);
}

/**
 * Receives the current Row OffsetWidth and calls `getGridRanges`.
 * @param {!Number} Row's OffsetWidth
 *
 * @returns {!Array}
 */
function getGridSizes(rowOffsetWidth) {
	const colWidth = Math.floor(rowOffsetWidth / TOTAL_NUMBER_OF_COLUMNS);

	return getGridRanges(colWidth);
}

/**
 *
 * @param {!Element} An element
 *
 * @returns {!DOMRect} The DOMRect of the given element.
 */
function getRect(element) {
	if (!element) {
		return {
			bottom: 0,
			height: 0,
			left: 0,
			right: 0,
			top: 0,
			width: 0
		};
	}

	return element.getBoundingClientRect();
}

const Row = React.forwardRef(({children, className, item, layoutData}, ref) => {
	const {gutters} = {
		...LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS[LAYOUT_DATA_ITEM_TYPES.row],
		...item.config
	};
	const parent = layoutData.items[item.parentId];
	const config = useContext(ConfigContext);
	const dispatch = useDispatch();
	const store = useSelector(state => state);
	const rowRef = useRef(null);

	const rowRect = getRect(rowRef.current);

	const [highlightedColumn, setHighLightedColumn] = useState(null);
	const [showOverlay, setShowOverlay] = useState(false);

	const getHighlightedColumnIndex = clientX => {
		const gridSizes = getGridSizes(rowRect.width);
		const mousePosition = clientX - rowRect.left;

		return getClosestGridIndexPosition(mousePosition, gridSizes);
	};

	const onResizeStart = ({clientX}) => {
		setHighLightedColumn(getHighlightedColumnIndex(clientX));
		setShowOverlay(true);
	};

	const onResizing = ({clientX}, columnInfo) => {
		if (rowRef.current) {
			const index = getHighlightedColumnIndex(clientX);
			setHighLightedColumn(index);

			const columnSizes = getColumnAccumulationSizes(
				item.children,
				layoutData
			);

			const {
				colIndex,
				currentColumn,
				currentColumnConfig,
				nextColumn,
				nextColumnIndex
			} = columnInfo;

			const currentRange = columnSizes[colIndex];
			const nextRange = columnSizes[nextColumnIndex];

			const addedIndex = index + 1;

			const newCurrentSize =
				currentColumnConfig.size + (addedIndex - currentRange);

			const newNextSize = nextRange - addedIndex;

			if (newCurrentSize >= 1 && newNextSize >= 1) {
				dispatch(
					updateColSize({
						itemId: currentColumn.itemId,
						nextColumnItemId: nextColumn.itemId,
						nextColumnSize: newNextSize,
						size: newCurrentSize
					})
				);
			}
		}
	};

	const onResizeEnd = () => {
		setHighLightedColumn(null);
		setShowOverlay(false);

		dispatch(
			resizeColumns({
				config,
				layoutData,
				store
			})
		);
	};

	const rowContent = (
		<div
			className={classNames(className, 'row', {
				empty: !item.children.some(
					childId => layoutData.items[childId].children.length
				),
				'no-gutters': !gutters
			})}
			ref={node => {
				if (node) {
					rowRef.current = node;

					if (ref) {
						ref.current = node;
					}
				}
			}}
		>
			<ResizingContext.Provider
				value={{
					onResizeEnd,
					onResizeStart,
					onResizing
				}}
			>
				{children}
			</ResizingContext.Provider>
			{showOverlay && (
				<ColumnOverlayGrid
					columnSpacing={gutters}
					highlightedColumn={highlightedColumn}
					rowRect={rowRect}
				/>
			)}
		</div>
	);

	return (
		<>
			{!parent || parent.type === LAYOUT_DATA_ITEM_TYPES.root ? (
				<div className="container-fluid p-0">{rowContent}</div>
			) : (
				rowContent
			)}
		</>
	);
});

export default Row;

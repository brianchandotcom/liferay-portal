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

import {useEventListener} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useCallback, useMemo, useRef, useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {VIEWPORT_SIZES} from '../../config/constants/viewportSizes';
import {useIsActive} from '../../contexts/ControlsContext';
import {useGlobalContext} from '../../contexts/GlobalContext';
import {
	useNextColumnSizes,
	useResizing,
	useSetNextColumnSizes,
	useSetResizing,
} from '../../contexts/ResizeContext';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../../selectors/selectCanUpdatePageStructure';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import resizeColumns from '../../thunks/resizeColumns';
import {
	NotDraggableArea,
	useSetCanDrag,
} from '../../utils/drag-and-drop/useDragAndDrop';
import {getResponsiveColumnSize} from '../../utils/getResponsiveColumnSize';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import isItemEmpty from '../../utils/isItemEmpty';
import TopperEmpty from '../TopperEmpty';
import Column from './Column';

const ROW_SIZE = 12;

const ColumnWithControls = React.forwardRef(({children, item}, ref) => {
	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);
	const layoutData = useSelector((state) => state.layoutData);
	const parentItem = layoutData.items[item.parentId];
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const [selectedColumn, setColumnSelected] = useState(null);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const dispatch = useDispatch();
	const globalContext = useGlobalContext();
	const isActive = useIsActive();
	const resizing = useResizing();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const setCanDrag = useSetCanDrag();
	const setResizing = useSetResizing();
	const setNextColumnSizes = useSetNextColumnSizes();
	const nextColumnSizes = useNextColumnSizes();

	const resizeInfo = useRef();

	const columnIndex = parentItem.children.indexOf(item.itemId);

	const parentItemIsActive = useMemo(
		() =>
			layoutData.items[item.parentId] ? isActive(item.parentId) : false,
		[isActive, item.parentId, layoutData.items]
	);

	const responsiveRowConfig = getResponsiveConfig(
		parentItem.config,
		selectedViewportSize
	);

	const isReverseOrder =
		responsiveRowConfig.reverseOrder &&
		parentItem.config.numberOfColumns === 2 &&
		responsiveRowConfig.modulesPerRow === 1;

	const isFirstColumnOfRow = columnRangeIsComplete(
		parentItem.children.slice(0, columnIndex),
		layoutData,
		nextColumnSizes,
		selectedViewportSize
	);

	const isLastColumnOfRow = columnRangeIsComplete(
		parentItem.children.slice(columnIndex + 1),
		layoutData,
		nextColumnSizes,
		selectedViewportSize
	);

	const endResize = useCallback(
		(nextSizes = null) => {

			// End resize and set all resizing props to their default. Update
			// layoutData only if we have some value in nextSizes, that contains
			// the new column sizes calculated during the resize

			resizeInfo.current = null;
			setColumnSelected(null);
			setResizing(false);
			setCanDrag(true);

			if (nextSizes) {
				const nextLayoutData = getNextLayoutData(
					layoutData,
					nextSizes,
					selectedViewportSize
				);

				dispatch(
					resizeColumns({
						layoutData: nextLayoutData,
						rowItemId: parentItem.itemId,
						segmentsExperienceId,
					})
				).then(() => {
					setNextColumnSizes(null);
				});
			}
			else {
				setNextColumnSizes(null);
			}
		},
		[
			dispatch,
			layoutData,
			parentItem.itemId,
			segmentsExperienceId,
			selectedViewportSize,
			setCanDrag,
			setNextColumnSizes,
			setResizing,
		]
	);

	const handleMouseDown = (event) => {
		setColumnSelected(item);
		setCanDrag(false);
		setResizing(true);

		// Calculate initial data for the resize. We need resizer's left and
		// right columns

		const leftColumnItem =
			layoutData.items[parentItem.children[columnIndex - 1]];

		const leftColumn = {
			initialSize: getResponsiveColumnSize(
				leftColumnItem.config,
				selectedViewportSize
			),
			item: leftColumnItem,
		};

		const rightColumn = {
			initialSize: getResponsiveColumnSize(
				item.config,
				selectedViewportSize
			),
			item,
		};

		// For some special cases, we also need previous and next columns

		let nextColumn = null;
		let previousColumn = null;

		if (isFirstColumnOfRow && rightColumn.initialSize < ROW_SIZE) {
			const nextColumnItem =
				layoutData.items[parentItem.children[columnIndex + 1]];

			nextColumn = {
				initialSize: getResponsiveColumnSize(
					nextColumnItem.config,
					selectedViewportSize
				),
				item: nextColumnItem,
			};
		}

		if (isFirstColumnOfRow && leftColumn.initialSize === 1) {
			const previousColumnItem =
				layoutData.items[parentItem.children[columnIndex - 2]];

			previousColumn = {
				initialSize: getResponsiveColumnSize(
					previousColumnItem.config,
					selectedViewportSize
				),
				item: previousColumnItem,
			};
		}

		resizeInfo.current = {
			columnWidth:
				ref.current.getBoundingClientRect().width /
				rightColumn.initialSize,
			initialX: event.clientX,
			initiallyWasFirstColumn: isFirstColumnOfRow,
			leftColumn,
			maxColumnDiff: isLastColumnOfRow
				? rightColumn.initialSize
				: rightColumn.initialSize - 1,
			minColumnDiff: -leftColumn.initialSize + 1,
			nextColumn,
			previousColumn,
			rightColumn,
		};
	};

	useEventListener(
		'mousemove',
		useCallback(
			(event) => {
				if (!resizeInfo.current || !resizing) {
					return;
				}

				const {
					columnWidth,
					initialX,
					initiallyWasFirstColumn,
					leftColumn,
					maxColumnDiff,
					minColumnDiff,
					nextColumn,
					previousColumn,
					rightColumn,
				} = resizeInfo.current;

				// Calculate displacement in px and columns

				const clientXDiff = event.clientX - initialX;
				const columnDiff = Math.round(clientXDiff / columnWidth);

				// Stop resizing if we are out of allowed displacement

				if (columnDiff < minColumnDiff || columnDiff > maxColumnDiff) {
					return;
				}

				// Calculate new column sizes. For the right column, if we
				// enlarge it enough, it will go down to the row below and will
				// occupy it completely

				let nextSizes = {
					[leftColumn.item.itemId]:
						leftColumn.initialSize + columnDiff,
					[rightColumn.item.itemId]:
						columnDiff === rightColumn.initialSize
							? ROW_SIZE
							: rightColumn.initialSize - columnDiff,
				};

				setNextColumnSizes(nextSizes);

				// Special case of resizing the first column of a row (only for
				// columns on the second row or below)

				if (initiallyWasFirstColumn) {

					// First column of a row can't be resized to the right

					if (clientXDiff >= 0) {
						return;
					}

					// Calculate new sizes. Right column will always come back
					// to the row above with size 1, and left column will
					// decrease its current size in 1 if it's not 1 already

					const currentLeftColumnSize =
						nextColumnSizes?.[leftColumn.item.itemId] ||
						leftColumn.initialSize;

					nextSizes = {
						[leftColumn.item.itemId]:
							currentLeftColumnSize - 1 || 1,
						[rightColumn.item.itemId]: 1,
					};

					// If the column that is coming back to the previous row has
					// a sibling on the right, it will also be resized

					if (nextColumn) {
						nextSizes = {
							...nextSizes,
							[nextColumn.item.itemId]:
								nextColumn.initialSize +
								rightColumn.initialSize,
						};
					}

					// If the column is coming back to the previous row and
					// the last column of that row has size 1, the previous column
					// will also be reized

					if (previousColumn) {
						nextSizes = {
							...nextSizes,
							[previousColumn.item.itemId]:
								previousColumn.initialSize - 1,
						};
					}

					// We end resize when the column comes back to previous row

					endResize(nextSizes);

					return;
				}
			},
			[endResize, resizing, setNextColumnSizes, nextColumnSizes]
		),
		false,
		globalContext.document.body
	);

	useEventListener(
		'mouseleave',
		useCallback(() => {
			if (!resizeInfo.current) {
				return;
			}

			// End resize without saving if we leave the screen

			endResize();
		}, [endResize]),
		false,
		globalContext.document.body
	);

	useEventListener(
		'mouseup',
		useCallback(() => {
			if (!resizeInfo.current) {
				return;
			}

			// End resize and save new column sizes

			endResize(nextColumnSizes);
		}, [endResize, nextColumnSizes]),
		false,
		globalContext.document.body
	);

	return (
		<TopperEmpty item={item}>
			<Column
				className={classNames('page-editor__col', {
					empty: isItemEmpty(item, layoutData, selectedViewportSize),
					'page-editor__row-overlay-grid__border':
						selectedColumn?.itemId === item.itemId &&
						!isFirstColumnOfRow &&
						resizing,
				})}
				item={item}
				ref={ref}
			>
				{(canUpdatePageStructure || canUpdateItemConfiguration) &&
					parentItemIsActive &&
					columnIndex !== 0 &&
					!isReverseOrder && (
						<NotDraggableArea>
							<button
								className={classNames(
									'btn-primary page-editor__col__resizer',
									{
										'page-editor__col__resizer-first': isFirstColumnOfRow,
									}
								)}
								onMouseDown={handleMouseDown}
								title={Liferay.Language.get('resize-column')}
								type="button"
							/>
						</NotDraggableArea>
					)}

				{children}
			</Column>
		</TopperEmpty>
	);
});

ColumnWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default ColumnWithControls;

// Calculate whether a column range is complete or not. We consider a range as
// complete when it occupies all the space of a row or several rows

function columnRangeIsComplete(
	columnRange,
	layoutData,
	nextColumnSizes,
	selectedViewportSize
) {
	const sum = columnRange
		.map(
			(columnId) =>
				nextColumnSizes?.[columnId] ||
				getResponsiveColumnSize(
					layoutData.items[columnId].config,
					selectedViewportSize
				)
		)
		.reduce((acc, value) => {
			return acc + value;
		}, 0);

	return sum % ROW_SIZE === 0;
}

// Calculate and return a new layoutData with new column sizes

export function getNextLayoutData(
	layoutData,
	nextColumnSizes,
	selectedViewportSize
) {
	const nextColumnItems = Object.entries(nextColumnSizes).reduce(
		(acc, [columnId, nextSize]) => ({
			...acc,
			[columnId]: {
				...layoutData.items[columnId],
				config: getNextResponsiveConfig(
					nextSize,
					layoutData.items[columnId].config,
					selectedViewportSize
				),
			},
		}),
		{}
	);

	return {
		...layoutData,
		items: {
			...layoutData.items,
			...nextColumnItems,
		},
	};
}

const getNextResponsiveConfig = (size, config, viewportSize) => {
	return viewportSize === VIEWPORT_SIZES.desktop
		? {...config, size}
		: {...config, [viewportSize]: {size}};
};

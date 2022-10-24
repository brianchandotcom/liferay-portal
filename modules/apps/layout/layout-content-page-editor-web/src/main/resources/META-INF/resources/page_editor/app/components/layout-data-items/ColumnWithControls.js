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
import resizeColumns from '../../thunks/resizeColumns';
import {
	NotDraggableArea,
	useSetCanDrag,
} from '../../utils/drag-and-drop/useDragAndDrop';
import getLayoutDataItemTopperUniqueClassName from '../../utils/getLayoutDataItemTopperUniqueClassName';
import {getResponsiveColumnSize} from '../../utils/getResponsiveColumnSize';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import isItemEmpty from '../../utils/isItemEmpty';
import TopperEmpty from '../topper/TopperEmpty';
import Column from './Column';

/**
 * @typedef ResizeInfo
 * @property {number} columnWidth: width of any column in px
 * @property {number} initialX: initial mouse X position in px
 * @property {boolean} initiallyWasFirstColumn: indicates whether the right
 * column was the first of the row when starting the resize
 * @property {object} leftColumn: contains the info about resizer's left column
 * @property {number} maxColumnDiff: number of columns the resizer can be moved
 * to the right. If right column is the last of the row, it's incremented in 1
 * because the column can go down to the next row
 * @property {number} minColumnDiff: number of columns the resizer can be moved
 * to the left
 * @property {object} nextColumn: contains info about the column that is next to
 * the right one. We will resize it when the right column is going up to the
 * previous row
 * @property {object} previousResizableColumn: contains info about the previous
 * column thatis rezisable. We will resize it sometimes when the right column is
 * going up to the previous row
 * @property {object} rightColumn: contains info about resizer's right column
 */

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
	const setCanDrag = useSetCanDrag();
	const setResizing = useSetResizing();
	const setNextColumnSizes = useSetNextColumnSizes();
	const nextColumnSizes = useNextColumnSizes();

	/**
	 * @type {{current: ResizeInfo}}
	 */
	const resizeInfoRef = useRef();

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

			resizeInfoRef.current = null;
			setColumnSelected(null);
			setResizing(false);
			setCanDrag(true);

			if (nextSizes) {

				// When we are resizing some columns in a viewport that is not
				// Desktop, we need to set a size for all columns that does not
				// have one, so they don't inherit it anymore

				const siblingSizes = {};

				if (selectedViewportSize !== VIEWPORT_SIZES.desktop) {
					const siblingsWithoutSize = parentItem.children.filter(
						(sibling) => !nextSizes[sibling]
					);

					siblingsWithoutSize.forEach((sibling) => {
						const siblingItem = layoutData.items[sibling];

						siblingSizes[sibling] = getResponsiveColumnSize(
							siblingItem.config,
							selectedViewportSize
						);
					});
				}

				const nextLayoutData = getNextLayoutData(
					layoutData,
					{...nextSizes, ...siblingSizes},
					selectedViewportSize
				);

				dispatch(
					resizeColumns({
						layoutData: nextLayoutData,
						rowItemId: parentItem.itemId,
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
			parentItem,
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

		// For some special cases, we also need the previous resizable column on
		// the left and the next one on the right

		let nextColumn = null;
		let previousResizableColumn = null;

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
			const previousResizableColumnId = parentItem.children
				.filter(
					(child, index) =>
						getResponsiveColumnSize(
							layoutData.items[child].config,
							selectedViewportSize
						) > 1 && index < columnIndex
				)
				.pop();

			const previousResizableColumnItem =
				layoutData.items[previousResizableColumnId];

			previousResizableColumn = {
				initialSize: getResponsiveColumnSize(
					previousResizableColumnItem.config,
					selectedViewportSize
				),
				item: previousResizableColumnItem,
			};
		}

		resizeInfoRef.current = {
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
			previousResizableColumn,
			rightColumn,
		};
	};

	useEventListener(
		'mousemove',
		useCallback(
			(event) => {
				if (!resizeInfoRef.current || !resizing) {
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
					previousResizableColumn,
					rightColumn,
				} = resizeInfoRef.current;

				// Calculate displacement in px

				const clientXDiff = event.clientX - initialX;

				let nextSizes = null;

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
					// the last column of that row has size 1, the previous
					// resizable column will also be resized

					if (previousResizableColumn) {
						nextSizes = {
							...nextSizes,
							[previousResizableColumn.item.itemId]:
								previousResizableColumn.initialSize - 1,
						};
					}

					// We end resize when the column comes back to previous row

					endResize(nextSizes);

					return;
				}

				// Standard resizing of a column that is not the first

				// Calculate displacement in columns and stop resizing if we
				// are out of allowed displacement

				const columnDiff = Math.round(clientXDiff / columnWidth);

				if (columnDiff < minColumnDiff || columnDiff > maxColumnDiff) {
					return;
				}

				// Calculate new column sizes. For the right column, if we
				// enlarge it enough, it will go down to the row below, will
				// occupy it completely and we will end the resize

				const nextRightColumnSize =
					columnDiff === rightColumn.initialSize
						? ROW_SIZE
						: rightColumn.initialSize - columnDiff;

				nextSizes = {
					[leftColumn.item.itemId]:
						leftColumn.initialSize + columnDiff,
					[rightColumn.item.itemId]: nextRightColumnSize,
				};

				if (nextRightColumnSize === ROW_SIZE) {
					endResize(nextSizes);

					return;
				}

				// Save new column sizes in the context and continue the resize

				setNextColumnSizes(nextSizes);
			},
			[endResize, resizing, setNextColumnSizes, nextColumnSizes]
		),
		false,
		globalContext.document.body
	);

	useEventListener(
		'mouseleave',
		useCallback(() => {
			if (!resizeInfoRef.current) {
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
			if (!resizeInfoRef.current) {
				return;
			}

			// End resize and save new column sizes

			endResize(nextColumnSizes);
		}, [endResize, nextColumnSizes]),
		false,
		globalContext.document.body
	);

	return (
		<TopperEmpty
			className={getLayoutDataItemTopperUniqueClassName(item.itemId)}
			item={item}
		>
			<Column
				className={classNames('page-editor__col', {
					'empty': isItemEmpty(
						item,
						layoutData,
						selectedViewportSize
					),
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

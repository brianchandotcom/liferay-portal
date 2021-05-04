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
import React, {useMemo, useRef, useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {VIEWPORT_SIZES} from '../../config/constants/viewportSizes';
import {useIsActive} from '../../contexts/ControlsContext';
import {useGlobalContext} from '../../contexts/GlobalContext';
import {
	useResizeContext,
	useSetResizeContext,
	useSetUpdatedLayoutDataContext,
	useUpdatedLayoutDataContext,
} from '../../contexts/ResizeContext';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../../selectors/selectCanUpdatePageStructure';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import resizeColumns from '../../thunks/resizeColumns';
import {NotDraggableArea} from '../../utils/drag-and-drop/useDragAndDrop';
import {getResponsiveColumnSize} from '../../utils/getResponsiveColumnSize';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import TopperEmpty from '../TopperEmpty';
import Column from './Column';

const ROW_SIZE = 12;

const getNewResponsiveConfig = (size, config, viewportSize) => {
	return viewportSize === VIEWPORT_SIZES.desktop
		? {...config, size}
		: {...config, [viewportSize]: {size}};
};

export const updateNewLayoutDataContext = (
	layoutDataContext,
	columnConfig,
	selectedViewportSize
) => {
	const newColumnConfig = Object.keys(columnConfig).reduce(
		(acc, columnId) => ({
			...acc,
			[columnId]: {
				...layoutDataContext.items[columnId],
				config: getNewResponsiveConfig(
					columnConfig[columnId].size,
					columnConfig[columnId].config,
					selectedViewportSize
				),
			},
		}),
		{}
	);

	return {
		...layoutDataContext,
		items: {
			...layoutDataContext.items,
			...newColumnConfig,
		},
	};
};

const ColumnWithControls = React.forwardRef(({children, item}, ref) => {
	const dispatch = useDispatch();
	const [isInitialResponsiveConfig, setIsInitialResponsiveConfig] = useState(
		false
	);
	const resizeInfo = useRef();
	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);
	const layoutData = useSelector((state) => state.layoutData);
	const parentItem = layoutData.items[item.parentId];
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const globalContext = useGlobalContext();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const [selectedColumn, setColumnSelected] = useState(null);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const resizing = useResizeContext();
	const setResizing = useSetResizeContext();
	const setUpdatedLayoutData = useSetUpdatedLayoutDataContext();
	const updatedLayoutData = useUpdatedLayoutDataContext();

	const columnIndex = parentItem.children.indexOf(item.itemId);
	let layoutDataContext = updatedLayoutData || layoutData;

	const columnRangeIsComplete = (columnRange) => {
		const sum = columnRange
			.map((columnId) =>
				getResponsiveColumnSize(
					layoutDataContext.items[columnId].config,
					selectedViewportSize
				)
			)
			.reduce((acc, value) => {
				return acc + value;
			}, 0);

		return sum % ROW_SIZE === 0;
	};

	const isLastColumnOfRow = () =>
		columnRangeIsComplete(parentItem.children.slice(columnIndex + 1));

	const isFirstColumnOfRow = (newColumnIndex) =>
		columnRangeIsComplete(
			parentItem.children.slice(0, newColumnIndex || columnIndex)
		);

	const getPreviousResizableColumnId = () => {
		const previousResizableColumns = parentItem.children
			.slice(0, columnIndex)
			.filter(
				(columnId) =>
					getResponsiveColumnSize(
						layoutData.items[columnId].config,
						selectedViewportSize
					) > 1
			);

		return previousResizableColumns[previousResizableColumns.length - 1];
	};

	const setInitialResponsiveConfig = (columns) => {
		const columnsInfo = columns.reduce(
			(acc, column) => ({
				...acc,
				[column.itemId]: {
					config: column.config,
					size: getResponsiveColumnSize(
						column.config,
						selectedViewportSize
					),
				},
			}),
			{}
		);

		layoutDataContext = updateNewLayoutDataContext(
			layoutDataContext,
			columnsInfo,
			selectedViewportSize
		);

		setIsInitialResponsiveConfig(false);
		setUpdatedLayoutData(layoutDataContext);
	};

	const handleMouseDown = (event) => {
		setColumnSelected(item);
		setResizing(true);

		let columns = null;
		const leftColumn =
			layoutData.items[parentItem.children[columnIndex - 1]];
		const rightColumn = item;

		const leftColumnInitialSize = getResponsiveColumnSize(
			leftColumn.config,
			selectedViewportSize
		);
		const rightColumnInitialSize = getResponsiveColumnSize(
			item.config,
			selectedViewportSize
		);

		if (selectedViewportSize !== VIEWPORT_SIZES.desktop) {
			columns = parentItem.children.map(
				(columnId) => layoutDataContext.items[columnId]
			);

			setIsInitialResponsiveConfig(
				!columns[0].config[selectedViewportSize].size
			);
		}

		resizeInfo.current = {
			columnWidth:
				ref.current.getBoundingClientRect().width /
				rightColumnInitialSize,
			columns,
			initialClientX: event.clientX,
			leftColumnConfig: leftColumn.config,
			leftColumnId: leftColumn.itemId,
			leftColumnInitialSize,
			maxColumnDiff: isLastColumnOfRow()
				? rightColumnInitialSize
				: rightColumnInitialSize - 1,
			minColumnDiff: -leftColumnInitialSize + 1,
			rightColumnConfig: item.config,
			rightColumnId: rightColumn.itemId,
			rightColumnInitialSize,
			rightColumnIsFirst: isFirstColumnOfRow(),
		};
	};

	useEventListener(
		'mousemove',
		(event) => {
			if (resizeInfo.current) {
				const {
					columnWidth,
					columns,
					initialClientX,
					leftColumnConfig,
					leftColumnId,
					leftColumnInitialSize,
					maxColumnDiff,
					minColumnDiff,
					rightColumnConfig,
					rightColumnId,
					rightColumnInitialSize,
					rightColumnIsFirst,
				} = resizeInfo.current;

				const clientXDiff = event.clientX - initialClientX;

				if (isInitialResponsiveConfig) {
					setInitialResponsiveConfig(columns);
				}

				if (rightColumnIsFirst && clientXDiff < 0) {
					let newLeftColumnId;
					let newLeftColumnSize;
					let newLeftColumnConfig;

					const leftColumnSize = leftColumnInitialSize - 1;
					const rightColumnSize = 1;

					if (leftColumnInitialSize === 1) {
						newLeftColumnId = getPreviousResizableColumnId();
						newLeftColumnConfig =
							layoutData.items[newLeftColumnId].config;

						newLeftColumnSize =
							getResponsiveColumnSize(
								newLeftColumnConfig,
								selectedViewportSize
							) - 1;
					}

					if (!isLastColumnOfRow()) {
						const nextColumnId =
							layoutData.items[
								parentItem.children[columnIndex + 1]
							].itemId;

						const nextColumnConfig =
							layoutDataContext.items[nextColumnId].config;

						const nextColumnResponsiveConfig = getResponsiveColumnSize(
							nextColumnConfig,
							selectedViewportSize
						);

						const nextColumnSize =
							nextColumnResponsiveConfig + rightColumnInitialSize;

						layoutDataContext = updateNewLayoutDataContext(
							layoutDataContext,
							{
								[nextColumnId]: {
									config: nextColumnConfig,
									size: nextColumnSize,
								},
							},
							selectedViewportSize
						);
					}

					layoutDataContext = updateNewLayoutDataContext(
						layoutDataContext,
						{
							[newLeftColumnId || leftColumnId]: {
								config: newLeftColumnConfig || leftColumnConfig,
								size: newLeftColumnSize || leftColumnSize,
							},
							[rightColumnId]: {
								config: rightColumnConfig,
								size: rightColumnSize,
							},
						},
						selectedViewportSize
					);

					setUpdatedLayoutData(layoutDataContext);

					resizeInfo.current = null;
					setResizing(false);
					setColumnSelected(null);

					dispatch(
						resizeColumns({
							layoutData: layoutDataContext,
							rowItemId: parentItem.itemId,
							segmentsExperienceId,
						})
					).then(() => {
						setUpdatedLayoutData(null);
					});
				}
				else if (!rightColumnIsFirst) {
					const columnDiff = Math.min(
						maxColumnDiff,
						Math.max(
							minColumnDiff,
							Math.round(clientXDiff / columnWidth)
						)
					);

					let leftColumnSize = leftColumnInitialSize + columnDiff;
					let rightColumnSize = rightColumnInitialSize - columnDiff;

					if (rightColumnInitialSize - columnDiff === 0) {
						leftColumnSize =
							leftColumnInitialSize + rightColumnInitialSize;
						rightColumnSize = ROW_SIZE;
					}
					layoutDataContext = updateNewLayoutDataContext(
						layoutDataContext,
						{
							[leftColumnId]: {
								config: leftColumnConfig,
								size: leftColumnSize,
							},
							[rightColumnId]: {
								config: rightColumnConfig,
								size: rightColumnSize,
							},
						},
						selectedViewportSize
					);

					setUpdatedLayoutData(layoutDataContext);
				}
			}
		},
		false,
		globalContext.document.body
	);

	useEventListener(
		'mouseup',
		() => {
			if (resizeInfo.current) {
				resizeInfo.current = null;
				setColumnSelected(null);
				setResizing(false);
				dispatch(
					resizeColumns({
						layoutData: layoutDataContext,
						rowItemId: parentItem.itemId,
						segmentsExperienceId,
					})
				).then(() => setUpdatedLayoutData(null));
			}
		},
		false,
		globalContext.document.body
	);

	useEventListener(
		'mouseleave',
		() => {
			if (resizeInfo.current) {
				resizeInfo.current = null;
				setColumnSelected(null);
				setResizing(false);
				setUpdatedLayoutData(null);
			}
		},
		false,
		globalContext.document.body
	);

	const isActive = useIsActive();

	const parentItemIsActive = useMemo(
		() =>
			layoutData.items[item.parentId] ? isActive(item.parentId) : false,
		[isActive, item.parentId, layoutData.items]
	);

	const firstColumnOfRow = isFirstColumnOfRow(columnIndex);

	const responsiveRowConfig = getResponsiveConfig(
		parentItem.config,
		selectedViewportSize
	);

	const isReverseOrder =
		responsiveRowConfig.reverseOrder &&
		parentItem.config.numberOfColumns === 2 &&
		responsiveRowConfig.modulesPerRow === 1;

	return (
		<TopperEmpty item={item}>
			<Column
				className={classNames('page-editor__col', {
					'page-editor__row-overlay-grid__border':
						!firstColumnOfRow &&
						resizing &&
						selectedColumn &&
						selectedColumn.itemId === item.itemId,
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
										'page-editor__col__resizer-first': firstColumnOfRow,
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

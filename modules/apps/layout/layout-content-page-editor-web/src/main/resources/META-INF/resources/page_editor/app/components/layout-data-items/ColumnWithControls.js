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
import {useEventListener} from 'frontend-js-react-web';
import React, {useMemo, useRef, useState} from 'react';

import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import {VIEWPORT_SIZES} from '../../config/constants/viewportSizes';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import resizeColumns from '../../thunks/resizeColumns';
import {getResponsiveColumnSizeConfig} from '../../utils/getResponsiveColumnSizeConfig';
import {NotDraggableArea} from '../../utils/useDragAndDrop';
import {useIsActive} from '../Controls';
import TopperEmpty from '../TopperEmpty';
import Column from './Column';
import {
	useResizeContext,
	useSetResizeContext,
	useSetUpdatedLayoutDataContext,
	useUpdatedLayoutDataContext,
} from './RowWithControls';

const ROW_SIZE = 12;

const getNewResponsiveConfig = (columnSize, config, viewportSize) => {
	return viewportSize === VIEWPORT_SIZES.desktop
		? {...config, size: columnSize}
		: {...config, [viewportSize]: {size: columnSize}};
};

const updateNewLayoutDataContext = (
	layoutDataContext,
	columnSizeConfig,
	config,
	selectedViewportSize
) => {
	const newColumnSizeConfig = Object.keys(columnSizeConfig).reduce(
		(acc, key) => {
			return {
				...acc,
				[key]: {
					...layoutDataContext.items[key],
					config: getNewResponsiveConfig(
						columnSizeConfig[key],
						config[key],
						selectedViewportSize
					),
				},
			};
		},
		{}
	);

	return {
		...layoutDataContext,
		items: {
			...layoutDataContext.items,
			...newColumnSizeConfig,
		},
	};
};

const ColumnWithControls = React.forwardRef(
	({children, item, layoutData}, ref) => {
		const dispatch = useDispatch();
		const parentItem = layoutData.items[item.parentId];
		const resizeInfo = useRef();
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
				.map(
					(columnId) =>
						getResponsiveColumnSizeConfig(
							layoutDataContext.items[columnId].config,
							selectedViewportSize
						).size
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
						getResponsiveColumnSizeConfig(
							layoutData.items[columnId].config,
							selectedViewportSize
						).size > 1
				);

			return previousResizableColumns[
				previousResizableColumns.length - 1
			];
		};

		const handleMouseDown = (event) => {
			setColumnSelected(item);
			setResizing(true);

			const leftColumn =
				layoutData.items[parentItem.children[columnIndex - 1]];
			const rightColumn = item;

			const leftColumnResponsiveConfig = getResponsiveColumnSizeConfig(
				leftColumn.config,
				selectedViewportSize
			);
			const rightColumnResponsiveConfig = getResponsiveColumnSizeConfig(
				item.config,
				selectedViewportSize
			);

			resizeInfo.current = {
				columnWidth:
					ref.current.getBoundingClientRect().width /
					rightColumnResponsiveConfig.size,
				initialClientX: event.clientX,
				leftColumnConfig: leftColumn.config,
				leftColumnId: leftColumn.itemId,
				leftColumnInitialSize: leftColumnResponsiveConfig.size,
				maxColumnDiff: isLastColumnOfRow()
					? rightColumnResponsiveConfig.size
					: rightColumnResponsiveConfig.size - 1,
				minColumnDiff: -leftColumnResponsiveConfig.size + 1,
				rightColumnConfig: item.config,
				rightColumnId: rightColumn.itemId,
				rightColumnInitialSize: rightColumnResponsiveConfig.size,
				rightColumnIsFirst: isFirstColumnOfRow(),
			};
		};

		useEventListener(
			'mousemove',
			(event) => {
				if (resizeInfo.current) {
					const {
						columnWidth,
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

					if (rightColumnIsFirst && clientXDiff < 0) {
						let leftColumnSize = leftColumnInitialSize - 1;
						let newLeftColumnId = leftColumnId;
						const rightColumnSize = 1;

						if (leftColumnInitialSize === 1) {
							newLeftColumnId = getPreviousResizableColumnId();

							leftColumnSize =
								getResponsiveColumnSizeConfig(
									layoutData.items[newLeftColumnId].config,
									selectedViewportSize
								).size - 1;
						}

						if (!isLastColumnOfRow()) {
							const nextColumnId =
								layoutData.items[
									parentItem.children[columnIndex + 1]
								].itemId;

							const nextColumnConfig =
								layoutDataContext.items[nextColumnId].config;

							const nextColumnResponsiveConfig = getResponsiveColumnSizeConfig(
								nextColumnConfig,
								selectedViewportSize
							);

							const nextColumnSize =
								nextColumnResponsiveConfig.size +
								rightColumnInitialSize;

							layoutDataContext = updateNewLayoutDataContext(
								layoutDataContext,
								{
									[nextColumnId]: nextColumnSize,
								},
								{
									[nextColumnId]: nextColumnConfig,
								},
								selectedViewportSize
							);
						}

						layoutDataContext = updateNewLayoutDataContext(
							layoutDataContext,
							{
								[newLeftColumnId]: leftColumnSize,
								[rightColumnId]: rightColumnSize,
							},
							{
								[newLeftColumnId]: leftColumnConfig,
								[rightColumnId]: rightColumnConfig,
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
						let rightColumnSize =
							rightColumnInitialSize - columnDiff;

						if (rightColumnInitialSize - columnDiff === 0) {
							leftColumnSize =
								leftColumnInitialSize + rightColumnInitialSize;
							rightColumnSize = ROW_SIZE;
						}
						layoutDataContext = updateNewLayoutDataContext(
							layoutDataContext,
							{
								[leftColumnId]: leftColumnSize,
								[rightColumnId]: rightColumnSize,
							},
							{
								[leftColumnId]: leftColumnConfig,
								[rightColumnId]: rightColumnConfig,
							},
							selectedViewportSize
						);

						setUpdatedLayoutData(layoutDataContext);
					}
				}
			},
			false,
			document.body
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
							segmentsExperienceId,
						})
					).then(() => setUpdatedLayoutData(null));
				}
			},
			false,
			document.body
		);

		const isActive = useIsActive();

		const parentItemIsActive = useMemo(
			() =>
				layoutData.items[item.parentId]
					? isActive(item.parentId)
					: false,
			[isActive, item, layoutData]
		);

		const firstColumnOfRow = isFirstColumnOfRow(columnIndex);

		return (
			<TopperEmpty item={item} layoutData={layoutData}>
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
					{parentItemIsActive && columnIndex !== 0 ? (
						<NotDraggableArea>
							<button
								className={classNames(
									'btn-primary page-editor__col__resizer',
									{
										'page-editor__col__resizer-first': firstColumnOfRow,
									}
								)}
								onMouseDown={handleMouseDown}
							/>
							{children}
						</NotDraggableArea>
					) : (
						children
					)}
				</Column>
			</TopperEmpty>
		);
	}
);

ColumnWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

export default ColumnWithControls;

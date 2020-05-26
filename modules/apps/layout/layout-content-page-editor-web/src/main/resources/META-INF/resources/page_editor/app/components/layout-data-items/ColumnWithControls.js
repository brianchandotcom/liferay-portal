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
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import resizeColumns from '../../thunks/resizeColumns';
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

const updateNewLayoutDataContext = (layoutDataContext, columnInfo) => {
	const newColumnInfo = Object.keys(columnInfo).reduce((acc, key) => {
		return {
			...acc,
			[key]: {
				...layoutDataContext.items[key],
				config: {size: columnInfo[key]},
			},
		};
	}, {});

	return {
		...layoutDataContext,
		items: {
			...layoutDataContext.items,
			...newColumnInfo,
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

		const resizing = useResizeContext();
		const setResizing = useSetResizeContext();
		const setUpdatedLayoutData = useSetUpdatedLayoutDataContext();
		const updatedLayoutData = useUpdatedLayoutDataContext();

		const columnIndex = parentItem.children.indexOf(item.itemId);
		let layoutDataContext = updatedLayoutData || layoutData;

		const columnRangeIsComplete = (columnRange) => {
			const sum = columnRange
				.map(
					(columnId) => layoutDataContext.items[columnId].config.size
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
					(columnId) => layoutData.items[columnId].config.size > 1
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

			resizeInfo.current = {
				columnWidth:
					ref.current.getBoundingClientRect().width /
					item.config.size,
				initialClientX: event.clientX,
				leftColumnId: leftColumn.itemId,
				leftColumnInitialSize: leftColumn.config.size,
				maxColumnDiff: isLastColumnOfRow()
					? rightColumn.config.size
					: rightColumn.config.size - 1,
				minColumnDiff: -leftColumn.config.size + 1,
				rightColumnId: rightColumn.itemId,
				rightColumnInitialSize: rightColumn.config.size,
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
						leftColumnId,
						leftColumnInitialSize,
						maxColumnDiff,
						minColumnDiff,
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
								layoutData.items[newLeftColumnId].config.size -
								1;
						}

						if (!isLastColumnOfRow()) {
							const nextColumnId =
								layoutData.items[
									parentItem.children[columnIndex + 1]
								].itemId;

							const nextColumnSize =
								layoutDataContext.items[nextColumnId].config
									.size + rightColumnInitialSize;
							layoutDataContext = updateNewLayoutDataContext(
								layoutDataContext,
								{
									[nextColumnId]: nextColumnSize,
								}
							);
						}

						layoutDataContext = updateNewLayoutDataContext(
							layoutDataContext,
							{
								[newLeftColumnId]: leftColumnSize,
								[rightColumnId]: rightColumnSize,
							}
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
							}
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

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
import updateColSize from '../../actions/updateColSize';
import layoutDataReducer from '../../reducers/layoutDataReducer';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import resizeColumns from '../../thunks/resizeColumns';
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

const updateNewLayoutDataContext = ({
	layoutDataContext,
	leftColumnId,
	leftColumnSize,
	rightColumnId,
	rightColumnSize,
}) => {
	return {
		...layoutDataContext,
		items: {
			...layoutDataContext.items,
			[leftColumnId]: {
				...layoutDataContext.items[leftColumnId],
				config: {size: leftColumnSize},
			},
			[rightColumnId]: {
				...layoutDataContext.items[rightColumnId],
				config: {size: rightColumnSize},
			},
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
				.map((columnId) => layoutData.items[columnId].config.size)
				.reduce((acc, value) => {
					return acc + value;
				}, 0);

			return sum % ROW_SIZE === 0;
		};

		const isLastColumnOfRow = () =>
			columnRangeIsComplete(parentItem.children.slice(columnIndex + 1));

		const isFirstColumnOfRow = () =>
			columnRangeIsComplete(parentItem.children.slice(0, columnIndex));

		const handleMouseDown = (event) => {
			setColumnSelected(item);
			setResizing(true);

			const leftColumn =
				layoutData.items[
					parentItem.children[
						parentItem.children.indexOf(item.itemId) - 1
					]
				];

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
						const leftColumnSize = leftColumnInitialSize - 1;
						const rightColumnSize = 1;

						layoutDataContext = updateNewLayoutDataContext({
							layoutDataContext,
							leftColumnId,
							leftColumnSize,
							rightColumnId,
							rightColumnSize,
						});

						setUpdatedLayoutData(layoutDataContext);

						resizeInfo.current = null;
						setResizing(false);
						setColumnSelected(null);

						dispatch(
							resizeColumns({
								layoutData: layoutDataReducer(
									layoutData,
									updateColSize({
										itemId: rightColumnId,
										nextColumnItemId: leftColumnId,
										nextColumnSize: leftColumnSize,
										size: rightColumnSize,
									})
								),
								segmentsExperienceId,
							})
						);
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

						layoutDataContext = updateNewLayoutDataContext({
							layoutDataContext,
							leftColumnId,
							leftColumnSize,
							rightColumnId,
							rightColumnSize,
						});

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
					);
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

		return (
			<TopperEmpty item={item} layoutData={layoutData}>
				<Column
					className={classNames('page-editor__col', {
						'page-editor__row-overlay-grid__border':
							resizing &&
							selectedColumn &&
							selectedColumn.itemId === item.itemId,
					})}
					item={item}
					ref={ref}
				>
					{parentItemIsActive && columnIndex !== 0 ? (
						<div>
							<button
								className="btn-primary page-editor__col__resizer"
								onMouseDown={handleMouseDown}
							/>
							{children}
						</div>
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

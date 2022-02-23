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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayList from '@clayui/list';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import React, {useContext, useRef} from 'react';
import {useDrag, useDrop} from 'react-dnd';

import ViewContext, {TYPES} from '../context';

import './BuilderListItem.scss';

interface Iprops {
	index: number;
	isDefaultSort?: boolean;
	label?: string;
	objectFieldName: string;
	setEditingObjectFieldName?: (objectFieldName: string) => void;
	setIsEditingSort?: (boolean: boolean) => void;
	setVisibleModal?: (boolean: boolean) => void;
	sortOrder?: string;
}

const BuilderListItem: React.FC<Iprops> = ({
	index,
	isDefaultSort,
	label,
	objectFieldName,
	setEditingObjectFieldName,
	setIsEditingSort,
	setVisibleModal,
	sortOrder,
}) => {
	const [, dispatch] = useContext(ViewContext);

	const ref = useRef() as React.MutableRefObject<HTMLLIElement>;

	const [{isDragging}, dragRef] = useDrag({
		collect: (monitor) => ({
			isDragging: monitor.isDragging(),
		}),
		item: {
			index,
			type: 'FIELD',
		},
	});

	const [{canDrop}, dropRef] = useDrop({
		accept: 'FIELD',
		collect: (monitor) => ({
			canDrop: monitor.canDrop() && monitor.isOver(),
		}),
		hover(item: any, monitor) {
			const draggedIndex = item.index;
			const targetIndex: any = index;

			if (draggedIndex === targetIndex) {
				return;
			}

			const targetSize = ref.current.getBoundingClientRect();
			const targetCenter = (targetSize.bottom - targetSize.top) / 2;

			const draggedOffset: any = monitor.getClientOffset();
			const draggedTop = draggedOffset?.y - targetSize.top;

			if (draggedIndex < targetIndex && draggedTop < targetCenter) {
				return;
			}

			if (draggedIndex > targetIndex && draggedTop > targetCenter) {
				return;
			}

			isDefaultSort
				? dispatch({
						payload: {draggedIndex, targetIndex},
						type: TYPES.CHANGE_OBJECT_VIEW_SORT_COLUMN_ORDER,
				  })
				: dispatch({
						payload: {draggedIndex, targetIndex},
						type: TYPES.CHANGE_OBJECT_VIEW_COLUMN_ORDER,
				  });

			item.index = targetIndex;
		},
	});

	const handleDeleteColumn = (
		objectFieldName?: string,
		isDefaultSort?: boolean
	) => {
		isDefaultSort
			? dispatch({
					payload: {objectFieldName},
					type: TYPES.DELETE_OBJECT_VIEW_SORT_COLUMN,
			  })
			: (dispatch({
					payload: {objectFieldName},
					type: TYPES.DELETE_OBJECT_VIEW_COLUMN,
			  }),
			  dispatch({
					payload: {objectFieldName},
					type: TYPES.DELETE_OBJECT_VIEW_SORT_COLUMN,
			  }));
	};

	dragRef(dropRef(ref));

	const handleEnableEditModal = (objectFieldName: string) => {
		setEditingObjectFieldName && setEditingObjectFieldName(objectFieldName);
		setIsEditingSort && setIsEditingSort(true);
		setVisibleModal && setVisibleModal(true);
	};

	return (
		<>
			<ClayList.Item
				className={classNames(
					'lfr-object__object-custom-view-builder-item',
					{
						'lfr-object__object-custom-view-builder-item-object-custom-view-builder-item--canDrop': canDrop,
						'lfr-object__object-custom-view-builder-item-object-custom-view-builder-item--dragging': isDragging,
					}
				)}
				flex
				ref={ref}
			>
				<ClayList.ItemField>
					<ClayButtonWithIcon displayType={null} symbol="drag" />
				</ClayList.ItemField>

				<ClayList.ItemField expand>
					<ClayList.ItemTitle>{label}</ClayList.ItemTitle>
				</ClayList.ItemField>

				{isDefaultSort && (
					<ClayList.ItemField
						className="object-builder-list-item-sort-order"
						expand
					>
						<ClayList.ItemText>
							{sortOrder === 'asc'
								? Liferay.Language.get('ascending')
								: Liferay.Language.get('descending')}
						</ClayList.ItemText>
					</ClayList.ItemField>
				)}

				<ClayList.ItemField className="lfr-object__object-custom-view-builder-item-action-menu">
					{isDefaultSort && (
						<ClayTooltipProvider>
							<ClayList.QuickActionMenu.Item
								data-tooltip-align="bottom"
								onClick={() =>
									handleEnableEditModal(objectFieldName)
								}
								symbol="pencil"
								title="Edit"
							/>
						</ClayTooltipProvider>
					)}

					<ClayTooltipProvider>
						<ClayList.QuickActionMenu.Item
							data-tooltip-align="bottom"
							onClick={() =>
								handleDeleteColumn(
									objectFieldName,
									isDefaultSort
								)
							}
							symbol="times"
							title="Delete"
						/>
					</ClayTooltipProvider>
				</ClayList.ItemField>
			</ClayList.Item>
		</>
	);
};

export default BuilderListItem;

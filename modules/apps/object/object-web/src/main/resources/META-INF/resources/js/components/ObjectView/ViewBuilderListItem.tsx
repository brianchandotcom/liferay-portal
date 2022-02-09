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
import classNames from 'classnames';
import React, {useContext, useRef} from 'react';
import {useDrag, useDrop} from 'react-dnd';

import ViewContext, {TYPES} from './context';
import {TObjectViewColumn} from './types';

import './ViewBuilderListItem.scss';

interface Iprops {
	index: number;
	objectViewColumn: TObjectViewColumn;
}

const ViewBuilderListItem: React.FC<Iprops> = ({index, objectViewColumn}) => {
	const [, dispatch] = useContext(ViewContext);

	const {label, objectFieldName} = objectViewColumn;

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

			dispatch({
				payload: {draggedIndex, targetIndex},
				type: TYPES.CHANGE_OBJECT_VIEW_COLUMN_ORDER,
			});

			item.index = targetIndex;
		},
	});

	const handleDeleteColumn = (objectFieldName: string) => {
		dispatch({
			payload: {objectFieldName},
			type: TYPES.DELETE_OBJECT_VIEW_COLUMN,
		});
	};

	dragRef(dropRef(ref));

	return (
		<>
			<ClayList.Item
				className={`lfr-object__object-custom-view-builder-item${classNames(
					{
						'-object-custom-view-builder-item--canDrop': canDrop,
						'-object-custom-view-builder-item--dragging': isDragging,
					}
				)}`}
				flex
				ref={ref}
			>
				<ClayList.ItemField>
					<ClayButtonWithIcon displayType={null} symbol="drag" />
				</ClayList.ItemField>

				<ClayList.ItemField expand>
					<ClayList.ItemTitle>{label}</ClayList.ItemTitle>
				</ClayList.ItemField>

				<ClayList.ItemField>
					<ClayList.QuickActionMenu>
						<ClayList.QuickActionMenu.Item
							onClick={() => handleDeleteColumn(objectFieldName)}
							symbol="times"
						/>
					</ClayList.QuickActionMenu>
				</ClayList.ItemField>
			</ClayList.Item>
		</>
	);
};

export default ViewBuilderListItem;

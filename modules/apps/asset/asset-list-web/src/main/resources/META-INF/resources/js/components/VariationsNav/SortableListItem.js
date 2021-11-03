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

import ClayIcon from '@clayui/icon';
import ClayList from '@clayui/list';
import PropTypes from 'prop-types';
import React, {useRef} from 'react';
import {useDrag, useDrop} from 'react-dnd';

const SortableListItem = ({id, index, onMove, sortableListItem}) => {
	const ref = useRef(null);

	const [, drop] = useDrop({
		accept: 'sortableListItem',
		hover(item, monitor) {
			const dragIndex = item.index;

			const hoverIndex = index;

			if (!ref.current || dragIndex === hoverIndex) {
				return;
			}

			const hoverBoundingRect = ref.current.getBoundingClientRect();

			const verticalMiddle =
				(hoverBoundingRect.bottom - hoverBoundingRect.top) / 2;

			const mousePosition = monitor.getClientOffset();

			const pixelsToTop = mousePosition.y - hoverBoundingRect.top;

			const draggingUpwards =
				dragIndex > hoverIndex && pixelsToTop > verticalMiddle * 1.5;

			const draggingDownwards =
				dragIndex < hoverIndex && pixelsToTop < verticalMiddle / 2;

			if (draggingDownwards || draggingUpwards) {
				return;
			}

			onMove(dragIndex, hoverIndex);

			item.index = hoverIndex;
		},
	});

	const [{isDragging, itemBeingDragged}, drag] = useDrag({
		collect: (monitor) => ({
			isDragging: monitor.isDragging(),
			itemBeingDragged: monitor.getItem() || {id: 0},
		}),
		item: {id, index, type: 'sortableListItem'},
	});

	const isItemBeingDragged = itemBeingDragged.id === id;

	drag(drop(ref));

	const style = {
		backgroundColor: isItemBeingDragged ? '#EFEFEF' : '',
		border: isItemBeingDragged ? '2px solid #555555' : '',
		cursor: 'grab',
		opacity: isDragging ? 0 : 1,
	};

	return (
		<ClayList.Item
			active={sortableListItem.active}
			className="align-items-center"
			flex
			id={`sortableListItem${sortableListItem.editAssetListEntryURL}`}
			ref={ref}
			style={style}
		>
			<ClayList.ItemField>
				<ClayIcon symbol="drag" />
			</ClayList.ItemField>

			<ClayList.ItemField>
				<ClayList.ItemTitle>
					<a href={sortableListItem.editAssetListEntryURL}>
						{sortableListItem.label}
					</a>
				</ClayList.ItemTitle>
			</ClayList.ItemField>
		</ClayList.Item>
	);
};

SortableListItem.propTypes = {
	id: PropTypes.string.isRequired,
	index: PropTypes.number.isRequired,
	onMove: PropTypes.func.isRequired,
	sortableListItem: PropTypes.object.isRequired,
};

export default SortableListItem;

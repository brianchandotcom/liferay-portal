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
import ClayLink from '@clayui/link';
import ClayList from '@clayui/list';
import PropTypes from 'prop-types';
import React, {useRef} from 'react';
import {useDrag, useDrop} from 'react-dnd';

import SortableListItemMoreActions from './SortableListItemMoreActions';

const SortableListItem = ({
	handleItemDelete,
	handleItemMove,
	id,
	index,
	sortableListItem,
	totalItems,
}) => {
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

			handleItemMove({hoverIndex, index: dragIndex});

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
		backgroundColor: isItemBeingDragged ? 'var(--light)' : '',
		border: isItemBeingDragged ? '1px solid var(--primary)' : '',
		cursor: 'grab',
		opacity: isDragging ? 0 : 1,
	};

	return (
		<ClayList.Item
			active={sortableListItem.active}
			className="align-items-center justify-content-between"
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
					<ClayLink href={sortableListItem.editAssetListEntryURL}>
						{sortableListItem.label}
					</ClayLink>
				</ClayList.ItemTitle>
			</ClayList.ItemField>

			<ClayList.ItemField shrink>
				<SortableListItemMoreActions
					index={index}
					itemIsDeleteable={
						!!sortableListItem.deleteAssetListEntryVariationURL
					}
					onDeleteVariation={() =>
						handleItemDelete({
							deleteURL:
								sortableListItem.deleteAssetListEntryVariationURL,
						})
					}
					onReorder={handleItemMove}
					totalItems={totalItems}
				/>
			</ClayList.ItemField>
		</ClayList.Item>
	);
};

SortableListItem.propTypes = {
	handleItemDelete: PropTypes.func.isRequired,
	handleItemMove: PropTypes.func.isRequired,
	id: PropTypes.string.isRequired,
	index: PropTypes.number.isRequired,
	sortableListItem: PropTypes.object.isRequired,
};

export default SortableListItem;

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
import React, {useRef, useState} from 'react';
import {useDrag, useDrop} from 'react-dnd';

import SortableListItemMoreActions from './SortableListItemMoreActions';
import {dragIsOutOfBounds, getDndStyles} from './utils/index';

const ItemTypes = {
	SORTABLE_LIST_ITEM: 'sortableListItem',
};

const SortableListItem = ({
	handleItemDelete,
	handleItemMove,
	handleSavePriority,
	id,
	index,
	sortableListItem,
	totalItems,
}) => {
	const [showDragIcon, setShowDragIcon] = useState(false);

	const ref = useRef(null);

	const [, drop] = useDrop({
		accept: ItemTypes.SORTABLE_LIST_ITEM,

		drop: () => {
			handleSavePriority();
		},

		hover: (item, monitor) => {
			const dragIndex = item.index;
			const hoverIndex = index;

			if (
				!ref.current ||
				dragIndex === hoverIndex ||
				dragIsOutOfBounds({dragIndex, hoverIndex, monitor, ref})
			) {
				return;
			}

			item.index = hoverIndex;
			handleItemMove({hoverIndex, index: dragIndex});
		},
	});

	const [{isDragging, itemBeingDragged}, drag] = useDrag({
		collect: (monitor) => ({
			isDragging: monitor.isDragging(),
			itemBeingDragged: monitor.getItem() || {id: 0},
		}),

		item: {
			id,
			index,
			type: ItemTypes.SORTABLE_LIST_ITEM,
		},
	});

	const isItemBeingDragged = itemBeingDragged.id === id;

	drag(drop(ref));

	const handleReorder = ({direction, index}) => {
		handleItemMove({direction, index, saveAfterMove: true});
	};

	const handleOnMouseEnter = () => {
		setShowDragIcon(true);
	};

	const handleOnMouseLeave = () => {
		setShowDragIcon(false);
	};

	return (
		<ClayList.Item
			active={sortableListItem.active}
			className="align-items-center justify-content-start sortable-list-item"
			flex
			id={`sortableListItem-id-${sortableListItem.assetListEntrySegmentsEntryRelId}`}
			onMouseEnter={handleOnMouseEnter}
			onMouseLeave={handleOnMouseLeave}
			ref={ref}
			style={getDndStyles({isDragging, isItemBeingDragged})}
		>
			<ClayList.ItemField className="sortable-list-item__drag-icon">
				{showDragIcon && <ClayIcon symbol="drag" />}
			</ClayList.ItemField>

			<ClayList.ItemField className="sortable-list-item__label">
				<ClayList.ItemTitle>
					<ClayLink href={sortableListItem.editAssetListEntryURL}>
						{sortableListItem.name}
					</ClayLink>
				</ClayList.ItemTitle>
			</ClayList.ItemField>

			<ClayList.ItemField className="sortable-list-item__more-icon">
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
					onReorder={handleReorder}
					totalItems={totalItems}
				/>
			</ClayList.ItemField>
		</ClayList.Item>
	);
};

SortableListItem.propTypes = {
	handleItemDelete: PropTypes.func.isRequired,
	handleItemMove: PropTypes.func.isRequired,
	handleSavePriority: PropTypes.func.isRequired,
	id: PropTypes.string.isRequired,
	index: PropTypes.number.isRequired,
	sortableListItem: PropTypes.object.isRequired,
};

export default SortableListItem;

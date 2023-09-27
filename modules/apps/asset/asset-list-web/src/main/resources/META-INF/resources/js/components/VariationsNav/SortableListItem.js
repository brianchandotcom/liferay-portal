/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		drop: (item) => {
			handleSavePriority({
				itemIndex: item.index,
				itemName: sortableListItem.name,
			});
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
			className="align-items-center justify-content-start mb-1 sortable-list-item"
			flex
			id={`sortableListItem-id-${sortableListItem.assetListEntrySegmentsEntryRelId}`}
			onMouseEnter={handleOnMouseEnter}
			onMouseLeave={handleOnMouseLeave}
			ref={ref}
			style={getDndStyles({isDragging, isItemBeingDragged})}
		>
			<ClayList.ItemField className="pl-0 sortable-list-item__drag-icon">
				{showDragIcon && <ClayIcon symbol="drag" />}
			</ClayList.ItemField>

			<ClayList.ItemField className="sortable-list-item__label">
				<ClayList.ItemTitle className="align-items-stretch">
					<ClayLink
						className="align-items-center d-flex w-100"
						href={sortableListItem.editAssetListEntryURL}
					>
						<span className="c-inner flex-grow-1" tabIndex="-1">
							{sortableListItem.name}
						</span>
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
					sortableListItemName={sortableListItem.name}
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

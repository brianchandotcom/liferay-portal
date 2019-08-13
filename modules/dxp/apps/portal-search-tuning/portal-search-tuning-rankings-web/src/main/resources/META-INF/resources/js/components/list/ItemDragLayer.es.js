/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import DRAG_TYPES from '../../utils/drag-types.es';
import ItemDragPreview from './ItemDragPreview.es';
import React from 'react';
import {DragLayer as DndDragLayer} from 'react-dnd';

const LAYER_STYLES = {
	height: '100%',
	left: 0,
	pointerEvents: 'none',
	position: 'fixed',
	top: 0,
	width: '100%',
	zIndex: 100
};

const OFFSET_X = 8;

const OFFSET_Y = 16;

function getItemStyles(props) {
	const {currentOffset, initialOffset} = props;

	const {x, y} = currentOffset || {};

	const transform = `translate(${x - OFFSET_X}px, ${y - OFFSET_Y}px)`;

	return !initialOffset || !currentOffset
		? {
				display: 'none'
		  }
		: {
				WebkitTransform: transform,
				transform
		  };
}

const ItemDragLayer = props => {
	const {dragging, item, itemType} = props;

	function renderItem() {
		return itemType === DRAG_TYPES.LIST_ITEM ? (
			<ItemDragPreview {...item} />
		) : null;
	}

	return dragging ? (
		<div className="drag-layer" style={LAYER_STYLES}>
			<div style={getItemStyles(props)}>{renderItem()}</div>
		</div>
	) : null;
};

export default DndDragLayer(monitor => ({
	currentOffset: monitor.getSourceClientOffset(),
	dragging: monitor.isDragging(),
	initialOffset: monitor.getInitialSourceClientOffset(),
	item: monitor.getItem(),
	itemType: monitor.getItemType()
}))(ItemDragLayer);

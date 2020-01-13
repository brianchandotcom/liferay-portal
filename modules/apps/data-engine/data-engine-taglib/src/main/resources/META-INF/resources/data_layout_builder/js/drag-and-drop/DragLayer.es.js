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

import React from 'react';
import {useDragLayer} from 'react-dnd';

const layerStyles = {
	cursor: 'grabbing',
	height: '100%',
	left: 0,
	pointerEvents: 'none',
	position: 'fixed',
	top: 0,
	width: '100%',
	zIndex: 150
};

function getItemStyles(initialOffset, currentOffset) {
	if (!initialOffset || !currentOffset) {
		return {
			display: 'none'
		};
	}

	const {x, y} = currentOffset;
	const transform = `translate(${x}px, ${y}px)`;

	return {
		WebkitTransform: transform,
		transform
	};
}

export default () => {
	const {currentOffset, initialOffset, isDragging, item} = useDragLayer(
		monitor => ({
			currentOffset: monitor.getSourceClientOffset(),
			initialOffset: monitor.getInitialSourceClientOffset(),
			isDragging: monitor.isDragging(),
			item: monitor.getItem(),
			itemType: monitor.getItemType()
		})
	);

	if (!isDragging) {
		return null;
	}

	const {preview} = item;

	return (
		<div style={{...layerStyles, zIndex: 200}}>
			<div
				style={getItemStyles(initialOffset, currentOffset, isDragging)}
			>
				{preview && preview()}
			</div>
		</div>
	);
};

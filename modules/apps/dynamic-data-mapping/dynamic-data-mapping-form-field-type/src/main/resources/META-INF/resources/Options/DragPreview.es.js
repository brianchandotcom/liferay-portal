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

import React, {useRef} from 'react';
import {useDragLayer} from 'react-dnd';
import ReactDOM from 'react-dom';

import {OPTIONS_TYPES} from './DnD.es';

const layerStyles = {
	height: '100%',
	left: 0,
	pointerEvents: 'none',
	position: 'fixed',
	top: 0,
	width: '100%',
	zIndex: 100,
};

const getItemStyles = (currentOffset, ref) => {
	if (!currentOffset || !ref.current) {
		return {
			display: 'none',
		};
	}

	const {x, y} = currentOffset;
	const transform = `translate(${x}px, ${y}px)`;

	return {
		WebkitTransform: transform,
		transform,
	};
};

export default function DragPreview({children, component: Component}) {
	const ref = useRef();

	const {currentOffset, isDragging, item} = useDragLayer((monitor) => ({
		currentOffset: monitor.getClientOffset(),
		isDragging: monitor.isDragging(),
		item: monitor.getItem(),
	}));

	if (!isDragging || (isDragging && item.type !== OPTIONS_TYPES.OPTION)) {
		return null;
	}

	return ReactDOM.createPortal(
		<div style={layerStyles}>
			<Component
				{...item.option}
				className="dragging"
				ref={ref}
				style={getItemStyles(currentOffset, ref)}
			>
				{children({index: item.position, option: item.option})}
			</Component>
		</div>,
		document.body
	);
}

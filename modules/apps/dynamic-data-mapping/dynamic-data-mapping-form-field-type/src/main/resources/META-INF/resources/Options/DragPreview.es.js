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

import {ReactPortal} from '@liferay/frontend-js-react-web';
import React, {useRef} from 'react';
import {useDragLayer} from 'react-dnd';

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

const getItemStyles = (currentOffset, ref, initialOffset) => {
	if (!currentOffset || !ref.current || !initialOffset) {
		return {
			display: 'none',
		};
	}

	const transform = `translate(${initialOffset.x}px, ${currentOffset.y}px)`;

	return {
		WebkitTransform: transform,
		transform,
	};
};

export default function DragPreview({children, component: Component}) {
	const ref = useRef();

	const {currentOffset, initialOffset, isDragging, item} = useDragLayer(
		(monitor) => ({
			currentOffset: monitor.getClientOffset(),
			initialOffset: monitor.getInitialClientOffset(),
			isDragging: monitor.isDragging(),
			item: monitor.getItem(),
		})
	);

	if (!isDragging || (isDragging && item.type !== OPTIONS_TYPES.OPTION)) {
		return null;
	}

	return (
		<ReactPortal>
			<div style={layerStyles}>
				<Component
					{...item.option}
					className="dragging"
					ref={ref}
					style={getItemStyles(currentOffset, ref, initialOffset)}
				>
					{children({index: item.position, option: item.option})}
				</Component>
			</div>
		</ReactPortal>
	);
}

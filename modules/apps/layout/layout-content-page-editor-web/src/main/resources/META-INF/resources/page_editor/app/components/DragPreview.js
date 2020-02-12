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

const getItemStyles = (currentOffset, ref) => {
	if (!currentOffset || !ref.current) {
		return {
			display: 'none'
		};
	}

	const rect = ref.current.getBoundingClientRect();
	const x = currentOffset.x - rect.width * 0.5;
	const y = currentOffset.y - rect.height * 0.5;
	const transform = `translate(${x}px, ${y}px)`;

	return {
		WebkitTransform: transform,
		transform
	};
};

export default function DragPreview() {
	const ref = useRef();

	const {currentOffset, isDragging, item} = useDragLayer(monitor => ({
		currentOffset: monitor.getClientOffset(),
		isDragging: monitor.isDragging(),
		item: monitor.getItem()
	}));

	if (!isDragging) {
		return null;
	}

	return (
		<div className="page-editor__drag-preview">
			<div
				className="page-editor__drag-preview__content"
				ref={ref}
				style={getItemStyles(currentOffset, ref)}
			>
				{item && item.name
					? item.name
					: Liferay.Language.get('element')}
			</div>
		</div>
	);
}

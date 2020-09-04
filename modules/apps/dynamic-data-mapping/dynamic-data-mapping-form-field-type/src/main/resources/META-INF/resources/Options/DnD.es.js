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

import classNames from 'classnames';
import React, {useEffect} from 'react';
import {useDrag, useDrop} from 'react-dnd';
import {getEmptyImage} from 'react-dnd-html5-backend';

export const OPTIONS_TYPES = {
	OPTION: 'option',
};

export default function DnD({children, index, onDragEnd = () => {}, option}) {
	const [{isDragging}, drag, preview] = useDrag({
		collect: (monitor) => ({
			isDragging: monitor.isDragging(),
		}),
		end: (item, monitor) => {
			const result = monitor.getDropResult();

			if (item && result) {
				onDragEnd(result);
			}
		},
		item: {
			option,
			position: index,
			type: OPTIONS_TYPES.OPTION,
		},
	});

	const [{canDrop, isOver}, drop] = useDrop({
		accept: OPTIONS_TYPES.OPTION,
		collect: (monitor) => ({
			canDrop: monitor.canDrop(),
			isOver: monitor.isOver(),
		}),
		drop: (item) => ({
			itemPosition: item.position,
			targetPosition: index,
		}),
	});

	useEffect(() => {
		preview(getEmptyImage(), {captureDraggingState: true});
	}, [preview]);

	return (
		<>
			<div
				className={classNames('ddm-options-target', {
					targetOver: isOver && canDrop,
				})}
				ref={drop}
			></div>

			{React.cloneElement(children, {
				...children.props,
				className: {'dragged ddm-source-dragging': isDragging},
				ref: drag,
			})}
		</>
	);
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

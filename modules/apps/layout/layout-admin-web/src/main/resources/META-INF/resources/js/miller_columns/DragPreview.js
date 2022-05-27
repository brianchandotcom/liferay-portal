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

import {sub} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';
import {useDragLayer} from 'react-dnd';

const getItemStyles = (currentOffset, ref, rtl) => {
	if (!currentOffset || !ref.current) {
		return {
			display: 'none',
		};
	}

	const rect = ref.current.getBoundingClientRect();
	const x = rtl
		? currentOffset.x + rect.width * 0.5 - window.innerWidth
		: currentOffset.x - rect.width * 0.5;
	const y = currentOffset.y - rect.height * 0.5;

	const transform = `translate(${x}px, ${y}px)`;

	return {
		WebkitTransform: transform,
		transform,
	};
};

export default function DragPreview({rtl}) {
	const ref = useRef();

	const {currentOffset, isDragging, items} = useDragLayer((monitor) => ({
		currentOffset: monitor.getClientOffset(),
		isDragging: monitor.isDragging(),
		items: monitor.getItem()?.items,
	}));

	const [label, setLabel] = useState();

	useEffect(() => {
		if (items) {
			if (items.length > 1) {
				setLabel(sub(Liferay.Language.get('x-elements'), items.length));
			}
			else {
				const [item] = items;

				setLabel(item.title);
			}
		}
	}, [items]);

	if (!isDragging) {
		return null;
	}

	return (
		<div className="miller-columns__drag-preview">
			<div
				className="miller-columns__drag-preview__content"
				ref={ref}
				style={getItemStyles(currentOffset, ref, rtl)}
			>
				{label}
			</div>
		</div>
	);
}

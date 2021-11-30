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

const VARIATIONS_PRIORITY_PARAM_NAME = 'variationsPriority';

const buildItemsPriorityURL = ({items, namespace, url}) => {
	const urlWithPriorityParam = `${url}&${namespace}${VARIATIONS_PRIORITY_PARAM_NAME}=${items
		.map((item) => item.assetListEntrySegmentsEntryRelId)
		.join(',')}`;

	return urlWithPriorityParam;
};

const dragIsOutOfBounds = ({dragIndex, hoverIndex, monitor, ref}) => {
	const hoverBoundingRect = ref.current.getBoundingClientRect();

	const verticalMiddle =
		(hoverBoundingRect.bottom - hoverBoundingRect.top) / 2;

	const mousePosition = monitor.getClientOffset();

	const pixelsToTop = mousePosition.y - hoverBoundingRect.top;

	const draggingUpwards =
		dragIndex > hoverIndex && pixelsToTop > verticalMiddle * 1.5;

	const draggingDownwards =
		dragIndex < hoverIndex && pixelsToTop < verticalMiddle / 2;

	return draggingDownwards || draggingUpwards;
};

const getDndStyles = ({isDragging, isItemBeingDragged}) => ({
	backgroundColor: isItemBeingDragged ? 'var(--gray-200)' : '',
	borderColor: isItemBeingDragged ? '#80ACFF' : 'transparent',
	color: isItemBeingDragged ? 'var(--gray-500)' : '',
	cursor: 'grab',
	opacity: isDragging ? 0.6 : 1,
});

export {buildItemsPriorityURL, dragIsOutOfBounds, getDndStyles};

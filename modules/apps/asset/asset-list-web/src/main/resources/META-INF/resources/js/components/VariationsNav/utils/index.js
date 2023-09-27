/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

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

import {TARGET_POSITIONS} from './constants/targetPositions';

export default function getDropData({
	isElevation,
	layoutDataRef,
	sourceItemId,
	targetItemId,
	targetPosition,
}) {
	const targetItem = layoutDataRef.current.items[targetItemId];
	const targetParentItem = layoutDataRef.current.items[targetItem.parentId];

	let dropItemId;
	let position;

	if (isElevation) {
		position = Math.min(
			targetParentItem.children.includes(sourceItemId)
				? targetParentItem.children.length - 1
				: targetParentItem.children.length,
			getSiblingPosition(
				sourceItemId,
				targetItem,
				targetParentItem,
				targetPosition
			)
		);

		dropItemId = targetParentItem.itemId;
	}
	else {
		position = targetItem.children.includes(sourceItemId)
			? targetItem.children.length - 1
			: targetItem.children.length;

		dropItemId = targetItem.itemId;
	}

	return {dropItemId, position};
}

function getSiblingPosition(
	sourceItemId,
	targetItem,
	targetParentItem,
	targetPosition
) {
	const dropItemPosition = targetParentItem.children.indexOf(sourceItemId);
	const siblingPosition = targetParentItem.children.indexOf(
		targetItem.itemId
	);

	if (
		targetPosition === TARGET_POSITIONS.BOTTOM ||
		targetPosition === TARGET_POSITIONS.RIGHT
	) {
		return siblingPosition + 1;
	}
	else if (
		dropItemPosition !== -1 &&
		dropItemPosition < siblingPosition &&
		siblingPosition > 0
	) {
		return siblingPosition - 1;
	}

	return siblingPosition;
}

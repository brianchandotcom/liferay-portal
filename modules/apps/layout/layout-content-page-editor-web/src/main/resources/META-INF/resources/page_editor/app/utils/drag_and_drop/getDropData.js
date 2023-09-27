/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

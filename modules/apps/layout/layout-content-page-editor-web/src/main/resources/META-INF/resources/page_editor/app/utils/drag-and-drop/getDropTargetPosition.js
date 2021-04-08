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

/**
 * Returns the cursor vertical position (extracted from provided dnd monitor)
 * @param {number} clientOffsetY
 * @param {DOMRect} hoverBoundingRect
 * @param {number} elevationBorderSize
 * @return {Array} Returns a tuple with targetPositionWithMiddle and
 *  targetPositionWithoutMiddle
 */
export default function getDropTargetPosition(
	clientOffsetY,
	hoverBoundingRect,
	elevationBorderSize
) {
	const hoverMiddleY = hoverBoundingRect.top + hoverBoundingRect.height / 2;

	const targetPositionWithoutMiddle =
		clientOffsetY < hoverMiddleY
			? TARGET_POSITIONS.TOP
			: TARGET_POSITIONS.BOTTOM;

	const targetPositionWithMiddle =
		clientOffsetY < hoverBoundingRect.bottom - elevationBorderSize &&
		clientOffsetY > hoverBoundingRect.top + elevationBorderSize
			? TARGET_POSITIONS.MIDDLE
			: targetPositionWithoutMiddle;

	return [targetPositionWithMiddle, targetPositionWithoutMiddle];
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	clientOffset,
	elevationBorderSize,
	targetPositions,
	targetData
) {
	const hoverMiddle = targetData.start + targetData.length / 2;

	const targetPositionWithoutMiddle =
		clientOffset < hoverMiddle
			? targetPositions.start
			: targetPositions.end;

	const targetPositionWithMiddle =
		clientOffset < targetData.end - elevationBorderSize &&
		clientOffset > targetData.start + elevationBorderSize
			? TARGET_POSITIONS.MIDDLE
			: targetPositionWithoutMiddle;

	return [targetPositionWithMiddle, targetPositionWithoutMiddle];
}

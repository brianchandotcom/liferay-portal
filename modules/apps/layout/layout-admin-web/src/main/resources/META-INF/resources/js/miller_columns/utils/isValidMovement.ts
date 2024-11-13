/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DropPosition} from '../constants/dropPositions';
import {MillerColumnItem} from '../types/MillerColumnItem';

type Props = {
	dropPosition: DropPosition;
	isPrivateLayoutsEnabled: boolean;
	sources: MillerColumnItem[];
	target: MillerColumnItem;
};

export function isValidMovement({
	dropPosition,
	isPrivateLayoutsEnabled,
	sources,
	target,
}: Props) {
	if (!sources.length || !target) {
		return false;
	}

	if (sources.some((item) => item.id === target.id)) {
		return false;
	}

	if (
		sources.some(
			(source) =>
				!(
					(((isPrivateLayoutsEnabled && target.parentId) ||
						!isPrivateLayoutsEnabled) &&
						target.columnIndex <= source.columnIndex) ||
					(target.columnIndex > source.columnIndex && !source.active)
				)
		)
	) {
		return false;
	}

	if (dropPosition === 'top') {
		return !sources.some(
			(source) =>
				!(
					target.columnIndex !== source.columnIndex ||
					target.itemIndex < source.itemIndex ||
					target.itemIndex > source.itemIndex + 1
				)
		);
	}
	else if (dropPosition === 'bottom') {
		return !sources.some(
			(source) =>
				!(
					target.columnIndex !== source.columnIndex ||
					target.itemIndex > source.itemIndex ||
					target.itemIndex < source.itemIndex - 1
				)
		);
	}
	else if (dropPosition === 'middle') {
		return !sources.some(
			(source) => !(target.id !== source.parentId && target.parentable)
		);
	}
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DropPosition} from '../constants/dropPositions';
import {MillerColumnItem} from '../types/MillerColumnItem';

type Props = {
	allowSelfTarget?: boolean;
	dropPosition: DropPosition;
	sources: MillerColumnItem[];
	target: MillerColumnItem;
};

export function isValidMovement({
	allowSelfTarget = false,
	dropPosition,
	sources,
	target,
}: Props) {
	if (!sources.length || !target) {
		return false;
	}

	if (
		sources.some(
			(source) =>
				(source.id === target.id && !allowSelfTarget) ||
				source.id === target.parentId
		)
	) {
		return false;
	}

	if (dropPosition === 'top') {
		return sources.every(
			(source) =>
				allowSelfTarget ||
				target.columnIndex !== source.columnIndex ||
				target.itemIndex < source.itemIndex ||
				target.itemIndex > source.itemIndex + 1
		);
	}
	else if (dropPosition === 'bottom') {
		return sources.every(
			(source) =>
				allowSelfTarget ||
				target.columnIndex !== source.columnIndex ||
				target.itemIndex > source.itemIndex ||
				target.itemIndex < source.itemIndex - 1
		);
	}
	else if (dropPosition === 'middle') {
		if (!target.parentable) {
			return false;
		}

		return sources.every((source) => target.id !== source.parentId);
	}
}

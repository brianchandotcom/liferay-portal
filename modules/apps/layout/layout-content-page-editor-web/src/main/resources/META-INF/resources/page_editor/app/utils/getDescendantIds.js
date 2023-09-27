/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isLayoutDataItemDeleted} from './isLayoutDataItemDeleted';

export function getDescendantIds(layoutData, itemId) {
	const item = layoutData.items[itemId];

	const descendantIds = [...item.children];

	item.children.forEach((childId) => {
		if (!isLayoutDataItemDeleted(layoutData, childId)) {
			descendantIds.push(...getDescendantIds(layoutData, childId));
		}
	});

	return descendantIds;
}

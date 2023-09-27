/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';

export default function hasDropZoneChild(item, layoutData) {
	return item.children.some((childrenId) => {
		const children = layoutData.items[childrenId];

		if (!children) {
			return false;
		}

		return children.type === LAYOUT_DATA_ITEM_TYPES.dropZone
			? true
			: hasDropZoneChild(children, layoutData);
	});
}

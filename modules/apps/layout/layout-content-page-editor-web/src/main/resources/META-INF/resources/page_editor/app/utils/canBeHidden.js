/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FRAGMENT_ENTRY_TYPES} from '../config/constants/fragmentEntryTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import canBeRemoved from './canBeRemoved';
import {isItemHidden} from './isItemHidden';

export default function canBeHidden(
	item,
	layoutData,
	masterLayoutData,
	fragmentEntryLinks,
	selectedViewportSize
) {
	switch (item.type) {
		case !canBeRemoved(item, layoutData):
			return false;
		case masterLayoutData &&
			Object.keys(masterLayoutData.items).includes(item.itemId):
			return false;
		case item.type !== LAYOUT_DATA_ITEM_TYPES.fragment:
			return true;
		case isItemHidden(layoutData, item.itemId, selectedViewportSize):
			return true;

		default: {
			const fragmentEntryLink =
				fragmentEntryLinks[item.config.fragmentEntryLinkId];

			return (
				fragmentEntryLink.fragmentEntryType !==
				FRAGMENT_ENTRY_TYPES.input
			);
		}
	}
}

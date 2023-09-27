/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import hasDropZoneChild from '../components/layout_data_items/hasDropZoneChild';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import getWidget from '../utils/getWidget';

export default function canBeDuplicated(
	fragmentEntryLinks,
	item,
	layoutData,
	widgets
) {
	switch (item.type) {
		case LAYOUT_DATA_ITEM_TYPES.collection:
			return true;

		case LAYOUT_DATA_ITEM_TYPES.container:
		case LAYOUT_DATA_ITEM_TYPES.form:
		case LAYOUT_DATA_ITEM_TYPES.row:
			return !hasDropZoneChild(item, layoutData);

		case LAYOUT_DATA_ITEM_TYPES.fragment: {
			const fragmentEntryLink =
				fragmentEntryLinks[item.config.fragmentEntryLinkId];

			const portletId = fragmentEntryLink.editableValues.portletId;

			const widget = portletId && getWidget(widgets, portletId);

			return (
				(!widget || widget.instanceable) &&
				!hasDropZoneChild(item, layoutData)
			);
		}

		default:
			return false;
	}
}

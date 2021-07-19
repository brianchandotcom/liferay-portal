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

import hasDropZoneChild from '../components/layout-data-items/hasDropZoneChild';
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

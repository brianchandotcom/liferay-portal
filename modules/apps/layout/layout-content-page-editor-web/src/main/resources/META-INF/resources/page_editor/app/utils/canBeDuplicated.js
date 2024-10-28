/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import hasDropZoneChild from '../components/layout_data_items/hasDropZoneChild';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import getWidget from '../utils/getWidget';
import isStepper from './isStepper';

export default function canBeDuplicated(
	fragmentEntryLinks,
	item,
	layoutData,
	getWidgets
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

			const portletId = fragmentEntryLink?.editableValues.portletId;

			if (hasDropZoneChild(item, layoutData)) {
				return false;
			}

			if (portletId) {
				const widget = getWidget(getWidgets(), portletId);

				if (widget && !widget.instanceable) {
					return false;
				}
			}

			if (isStepper(fragmentEntryLink)) {
				return false;
			}

			return true;
		}

		default:
			return false;
	}
}

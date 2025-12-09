/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../../types/State';
import {LayoutDataItem} from '../../types/layout_data/LayoutData';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import {WidgetSet} from '../actions/updateWidgets';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import getWidget from './getWidget';

export function getItemIcon(
	fragmentEntryLinks: FragmentEntryLinkMap,
	fragments: State['fragments'],
	item: LayoutDataItem,
	getWidgets: () => WidgetSet[]
) {
	if (!item) {
		return null;
	}

	const fragmentEntries = fragments.flatMap(
		(collection) => collection.fragmentEntries
	);

	if (item.type === LAYOUT_DATA_ITEM_TYPES.fragment) {
		const fragmentEntryLink =
			fragmentEntryLinks[item.config.fragmentEntryLinkId];

		if (fragmentEntryLink.portletId) {
			const widget = getWidget(getWidgets(), fragmentEntryLink.portletId);

			return widget?.instanceable ? 'square-hole-multi' : 'square-hole';
		}

		return fragmentEntries.find(
			(fragment) =>
				fragment.fragmentEntryKey === fragmentEntryLink.fragmentEntryKey
		)?.icon;
	}

	return fragmentEntries.find(
		(fragment) => 'itemType' in fragment && fragment.itemType === item.type
	)?.icon;
}

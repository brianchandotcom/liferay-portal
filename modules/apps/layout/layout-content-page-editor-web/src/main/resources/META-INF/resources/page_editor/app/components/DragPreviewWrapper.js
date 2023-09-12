/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DragPreview} from '@liferay/layout-js-components-web';
import React from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {useSelector} from '../contexts/StoreContext';
import getWidget from '../utils/getWidget';

function getItemIcon(fragmentEntryLinks, fragments, item, widgets) {
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
			const widget = getWidget(widgets, fragmentEntryLink.portletId);

			return widget?.instanceable ? 'square-hole-multi' : 'square-hole';
		}

		return fragmentEntries.find(
			(fragment) =>
				fragment.fragmentEntryKey === fragmentEntryLink.fragmentEntryKey
		)?.icon;
	}

	return fragmentEntries.find((fragment) => fragment.type === item.type)
		?.icon;
}

export default function DragPreviewWrapper() {
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const fragments = useSelector((state) => state.fragments);
	const widgets = useSelector((state) => state.widgets);

	return (
		<DragPreview
			getIcon={(item) => {
				return (
					item?.icon ??
					getItemIcon(fragmentEntryLinks, fragments, item, widgets)
				);
			}}
		/>
	);
}

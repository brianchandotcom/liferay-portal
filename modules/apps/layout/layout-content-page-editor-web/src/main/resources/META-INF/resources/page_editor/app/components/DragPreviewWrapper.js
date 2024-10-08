/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DragPreview} from '@liferay/layout-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {useActiveItemIds} from '../contexts/ControlsContext';
import {useSelector} from '../contexts/StoreContext';
import {useGetWidgets} from '../contexts/WidgetsContext';
import getWidget from '../utils/getWidget';

function getItemIcon(fragmentEntryLinks, fragments, item, getWidgets) {
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

	return fragmentEntries.find((fragment) => fragment.type === item.type)
		?.icon;
}

export function getIcon({
	activeItemIds,
	fragmentEntryLinks,
	fragments,
	getWidgets,
	item,
}) {
	if (activeItemIds.length > 1) {
		return null;
	}

	return (
		item?.icon ??
		getItemIcon(fragmentEntryLinks, fragments, item, getWidgets)
	);
}

export function getLabel({activeItemIds, item}) {
	return activeItemIds.length > 1
		? sub(Liferay.Language.get('x-items'), activeItemIds.length)
		: item?.name;
}

export default function DragPreviewWrapper() {
	const activeItemIds = useActiveItemIds();
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const fragments = useSelector((state) => state.fragments);
	const getWidgets = useGetWidgets();

	return (
		<DragPreview
			getIcon={(item) =>
				getIcon({
					activeItemIds,
					fragmentEntryLinks,
					fragments,
					getWidgets,
					item,
				})
			}
			getLabel={(item) => getLabel({activeItemIds, item})}
		/>
	);
}

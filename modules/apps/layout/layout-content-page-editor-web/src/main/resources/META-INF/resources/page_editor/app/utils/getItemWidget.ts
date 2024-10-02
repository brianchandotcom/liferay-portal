/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FragmentLayoutDataItem} from '../..//types/layout_data/FragmentLayoutDataItem';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import {Widget, WidgetSet} from '../actions/updateWidgets';
import getWidget from './getWidget';

export default function getItemWidget(
	item: FragmentLayoutDataItem,
	fragmentEntryLinks: FragmentEntryLinkMap,
	widgets: WidgetSet[]
): Widget | null {
	if (!item.config) {
		return null;
	}

	const {fragmentEntryLinkId} = item.config;

	if (!fragmentEntryLinkId) {
		return null;
	}

	const fragmentEntryLink = fragmentEntryLinks[fragmentEntryLinkId];

	if (fragmentEntryLink.portletId) {
		return getWidget(widgets, fragmentEntryLink.portletId);
	}

	return null;
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../../types/State';
import {WidgetSet} from '../actions/updateWidgets';
import {getItemIcon} from '../utils/getItemIcon';

import type {
	LayoutData,
	LayoutDataItem,
} from '../../types/layout_data/LayoutData';
import type {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';

export default function selectLayoutDataItemIcon(
	{
		fragmentEntryLinks,
		fragments,
	}: {
		fragmentEntryLinks: FragmentEntryLinkMap;
		fragments: State['fragments'];
		layoutData: LayoutData;
	},
	item: LayoutDataItem,
	getWidgets: () => WidgetSet[]
) {
	return getItemIcon(fragmentEntryLinks, fragments, item, getWidgets);
}

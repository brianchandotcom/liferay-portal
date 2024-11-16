/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutDataItem} from '../../types/layout_data/LayoutData';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import {isFragment} from './isFragment';

export default function isItemWidget(
	item: LayoutDataItem,
	fragmentEntryLinks: FragmentEntryLinkMap
) {
	if (!isFragment(item)) {
		return false;
	}

	const {fragmentEntryLinkId} = item.config;

	if (!fragmentEntryLinkId) {
		return false;
	}

	const fragmentEntryLink = fragmentEntryLinks[fragmentEntryLinkId];

	return Boolean(fragmentEntryLink.portletId);
}

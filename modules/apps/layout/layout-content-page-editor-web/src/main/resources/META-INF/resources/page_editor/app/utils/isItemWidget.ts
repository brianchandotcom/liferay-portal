/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FragmentLayoutDataItem} from '../..//types/layout_data/FragmentLayoutDataItem';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';

export default function isItemWidget(
	item: FragmentLayoutDataItem,
	fragmentEntryLinks: FragmentEntryLinkMap
) {
	if (!item.config) {
		return false;
	}

	const {fragmentEntryLinkId} = item.config;

	if (!fragmentEntryLinkId) {
		return false;
	}

	const fragmentEntryLink = fragmentEntryLinks[fragmentEntryLinkId];

	return Boolean(fragmentEntryLink.portletId);
}

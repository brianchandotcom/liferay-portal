/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {State} from '../../types/State';
import {FragmentLayoutDataItem} from '../../types/layout_data/FragmentLayoutDataItem';

export default function selectFragmentEntryLink(
	state: State,
	layoutDataItem: FragmentLayoutDataItem
) {
	return state.fragmentEntryLinks[
		(
			state.layoutData.items[
				layoutDataItem.itemId
			] as FragmentLayoutDataItem
		).config.fragmentEntryLinkId
	];
}

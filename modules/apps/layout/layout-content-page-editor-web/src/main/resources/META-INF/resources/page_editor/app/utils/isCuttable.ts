/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData, LayoutDataItem} from '../../types/layout_data/LayoutData';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import canBeRemoved from './canBeRemoved';
import {isFragment} from './isFragment';
import isStepper from './isStepper';

export default function isCuttable(
	itemId: LayoutDataItem['itemId'],
	fragmentEntryLinks: FragmentEntryLinkMap,
	layoutData: LayoutData
) {
	const item = layoutData.items[itemId];

	return (
		Liferay.FeatureFlags['LPD-18221'] &&
		canBeRemoved(item, layoutData) &&
		!(
			isFragment(item) &&
			isStepper(fragmentEntryLinks[item.config.fragmentEntryLinkId])
		)
	);
}

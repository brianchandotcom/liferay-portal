/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData, LayoutDataItem} from '../../types/layout_data/LayoutData';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import {hasFieldType} from './hasFieldType';

export function getStepperChild(
	parent: LayoutDataItem,
	layoutData: LayoutData,
	fragmentEntryLinks: FragmentEntryLinkMap
): LayoutDataItem | null {
	for (const childId of parent.children) {
		const child = layoutData.items[childId];

		if (!child) {
			continue;
		}

		if (!('fragmentEntryLinkId' in child.config)) {
			return getStepperChild(child, layoutData, fragmentEntryLinks);
		}

		if (
			hasFieldType({
				fragmentEntryLinks,
				itemId: childId,
				layoutData,
				requiredFieldType: 'stepper',
			})
		) {
			return child;
		}
	}

	return null;
}

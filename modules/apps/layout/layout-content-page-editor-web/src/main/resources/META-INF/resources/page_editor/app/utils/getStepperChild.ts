/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormLayoutDataItem} from '../../types/layout_data/FormLayoutDataItem';
import {FragmentLayoutDataItem} from '../../types/layout_data/FragmentLayoutDataItem';
import {LayoutData} from '../../types/layout_data/LayoutData';
import {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
import isStepper from './isStepper';

export function getStepperChild(
	form: FormLayoutDataItem,
	layoutData: LayoutData | null,
	fragmentEntryLinks: FragmentEntryLinkMap | null
): FragmentLayoutDataItem | null {
	if (!layoutData || !fragmentEntryLinks) {
		return null;
	}

	for (const childId of form.children) {
		const child = layoutData?.items[childId];

		if (!child || !('fragmentEntryLinkId' in child.config)) {
			continue;
		}

		const fragment =
			fragmentEntryLinks?.[child.config.fragmentEntryLinkId!];

		if (isStepper(fragment)) {
			return child as FragmentLayoutDataItem;
		}
	}

	return null;
}

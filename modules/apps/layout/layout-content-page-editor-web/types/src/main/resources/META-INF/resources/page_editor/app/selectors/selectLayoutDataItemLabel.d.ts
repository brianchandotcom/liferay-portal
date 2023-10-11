/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {LayoutDataItem} from '../../types/layout_data/LayoutData';
import type {FragmentEntryLinkMap} from '../actions/addFragmentEntryLinks';
export default function selectLayoutDataItemLabel(
	{
		fragmentEntryLinks,
	}: {
		fragmentEntryLinks: FragmentEntryLinkMap;
	},
	item: LayoutDataItem,
	{
		useCustomName,
	}?: {
		useCustomName?: boolean | undefined;
	}
): string;

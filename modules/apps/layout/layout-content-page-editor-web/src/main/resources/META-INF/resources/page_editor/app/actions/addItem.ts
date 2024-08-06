/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ADD_ITEM} from './types';

import type {LayoutData} from '../../types/layout_data/LayoutData';

export default function addItem({
	fragmentEntryLinkIds,
	itemIds,
	layoutData,
	portletIds = [],
}: {
	fragmentEntryLinkIds: string[];
	itemIds: string[];
	layoutData: LayoutData;
	portletIds: string[];
}) {
	return {
		fragmentEntryLinkIds,
		itemIds,
		layoutData,
		portletIds,
		type: ADD_ITEM,
	} as const;
}

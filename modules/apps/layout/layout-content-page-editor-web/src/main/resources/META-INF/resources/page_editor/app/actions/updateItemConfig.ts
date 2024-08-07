/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_ITEM_CONFIG} from './types';

import type {LayoutData} from '../../types/layout_data/LayoutData';

export default function updateItemConfig({
	itemIds,
	layoutData,
}: {
	itemIds: string[];
	layoutData: LayoutData;
}) {
	return {
		itemIds,
		layoutData,
		type: UPDATE_ITEM_CONFIG,
	} as const;
}

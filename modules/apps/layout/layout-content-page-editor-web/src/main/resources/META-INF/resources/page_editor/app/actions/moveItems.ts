/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MOVE_ITEM} from './types';

import type {LayoutData} from '../../types/layout_data/LayoutData';

export default function moveItems({
	itemIds,
	layoutData,
}: {
	itemIds: string[];
	layoutData: LayoutData;
}) {
	return {
		itemIds,
		layoutData,
		type: MOVE_ITEM,
	} as const;
}

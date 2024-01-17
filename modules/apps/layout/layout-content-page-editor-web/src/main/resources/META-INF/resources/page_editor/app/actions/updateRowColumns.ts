/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_ROW_COLUMNS} from './types';

import type {LayoutData} from '../../types/layout_data/LayoutData';

export default function updateRowColumns({
	itemId,
	layoutData,
	numberOfColumns,
}: {
	itemId: string;
	layoutData: LayoutData;
	numberOfColumns: number;
}) {
	return {
		itemId,
		layoutData,
		numberOfColumns,
		type: UPDATE_ROW_COLUMNS,
	} as const;
}

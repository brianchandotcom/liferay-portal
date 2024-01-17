/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {LayoutData} from '../../types/layout_data/LayoutData';
export default function updateItemConfig({
	itemId,
	layoutData,
	overridePreviousConfig,
}: {
	itemId: string;
	layoutData: LayoutData;
	overridePreviousConfig?: boolean;
}): {
	readonly itemId: string;
	readonly layoutData: LayoutData;
	readonly overridePreviousConfig: boolean;
	readonly type: 'UPDATE_ITEM_CONFIG';
};

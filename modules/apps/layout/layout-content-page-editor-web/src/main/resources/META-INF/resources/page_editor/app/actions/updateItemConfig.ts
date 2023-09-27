/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {UPDATE_ITEM_CONFIG} from './types';

import type {LayoutData} from '../../types/LayoutData';
import type {PageContent} from './addItem';

export default function updateItemConfig({
	itemId,
	layoutData,
	overridePreviousConfig = false,
	pageContents,
}: {
	itemId: string;
	layoutData: LayoutData;
	overridePreviousConfig?: boolean;
	pageContents: PageContent[];
}) {
	return {
		itemId,
		layoutData,
		overridePreviousConfig,
		pageContents,
		type: UPDATE_ITEM_CONFIG,
	} as const;
}

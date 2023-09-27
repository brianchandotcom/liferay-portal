/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DELETE_ITEM} from './types';

import type {LayoutData} from '../../types/LayoutData';
import type {PageContent} from './addItem';

export default function deleteItem({
	fragmentEntryLinkIds,
	itemId,
	layoutData,
	pageContents,
	portletIds = [],
}: {
	fragmentEntryLinkIds: string[];
	itemId: string;
	layoutData: LayoutData;
	pageContents: PageContent[];
	portletIds?: string[];
}) {
	return {
		fragmentEntryLinkIds,
		itemId,
		layoutData,
		pageContents,
		portletIds,
		type: DELETE_ITEM,
	} as const;
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export default function getFragmentEntryLinkIdsFromItemId({
	itemId,
	layoutData,
}) {
	const item = layoutData?.items[itemId];

	return item?.type === LAYOUT_DATA_ITEM_TYPES.fragment
		? [item?.config?.fragmentEntryLinkId]
		: item?.children.reduce(
				(acc, childId) => [
					...acc,
					...getFragmentEntryLinkIdsFromItemId({
						itemId: childId,
						layoutData,
					}),
				],
				[]
		  );
}

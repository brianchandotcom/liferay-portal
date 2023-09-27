/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LAYOUT_DATA_ITEM_TYPE_LABELS} from '../config/constants/layoutDataItemTypeLabels';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export default function selectLayoutDataItemLabel(
	{fragmentEntryLinks},
	item,
	{useCustomName = true} = {}
) {
	if (useCustomName && item.config?.name) {
		return item.config.name;
	}

	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
		fragmentEntryLinks[item.config?.fragmentEntryLinkId]?.name
	) {
		return fragmentEntryLinks[item.config.fragmentEntryLinkId].name;
	}

	return (
		LAYOUT_DATA_ITEM_TYPE_LABELS[item.type] ||
		item.type ||
		Liferay.Language.get('element')
	);
}

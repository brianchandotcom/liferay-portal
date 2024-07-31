/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isHideable from './isHideable';
import {isItemHidden} from './isItemHidden';

export default function canBeHidden({
	fragmentEntryLinks,
	item,
	layoutData,
	masterLayoutData,
	selectedViewportSize,
}) {
	const itemInMasterLayout =
		masterLayoutData &&
		Object.keys(masterLayoutData.items).includes(item.itemId);

	return (
		(!itemInMasterLayout &&
			isHideable(item, fragmentEntryLinks, layoutData)) ||
		isItemHidden(layoutData, item.itemId, selectedViewportSize)
	);
}

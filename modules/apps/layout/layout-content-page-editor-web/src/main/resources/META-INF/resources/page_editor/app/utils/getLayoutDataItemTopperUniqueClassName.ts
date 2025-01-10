/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutDataItem} from '../../types/layout_data/LayoutData';
import {LAYOUT_STRUCTURE_ITEM_CLASS_NAME_PREFIX} from '../config/constants/layoutStructureItemClassNamePrefix';

export default function getLayoutDataItemTopperUniqueClassName(
	itemId: LayoutDataItem['itemId']
) {
	return `${LAYOUT_STRUCTURE_ITEM_CLASS_NAME_PREFIX}topper-${itemId}`;
}

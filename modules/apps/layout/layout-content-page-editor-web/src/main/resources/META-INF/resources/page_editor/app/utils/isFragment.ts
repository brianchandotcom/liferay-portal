/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FragmentLayoutDataItem} from '../../types/layout_data/FragmentLayoutDataItem';

export function isFragment(item: any): item is FragmentLayoutDataItem {
	return item.config && 'fragmentEntryLinkId' in item.config;
}

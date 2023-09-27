/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CONTENT_DISPLAY_OPTIONS} from '../config/constants/contentDisplayOptions';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export default function itemIsContainerFlex(item) {
	return (
		item.type === LAYOUT_DATA_ITEM_TYPES.container &&
		item.config.contentDisplay === CONTENT_DISPLAY_OPTIONS.flexRow
	);
}

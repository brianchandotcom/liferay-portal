/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateItemStyle from './updateItemStyle';

export default function hideFragment({dispatch, itemId, selectedViewportSize}) {
	updateItemStyle({
		dispatch,
		itemId,
		selectedViewportSize,
		styleName: 'display',
		styleValue: 'none',
	});
}

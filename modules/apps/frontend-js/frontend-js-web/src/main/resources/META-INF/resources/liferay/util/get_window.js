/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getTop from './get_top';

export default function getWindow(windowId = window.name) {
	const topWindow = getTop();

	return topWindow.Liferay.Util.Window.getById(windowId);
}

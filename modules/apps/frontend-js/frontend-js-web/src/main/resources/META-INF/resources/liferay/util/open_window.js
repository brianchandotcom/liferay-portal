/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getTop from './get_top';

export default function openWindow(config, callback) {
	const topUtil = getTop();

	config.openingWindow = window;

	topUtil.Liferay.Util._openWindowProvider(config, callback);
}

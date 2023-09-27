/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getTop from './get_top';

let _opener;

export default function getOpener() {
	let openingWindow = _opener;

	if (!openingWindow) {
		const topUtil = getTop().Liferay.Util;
		const windowName = window.name;

		const dialog = topUtil.Window.getById(windowName);

		if (dialog) {
			openingWindow = dialog._opener;

			_opener = openingWindow;
		}
	}

	return openingWindow || window.opener || window.parent;
}

/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import getTop from './get_top';

let _opener;

export default function getOpener() {
	console.log('GETOPENER');
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

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

import toggleDisabled from './toggle_disabled';

export default function selectFolder(folderData, namespace) {
	const folderDataElement = document.getElementById(
		`${namespace}${folderData.idString}`
	);

	if (folderDataElement) {
		folderDataElement.value = folderData.idValue;
	}

	const folderNameElement = document.getElementById(
		`${namespace}${folderData.nameString}`
	);

	if (folderNameElement) {
		folderNameElement.value = Liferay.Util.unescape(folderData.nameValue);
	}

	const removeFolderButton = document.getElementById(
		`${namespace}removeFolderButton`
	);

	if (removeFolderButton) {
		toggleDisabled(removeFolderButton, false);
	}
}

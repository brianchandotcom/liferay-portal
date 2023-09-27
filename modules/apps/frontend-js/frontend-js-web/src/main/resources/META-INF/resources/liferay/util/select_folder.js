/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

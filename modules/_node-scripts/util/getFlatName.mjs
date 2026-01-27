/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function getFlatName(moduleName, appendExtension = undefined) {
	let flatName = moduleName.replaceAll('/', '$');

	if (appendExtension) {
		flatName += `.${appendExtension}`;
	}

	return flatName;
}

getFlatName.reverse = function (flatName, stripExtension = undefined) {
	let moduleName = flatName.replaceAll('$', '/');

	if (stripExtension) {
		moduleName = moduleName.replace(
			new RegExp(`\\.${stripExtension}$`),
			''
		);
	}

	return moduleName;
};

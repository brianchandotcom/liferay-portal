/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import splitProjectExport from './splitProjectExport.mjs';

export default function getNamespacedPackageName(
	projectExport,
	namespacePackageName
) {
	let namespace;

	if (namespacePackageName.startsWith('@')) {
		namespace = namespacePackageName.substring(1).replace('/', '!');
	}
	else {
		namespace = namespacePackageName;
	}

	const {name, scope} = splitProjectExport(projectExport);

	if (scope) {
		namespace = `@${namespace}$${scope.substring(1)}/${name}`;
	}
	else {
		namespace = `${namespace}$${name}`;
	}

	return namespace;
}

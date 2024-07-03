/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getPathPrefix from './getPathPrefix.mjs';

export default function getExternals(
	globalImports,
	projectWebContextPath,
	type
) {
	const prefix = getPathPrefix(type);

	const externals = [

		//
		// Use a Set to deduplicate items
		//

		...new Set(
			Object.values(globalImports).map(
				({webContextPath}) => `${prefix}/${webContextPath}/*`
			)
		),
	];

	if (type === 'main') {
		externals.push(`@liferay/language/${projectWebContextPath}/all.js`);
	}

	return externals;
}

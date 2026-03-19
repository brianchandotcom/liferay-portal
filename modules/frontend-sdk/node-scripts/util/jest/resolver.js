/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = function (request, options) {
	const {defaultResolver} = options;

	// Redirect .css files to our empty.css mock file

	if (request.endsWith('.css')) {
		return require.resolve('./mocks/empty.css');
	}

	// Fallback to default resolver

	return defaultResolver(request, options);
};

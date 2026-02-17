/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * This transform allows Jest to test components that make use of `import`
 * statements to load SCSS files. Outside of tests, these get transformed by the
 * liferay-npm-bundler into JS modules. In the Jest context, the bundler is not
 * involved, so we turn those imports into side-effectless no-ops.
 */
module.exports = {
	process(_src, _file) {
		return {code: ''};
	},
};

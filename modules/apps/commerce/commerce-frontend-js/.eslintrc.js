/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = {
	env: {
		browser: true,
		jest: true,
		node: true,
	},
	globals: {
		AUI: 'readonly',
		Liferay: 'readonly',
	},
	parserOptions: {
		ecmaVersion: 2018,
	},
};

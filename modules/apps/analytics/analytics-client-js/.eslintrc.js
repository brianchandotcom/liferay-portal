/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = {
	globals: {
		global: true,
	},
	overrides: [
		{
			env: {
				jest: true,
			},
			files: '**/__mocks__/**',
		},
	],
	rules: {
		'@liferay/portal/no-global-fetch': 'off',
	},
};

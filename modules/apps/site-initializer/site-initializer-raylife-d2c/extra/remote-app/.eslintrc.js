/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = {
	rules: {
		'@liferay/group-imports': 'off',
		'@liferay/portal/no-loader-import-specifier': 'off',
		'@liferay/portal/no-react-dom-render': 'off',
		'no-case-declarations': 'off',
		'no-empty': ['error', {allowEmptyCatch: true}],
		'no-prototype-builtins': 'off',
		'promise/catch-or-return': 'off',
		'quote-props': 'off',
	},
};

/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

'use strict';

const config = {
	extends: [require.resolve('./react')],
	rules: {
		'@liferay/portal/deprecation': 'error',
		'@liferay/portal/empty-line-after-copyright': 'error',
		'@liferay/portal/no-api-submodule-import': 'error',
		'@liferay/portal/no-default-export-from-frontend-js-web': 'error',
		'@liferay/portal/no-document-cookie': 'error',
		'@liferay/portal/no-explicit-extend': 'error',
		'@liferay/portal/no-global-fetch': 'error',
		'@liferay/portal/no-global-storage': 'error',
		'@liferay/portal/no-loader-import-specifier': 'error',
		'@liferay/portal/no-localhost-reference': 'error',
		'@liferay/portal/no-metal-plugins': 'error',
		'@liferay/portal/no-react-dom-create-portal': 'error',
		'@liferay/portal/no-react-dom-render': 'error',
		'@liferay/portal/no-side-navigation': 'error',
		'@liferay/portal/unexecuted-ismounted': 'error',
		'no-restricted-globals': ['error', 'event'],
	},
};

module.exports = config;

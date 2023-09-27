/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const path = require('path');

/**
 * We use @liferay/npm-scripts to perform linting in a controlled way, but we
 * also try to expose its configuration here so it can be picked up by editors.
 */
let config = {};

try {
	config = require('@liferay/npm-scripts/src/config/eslint.config');
}
catch (error) {
	throw new Error(
		'@liferay/npm-scripts is not installed; please run "ant setup-sdk"'
	);
}

config = {
	...config,
	globals: {
		...config.globals,
		MODULE_PATH: true,
	},
	rules: {
		'@liferay/no-extraneous-dependencies': [
			'error',
			[
				'@liferay/npm-scripts',
				'@testing-library/dom',
				'@testing-library/jest-dom',
				'@testing-library/react-hooks',
				'@testing-library/react',
				'@testing-library/user-event',
				'alloy-ui',
				'buffer',
				'fs',
				'path',
				'process',
				'~',
			],
		],
		'@liferay/no-get-data-attribute': 'off',
		'@liferay/portal/no-document-cookie': 'off',
		'@liferay/portal/no-global-storage': 'off',
		'no-empty': ['error', {allowEmptyCatch: true}],
		'notice/notice': [
			'error',
			{
				nonMatchingTolerance: 0.7,
				onNonMatchingHeader: 'replace',
				templateFile: path.join(__dirname, 'copyright.js'),
			},
		],
		'promise/catch-or-return': 'off',
	},
};

module.exports = config;

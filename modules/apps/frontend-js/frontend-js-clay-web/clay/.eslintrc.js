/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const path = require('path');

const config = require(path.join(__dirname, '../../../../', '.eslintrc.js'));

config.ignorePatterns = [
	...config.ignorePatterns,
	'stories/static/map.js',
	'clay-css/src/js/svg4everybody.js'
];

config.rules = {
	...config.rules,
	'@liferay/no-dynamic-require': 'off',
	'@liferay/no-extraneous-dependencies': 'off',
	'@liferay/portal/deprecation': 'off',
	'@liferay/portal/no-global-fetch': 'off',
	'@liferay/portal/no-react-dom-create-portal': 'off',
	'react/no-unescaped-entities': 'off',
	'react-compiler/react-compiler': 'off',
	'react-hooks/exhaustive-deps': 'off',
};

module.exports = config;

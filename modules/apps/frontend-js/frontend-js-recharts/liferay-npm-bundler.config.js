/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

module.exports = {
	'create-jar': false,
	'exports': {
		recharts: 'recharts',
		recharts_lib_index: 'recharts/lib/index.js',
	},
	'imports': {
		'@liferay/frontend-js-react-web': {
			'react': '^16.0.0',
			'react-dom': '^16.0.0',
		},
		'frontend-js-node-shims': {
			events: '^1.0.0',
		},
	},
	'output': 'build/node/packageRunBuild/resources',
	'workdir': 'build/node/bundler',
};

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
const config = require('generator-liferay-fragments').getBundlerConfig();

module.exports = {
	...config,

	webpack: {
		...config.webpack,
		module: {
			...config.webpack.module,
			rules: [
				...config.webpack.module.rules,
				{
					test: /\.css$/i,
					use: ['style-loader', 'css-loader'],
				},
			],
		},
	},
};

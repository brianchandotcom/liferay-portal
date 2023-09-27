/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const config = require('./webpack.config');

module.exports = {
	...config,
	devServer: {
		port: 3000,
		proxy: {
			'**': 'http://0.0.0.0:8080',
		},
		publicPath: config.output.publicPath,
	},
	devtool: 'inline-source-map',
	mode: 'development',
};

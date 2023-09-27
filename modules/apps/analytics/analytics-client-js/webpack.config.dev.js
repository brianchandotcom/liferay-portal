/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const config = require('./webpack.config');

module.exports = {
	...config,
	devServer: {
		contentBase: config.output.path,
		port: 8081,
	},
	devtool: 'inline-source-map',
	mode: 'development',
	optimization: {
		minimize: false,
	},
};

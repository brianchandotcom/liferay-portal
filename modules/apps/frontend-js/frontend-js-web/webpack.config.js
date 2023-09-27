/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const path = require('path');

const PUBLIC_PATH = '/o/frontend-js-web/liferay/';

module.exports = {
	context: path.resolve(__dirname),
	devtool: 'source-map',
	entry: './src/main/resources/META-INF/resources/liferay/global.es.js',
	mode: 'production',
	module: {
		rules: [
			{
				exclude: /node_modules/,
				test: /\.js$/,
				use: {
					loader: 'babel-loader',
				},
			},
		],
	},
	output: {
		filename: 'global.bundle.js',
		libraryTarget: 'window',
		path: path.resolve('./build/node/packageRunBuild/resources/liferay/'),
		publicPath: PUBLIC_PATH,
	},
};

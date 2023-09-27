/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const path = require('path');

const PUBLIC_PATH = '/o/client-extension-client-js/';

module.exports = {
	context: path.resolve(__dirname),
	devtool: 'source-map',
	entry: './src/main/resources/META-INF/resources/js/index.ts',
	mode: 'production',
	module: {
		rules: [
			{
				test: /\.ts$/,
				use: 'babel-loader',
			},
		],
	},
	output: {
		filename: 'client-extension-client-js.js',
		libraryTarget: 'window',
		path: path.resolve('./build/node/packageRunBuild/resources/'),
		publicPath: PUBLIC_PATH,
	},
	resolve: {
		extensions: ['.js', '.ts'],
	},
};

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const path = require('path');
const webpack = require('webpack');

module.exports = {
	context: path.resolve(__dirname),
	devtool: 'source-map',
	entry: './src/main/resources/META-INF/resources/svg4everybody.js',
	mode: 'production',
	output: {
		filename: 'index.js',
		library: 'svg4everybody',
		path: path.resolve('./build/node/packageRunBuild/resources/'),
	},
	plugins: [
		new webpack.DefinePlugin({
			LEGACY_SUPPORT: false,
		}),
	],
};

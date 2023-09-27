/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const buildFolder = `${__dirname}/build`;
const buildName = 'analytics-all-min.js';

module.exports = {
	entry: [
		'core-js/fn/array/includes',
		'core-js/fn/object/assign',
		'core-js/fn/promise',
		'core-js/fn/string/includes',
		'./src/analytics.js',
	],
	mode: 'production',
	module: {
		rules: [
			{
				exclude: /node_modules/,
				test: /\.m?js$/,
				use: {
					loader: 'babel-loader',
					options: {
						compact: false,
						plugins: [],
					},
				},
			},
		],
	},
	optimization: {
		minimize: true,
	},
	output: {
		filename: buildName,
		path: buildFolder,
	},
};

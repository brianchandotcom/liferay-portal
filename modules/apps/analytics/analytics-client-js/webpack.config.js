/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

const buildFolder = `${__dirname}/build`;
const buildName = 'analytics-all-min.js';

module.exports = {
	entry: [
		'core-js/fn/array/includes',
		'core-js/fn/object/assign',
		'core-js/fn/promise',
		'core-js/fn/string/includes',
		'unfetch/polyfill',
		'./src/analytics.js',
	],
	mode: 'production',
	module: {
		rules: [
			{
				exclude: /node_modules\/(?!unfetch\/)/,
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

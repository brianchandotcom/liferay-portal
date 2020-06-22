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

const webpack = require('webpack');
const buildFolder = `${__dirname}/build`;
const buildName = 'analytics-all-min.js';

module.exports = {
	entry: [
		'core-js/fn/array/from',
		'core-js/fn/array/find',
		'core-js/fn/array/includes',
		'core-js/fn/math/sign',
		'core-js/fn/math/trunc',
		'core-js/fn/string/includes',
		'core-js/fn/string/pad-start',
		'core-js/es6/symbol',
		'core-js/fn/promise',
		'whatwg-fetch',
		'./src/analytics.js',
	],
	mode: 'production',
	module: {
		rules: [
			{
				exclude: /(node_modules)/,
				test: /\.js$/,
				use: {
					loader: 'babel-loader',
					options: {
						compact: false,
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
	plugins: [new webpack.optimize.ModuleConcatenationPlugin()],
};

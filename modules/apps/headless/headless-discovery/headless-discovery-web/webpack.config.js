/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const path = require('path');
const buildName = 'headless-discovery-web-min.js';

const config = {
	mode: 'production',
	module: {
		rules: [
			{
				test: /\.css$/,
				use: [MiniCssExtractPlugin.loader, 'css-loader'],
			},
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
		path: path.resolve('./build/node/packageRunBuild/resources/'),
		publicPath: '',
	},
	plugins: [
		new MiniCssExtractPlugin({
			chunkFilename: '[id].css',
			filename: '[name].css',
		}),
		new HtmlWebpackPlugin({
			cache: false,
			filename: 'index.html',
			inject: true,
			template: './src/index.html',
		}),
	],
};

module.exports = () => config;

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

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
const path = require('path');
const webpack = require('webpack');

const {defineServerResponses} = require('./dev/fakeServerUtilities');
const components = require('./src/main/resources/META-INF/resources/components/index');

const outputPath = path.resolve(__dirname, './dev/public');

const getComponentPath = (component, entry) =>
	path.join(
		__dirname,
		'src',
		'main',
		'resources',
		'META-INF',
		'resources',
		'components',
		component,
		entry
	);

// eslint-disable-next-line no-undef
module.exports = {
	devServer: {
		before(app) {
			defineServerResponses(app);
		},
		compress: false,
		contentBase: './dev/public',
		open: true,
		openPage: 'index.html',
		port: 9000,
		proxy: {
			'/o': {
				target: 'http://localhost:8080/'
			}
		},
		publicPath: '/'
	},
	devtool: 'inline-source-map',
	entry: components.reduce((comp, current) => {
		comp[current.folder] = getComponentPath(
			current.folder,
			current.entry_dev
		);
		return comp;
	}, {}),
	mode: 'development',
	module: {
		rules: [
			{
				exclude: /node_modules/,
				test: /\.(js|jsx)$/,
				use: [
					{
						loader: 'babel-loader'
					}
				]
			},
			{
				test: /\.(scss|css)$/,
				use: [
					{loader: 'style-loader'},
					{loader: 'css-loader'},
					{loader: 'sass-loader'}
				]
			},
			{
				exclude: /node_modules/,
				test: /\.tsx?$/,
				use: 'ts-loader'
			}
		]
	},
	output: {
		filename: '[name].js',
		path: outputPath
	},
	plugins: [
		new webpack.optimize.ModuleConcatenationPlugin(),
		new HtmlWebpackPlugin({
			inject: false,
			template: path.resolve(__dirname, './dev/public/index.html')
		})
	],
	resolve: {
		extensions: ['.js', '.jsx']
	}
};

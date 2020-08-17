/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

const CleanWebpackPlugin = require('clean-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');

const {defineServerResponses} = require('./dev/fakeServerUtilities');

const outputPath = path.resolve(__dirname, './dev/public');

module.exports = {
	entry: path.join(
		__dirname,
		'./src/main/resources/META-INF/resources/js/index.dev.es.js'
	),
	mode: 'development',
	module: {
		rules: [
			{
				exclude: /node_modules/,
				test: /\.(js|jsx)$/,
				use: [
					{
						loader: 'babel-loader',
					},
				],
			},
			{
				test: /\.(scss|css)$/,
				use: [
					{loader: 'style-loader'},
					{loader: 'css-loader'},
					{loader: 'sass-loader'},
				],
			},
		],
	},
	output: {
		filename: 'bundle.js',
		path: outputPath,
	},
	plugins: [
		new HtmlWebpackPlugin({
			template: path.resolve(__dirname, './dev/public/index.html'),
			inject: false,
		}),
	],
	resolve: {
		extensions: ['.js', '.jsx'],
	},
	devServer: {
		compress: false,
		publicPath: '/',
		contentBase: './dev/public',
		filename: path.join(outputPath, '/bundle.js'),
		open: true,
		port: 9000,
		hot: true,
		historyApiFallback: true,
		before(app) {
			defineServerResponses(app);
		},
	},
};

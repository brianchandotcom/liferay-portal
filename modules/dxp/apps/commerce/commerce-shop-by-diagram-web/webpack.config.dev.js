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

const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');
const webpack = require('webpack');

const outputPath = path.resolve(__dirname, './dev/public');

module.exports = {
	devServer: {
		compress: false,
		contentBase: './dev/public',
		disableHostCheck: true,
		open: true,
		openPage: 'index.html',
		port: 9000,
		proxy: {
			'/documents': {
				target: 'http://localhost:8080/',
			},
			'/image': {
				target: 'http://localhost:8080/',
			},
			'/o': {
				target: 'http://localhost:8080/',
			},
		},
		publicPath: '/',
	},
	devtool: 'inline-source-map',
	entry: path.resolve(__dirname, 'dev/entry.js'),

	mode: 'development',
	module: {
		rules: [
			{
				exclude: /node_modules/,
				test: /\.(js|jsx|ts|tsx)$/,
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
					{
						loader: 'sass-loader',
						options: {
							sassOptions: {
								importer: (url, _, done) => {
									if (url.includes('atlas-variables')) {
										done({
											file: path.resolve(
												__dirname,
												'./../../../../node_modules/@clayui/css/src/scss/atlas-variables.scss'
											),
										});
									}
									else {
										done({file: url});
									}
								},
							},
						},
					},
				],
			},
		],
	},
	output: {
		filename: 'entry.js',
		path: outputPath,
	},
	plugins: [
		new webpack.optimize.ModuleConcatenationPlugin(),
		new HtmlWebpackPlugin({
			inject: false,
			template: path.resolve(__dirname, './dev/public/index.html'),
		}),
	],
	resolve: {
		alias: {
			'@liferay/frontend-data-set-web': path.resolve(
				__dirname,
				'../../../../node_modules/@liferay/frontend-data-set-web/src/main/resources/META-INF/resources/index.js'
			),
			'@liferay/frontend-js-react-web': path.resolve(
				__dirname,
				'../../../../node_modules/@liferay/frontend-js-react-web/src/main/resources/META-INF/resources/js/index.ts'
			),
			'@liferay/frontend-js-state-web': path.resolve(
				__dirname,
				'../../../../node_modules/@liferay/frontend-js-state-web/src/main/resources/META-INF/resources/index.ts'
			),
			'commerce-frontend-js/components/add_to_cart/AddToCart': path.resolve(
				__dirname,
				'../../../../node_modules/commerce-frontend-js/src/main/resources/META-INF/resources/components/add_to_cart/AddToCart.js'
			),
			'commerce-frontend-js/components/add_to_cart/AddToCartButton': path.resolve(
				__dirname,
				'../../../../node_modules/commerce-frontend-js/src/main/resources/META-INF/resources/components/add_to_cart/AddToCartButton.js'
			),
			'commerce-frontend-js/components/autocomplete/Autocomplete': path.resolve(
				__dirname,
				'../../../../node_modules/commerce-frontend-js/src/main/resources/META-INF/resources/components/autocomplete/Autocomplete.js'
			),
			'commerce-frontend-js/components/infinite_scroller/InfiniteScroller': path.resolve(
				__dirname,
				'../../../../node_modules/commerce-frontend-js/src/main/resources/META-INF/resources/components/infinite_scroller/InfiniteScroller.js'
			),
			'commerce-frontend-js/components/quantity_selector/QuantitySelector': path.resolve(
				__dirname,
				'../../../../node_modules/commerce-frontend-js/src/main/resources/META-INF/resources/components/quantity_selector/QuantitySelector.js'
			),
			'commerce-frontend-js/utilities/hooks': path.resolve(
				__dirname,
				'../../../../node_modules/commerce-frontend-js/src/main/resources/META-INF/resources/utilities/hooks.js'
			),
			'frontend-js-web': path.resolve(
				__dirname,
				'../../../../node_modules/frontend-js-web/src/main/resources/META-INF/resources/index.es.js'
			),
		},
		extensions: ['.js', '.jsx', '.ts', '.tsx'],
	},
};

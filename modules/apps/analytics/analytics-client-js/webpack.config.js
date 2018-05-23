const webpack = require('webpack');
const buildFolder = `${__dirname}/build`;
const buildName = 'analytics-all-min.js';

module.exports = {
	entry: [
		'core-js/fn/array/from',
		'core-js/fn/array/find',
		'core-js/es6/symbol',
		'core-js/fn/promise',
		'whatwg-fetch',
		'./src/analytics.js'
	],
	mode: 'production',
	module: {
		rules: [
			{
				test: /\.js$/,
				exclude: /(node_modules)/,
				use: {
					loader: 'babel-loader',
					options: {
						compact: false,
					},
				},
			},
		],
	},
	output: {
		path: buildFolder,
		filename: buildName
	},
	optimization: {
		minimize: true
	},
	plugins: [
		new webpack.optimize.ModuleConcatenationPlugin()
	]
};
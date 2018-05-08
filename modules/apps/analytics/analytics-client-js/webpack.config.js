const webpack = require('webpack');
const buildFolder = './build';
const buildName = 'analytics-all-min.js';

module.exports = {
	entry: './src/analytics.js',
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
		filename: `${buildFolder}/${buildName}`,
	},
	plugins: [
		new webpack.optimize.ModuleConcatenationPlugin(),
		new webpack.optimize.UglifyJsPlugin({
			compress: {
				warnings: false,
			},
		}),
	],
};
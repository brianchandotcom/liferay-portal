const webpack = require('webpack');
const path = require('path');

const PUBLIC_PATH = '/o/frontend-js-web/liferay/';

module.exports = {
	config: {
		entry: './src/main/resources/META-INF/resources/liferay/global.es.js',
		output: {
			filename: 'global.bundle.js',
			library: 'portlet',
			libraryTarget: 'window',
			path: path.resolve('./classes/META-INF/resources/liferay/'),
			publicPath: PUBLIC_PATH
		}
	},
	module: {
		rules: [
			{
				test: /\.js$/,
				exclude: /node_modules/,
				use: {
					loader: 'babel-loader'
				}
			}
		]
	},
	publicPath: PUBLIC_PATH
};
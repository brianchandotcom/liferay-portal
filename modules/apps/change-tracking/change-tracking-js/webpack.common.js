const webpack = require('webpack');
const path = require('path');

const PUBLIC_PATH = '/o/change-tracking-js/liferay/';

module.exports = {
	config: {
		context: path.resolve(__dirname),
		entry: './src/main/resources/META-INF/resources/liferay/global.es.js',
		output: {
			filename: 'global.bundle.js',
			libraryTarget: 'window',
			path: path.resolve('./classes/META-INF/resources/liferay/'),
			publicPath: PUBLIC_PATH
		},
		mode: 'none',
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
	},
	publicPath: PUBLIC_PATH
};
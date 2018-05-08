const webpack = require('webpack');
const path = require('path');

const PUBLIC_PATH = '/o/frontend-editor-alloyeditor-web/alloyeditor/';

module.exports = {
	config: {
		entry: './src/main/resources/META-INF/resources/js/extras/index.js',
		output: {
			filename: 'alloy-editor-extras.js',
			path: path.resolve('./classes/META-INF/resources/alloyeditor/'),
			publicPath: PUBLIC_PATH
		},
		module: {
			rules: [
				{
					include: __dirname,
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
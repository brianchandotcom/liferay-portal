const path = require('path');

const PUBLIC_PATH = '/o/frontend-js-web/liferay/';

module.exports = {
	context: path.resolve(__dirname),
	devtool: 'source-map',
	entry: './src/main/resources/META-INF/resources/liferay/global.es.js',
	output: {
		filename: 'global.bundle.js',
		libraryTarget: 'window',
		path: path.resolve('./classes/META-INF/resources/liferay/'),
		publicPath: PUBLIC_PATH
	},
	mode: 'production',
	module: {
		rules: [
			{
				exclude: /node_modules/,
				test: /\.js$/,
				use: {
					loader: 'babel-loader'
				}
			}
		]
	}
};
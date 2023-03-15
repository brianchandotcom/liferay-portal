const common = require('./webpack.common');
const merge = require('webpack-merge');
const webpack = require('webpack');

require('dotenv').config();

module.exports = merge(common.config, {
	devServer: {
		host: '0.0.0.0',
		port: 3000,
		proxy: {
			'**': process.env.FARO_URL || 'http://0.0.0.0:8080'
		},
		publicPath: common.PUBLIC_PATH
	},
	devtool: 'inline-source-map',
	mode: 'development',
	module: {
		rules: [
			{
				include: common.include,
				loader: 'liferay-lang-key-dev-loader',
				test: /\.(js|ts)x?$/
			}
		]
	},
	output: {
		chunkFilename: '[name].[chunkhash:8].js'
	},
	plugins: [
		new webpack.DefinePlugin({
			FARO_DEV_MODE: true
		})
	]
});

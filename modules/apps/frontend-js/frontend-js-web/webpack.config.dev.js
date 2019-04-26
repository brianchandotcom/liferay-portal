const merge = require('webpack-merge');
const webpack = require('webpack');

const common = require('./webpack.config.common');

module.exports = merge(
	common.config,
	{
		devServer: {
			port: 3000,
			proxy: {
				'**': 'http://0.0.0.0:8080'
			},
			publicPath: common.publicPath
		},
		devtool: 'inline-source-map',
		mode: 'development'
	}
);
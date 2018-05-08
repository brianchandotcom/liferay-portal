const merge = require('webpack-merge');
const webpack = require('webpack');

const common = require('./webpack.common.js');

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
		plugins: [
			new webpack.DefinePlugin(
				{
					'process.env': {
						NODE_ENV: '"development"'
					}
				}
			)
		]
	}
);
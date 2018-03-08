const merge = require('webpack-merge');
const webpack = require('webpack');

const common = require('./webpack.common.js');

module.exports = merge(
	common.config,
	{
		plugins: [
			new webpack.DefinePlugin(
				{
					'process.env': {
						NODE_ENV: '"production"'
					}
				}
			)
		]
	}
);
const merge = require('webpack-merge');
const webpack = require('webpack');

const common = require('./webpack.config.common');

module.exports = merge(
	common.config,
	{
		devtool: 'source-map',
		mode: 'production'
	}
);
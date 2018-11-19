const merge = require('webpack-merge');
const webpack = require('webpack');

const common = require('./webpack.common.js');

module.exports = merge(
	common.config,
	{
		devtool: 'source-map',
		mode: 'production'
	}
);
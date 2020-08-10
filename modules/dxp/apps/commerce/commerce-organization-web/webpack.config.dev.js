const merge = require('webpack-merge'),
	webpackBase = require('./webpack.config.js');

module.exports = merge(webpackBase, {
	devtool: 'inline-source-map'
});
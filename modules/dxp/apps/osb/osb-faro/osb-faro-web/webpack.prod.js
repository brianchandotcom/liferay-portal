const common = require('./webpack.common');
const merge = require('webpack-merge');
const webpack = require('webpack');

module.exports = merge(common.config, {
	mode: 'production',
	output: {
		chunkFilename: '[id].[chunkhash].js'
	},
	performance: {
		hints: false
	},
	plugins: [
		new webpack.DefinePlugin({
			FARO_DEV_MODE: false
		})
	]
});
